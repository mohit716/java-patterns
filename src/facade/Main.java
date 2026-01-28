package facade;

// Facade Pattern = provide a simple interface to a complex subsystem.
// Client calls ONE method; facade coordinates many classes behind the scenes.

// --- Subsystem classes (messy / too many steps) ---

class VideoFile {
    String filename;
    VideoFile(String filename) { this.filename = filename; }
}

class Codec { String name; Codec(String name) { this.name = name; } }

class CodecDetector {
    Codec detect(VideoFile file) {
        System.out.println("Detecting codec for: " + file.filename);
        return new Codec("H.264");
    }
}

class Decoder {
    String decode(VideoFile file, Codec codec) {
        System.out.println("Decoding " + file.filename + " using codec " + codec.name);
        return "RAW_DATA";
    }
}

class Encoder {
    String encode(String rawData, String targetFormat) {
        System.out.println("Encoding raw data to " + targetFormat);
        return "ENCODED_DATA_" + targetFormat;
    }
}

class FileSaver {
    void save(String encodedData, String outputFilename) {
        System.out.println("Saving output to: " + outputFilename);
        // pretend we wrote a file
    }
}

// --- The Facade (the "easy button") ---

class VideoConverterFacade {
    private final CodecDetector codecDetector = new CodecDetector();
    private final Decoder decoder = new Decoder();
    private final Encoder encoder = new Encoder();
    private final FileSaver saver = new FileSaver();

    // ONE simple method that hides the subsystem complexity
    public void convert(String inputFilename, String targetFormat) {
        VideoFile file = new VideoFile(inputFilename);

        Codec codec = codecDetector.detect(file);
        String raw = decoder.decode(file, codec);
        String encoded = encoder.encode(raw, targetFormat);

        String outputFilename = inputFilename + "." + targetFormat;
        saver.save(encoded, outputFilename);

        System.out.println("Done ✅");
    }
}

public class Main {
    public static void main(String[] args) {
        // Client only talks to the facade
        VideoConverterFacade converter = new VideoConverterFacade();
        converter.convert("movie.mp4", "avi");
    }
}
