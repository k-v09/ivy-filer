import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class IvyFileWriter {

    public static void writeIvyFile(String filename, List<Harmonic> overtones, List<String> references) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(filename))) {
            dos.writeInt(0x49495659);
            dos.writeShort(overtones.size());
            for (Harmonic overtone : overtones) {
                dos.writeFloat(overtone.getFrequency());
                dos.writeFloat(overtone.getAmplitude());
            }
            dos.writeShort(references.size());
            for (String ref : references) {
                dos.writeByte(ref.length());
                dos.writeBytes(ref);
            }
        }
    }

    public static void main(String[] args) {
        try {
            List<Harmonic> overtones = Arrays.asList(
                new Harmonic(440.0f, 1.0f),
                new Harmonic(880.0f, 0.5f),
                new Harmonic(1320.0f, 0.25f)
            );

            List<String> references = Arrays.asList("base.ivy", "mod.ivy");

            writeIvyFile("sound.ivy", overtones, references);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Harmonic {
    private float frequency;
    private float amplitude;

    public Harmonic(float frequency, float amplitude) {
        this.frequency = frequency;
        this.amplitude = amplitude;
    }

    public float getFrequency() {
        return frequency;
    }

    public float getAmplitude() {
        return amplitude;
    }
}
