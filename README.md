# Ivy File Format Project

## Overview

This project provides a Java implementation for creating and reading custom binary files with the `.ivy` extension. These `.ivy` files store frequency information such as the number of overtones, the amplitude of each harmonic, and references to other `.ivy` files. This format can be useful in sound synthesis or analysis applications.

## Project Structure

- `IvyFileWriter.java`: Handles writing data to a `.ivy` file.
- `IvyFileReader.java`: Handles reading data from a `.ivy` file.
- `Harmonic.java`: Represents a harmonic with frequency and amplitude properties.

## Usage

### Writing an `.ivy` File

```java
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            List<Harmonic> overtones = Arrays.asList(
                new Harmonic(440.0f, 1.0f),
                new Harmonic(880.0f, 0.5f),
                new Harmonic(1320.0f, 0.25f)
            );

            List<String> references = Arrays.asList("base.ivy", "mod.ivy");

            IvyFileWriter.writeIvyFile("sound.ivy", overtones, references);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

### Reading an `.ivy` File

To read data from an `.ivy` file, use the `IvyFileReader` class:

```java
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            IvyFileData ivyData = IvyFileReader.readIvyFile("sound.ivy");
            System.out.println("Overtones: " + ivyData.getOvertones());
            System.out.println("References: " + ivyData.getReferences());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

## Example Files

### base.ivy

This file contains a simple harmonic structure with three harmonics (440 Hz, 880 Hz, 1320 Hz) and no references.

### mod.ivy

This file contains two harmonics (2000 Hz, 3000 Hz) and references `base.ivy` as its base.

You can test reading these example `.ivy` files using the `IvyFileReader`:

```java
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            IvyFileData baseData = IvyFileReader.readIvyFile("base.ivy");
            System.out.println("Base Overtones: " + baseData.getOvertones());
            System.out.println("Base References: " + baseData.getReferences());

            IvyFileData modData = IvyFileReader.readIvyFile("mod.ivy");
            System.out.println("Mod Overtones: " + modData.getOvertones());
            System.out.println("Mod References: " + modData.getReferences());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

### complex.ivy

This file represents a complex harmonic structure with five harmonics and references to three other `.ivy` files: `base.ivy`, `mod.ivy`, and `additional.ivy`.

**Harmonics**:
- 440 Hz with amplitude 1.0
- 1000 Hz with amplitude 0.75
- 2000 Hz with amplitude 0.625
- 3000 Hz with amplitude 0.25
- 4000 Hz with amplitude 0.5

**References**:
- `base.ivy`: A file with three harmonics (440 Hz, 880 Hz, 1320 Hz).
- `mod.ivy`: A file with two harmonics (2000 Hz, 3000 Hz) and a reference to `base.ivy`.
- `additional.ivy`: A file with four harmonics (550 Hz, 1100 Hz, 1650 Hz, 2200 Hz).

### Example Usage

You can load the `complex.ivy` file and its references using the following Java code:

```java
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            IvyFileData complexData = IvyFileReader.readIvyFile("complex.ivy");
            System.out.println("Complex Overtones: " + complexData.getOvertones());
            System.out.println("Complex References: " + complexData.getReferences());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

## Ivy File Format

The `.ivy` file format has the following binary structure:

1. **Magic Number (4 bytes)**: A fixed identifier to recognize the file type. For example, `0x49495659`.
2. **Number of Overtones (2 bytes)**: Indicates the number of harmonic overtones.
3. **Harmonic Data**:
   - For each overtone:
     - **Frequency (4 bytes)**: The frequency of the harmonic.
     - **Amplitude (4 bytes)**: The amplitude of the harmonic.
4. **Number of References (2 bytes)**: The number of references to other `.ivy` files.
5. **File References**:
   - For each reference:
     - **Length of Filename (1 byte)**: The length of the filename.
     - **Filename (variable length)**: The name of the referenced file.

## Requirements

- Java 8 or later

## How to Compile and Run

1. **Compile the Java files**:

   ```bash
   javac IvyFileWriter.java IvyFileReader.java Harmonic.java IvyFileData.java
    ```

2. **Run the example**:
    ```bash
    java Main
    ```
This will create a `sound.ivy` file and read its contents

## Future Improvements

- **Enhanced Error Handling**: Improve handling for corrupted files or unexpected data formats to ensure robust file operations and data integrity.
- **Extended File Format**: Add support for additional metadata, such as waveform types, sound envelopes, or other sound attributes to provide more comprehensive information.
- **File Compression**: Implement file compression to reduce file size, making it more efficient for storage and transfer.
- **Versioning**: Introduce versioning to the file format to support backward and forward compatibility as the format evolves.
- **User Interface**: Develop a graphical user interface (GUI) for easier creation and editing of `.ivy` files.
- **Validation Tools**: Create tools for validating `.ivy` files to ensure they adhere to the expected format and contain valid data.
