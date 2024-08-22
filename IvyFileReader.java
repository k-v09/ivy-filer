import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IvyFileReader {

    public static IvyFileData readIvyFile(String filename) throws IOException {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(filename))) {
            int magicNumber = dis.readInt();
            if (magicNumber != 0x49495659) {
                throw new IOException("Invalid .ivy file.");
            }
            int numOvertones = dis.readShort();
            List<Harmonic> overtones = new ArrayList<>();
            for (int i = 0; i < numOvertones; i++) {
                float frequency = dis.readFloat();
                float amplitude = dis.readFloat();
                overtones.add(new Harmonic(frequency, amplitude));
            }
            int numReferences = dis.readShort();
            List<String> references = new ArrayList<>();
            for (int i = 0; i < numReferences; i++) {
                int refLength = dis.readByte();
                byte[] refBytes = new byte[refLength];
                dis.readFully(refBytes);
                references.add(new String(refBytes));
            }

            return new IvyFileData(overtones, references);
        }
    }

    public static void main(String[] args) {
        try {
            // Example usage
            IvyFileData ivyData = readIvyFile("sound.ivy");
            System.out.println("Overtones: " + ivyData.getOvertones());
            System.out.println("References: " + ivyData.getReferences());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class IvyFileData {
    private List<Harmonic> overtones;
    private List<String> references;

    public IvyFileData(List<Harmonic> overtones, List<String> references) {
        this.overtones = overtones;
        this.references = references;
    }

    public List<Harmonic> getOvertones() {
        return overtones;
    }

    public List<String> getReferences() {
        return references;
    }

    @Override
    public String toString() {
        return "IvyFileData{" +
                "overtones=" + overtones +
                ", references=" + references +
                '}';
    }
}
