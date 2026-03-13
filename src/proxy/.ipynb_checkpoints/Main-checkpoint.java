package proxy;

// Proxy Pattern = a stand-in object that controls access to a real object.
// Common use cases: lazy loading, caching, access control.

interface Image {
    void display();
}

// Real Subject (heavy object)
class RealImage implements Image {
    private final String filename;

    public RealImage(String filename) {
        this.filename = filename;
        loadFromDisk(); // heavy work
    }

    private void loadFromDisk() {
        System.out.println("Loading image from disk: " + filename + " (heavy)");
    }

    public void display() {
        System.out.println("Displaying image: " + filename);
    }
}

// Proxy (controls access + lazy-loads)
class ImageProxy implements Image {
    private final String filename;
    private RealImage realImage; // starts null (not loaded yet)

    public ImageProxy(String filename) {
        this.filename = filename;
    }

    public void display() {
        // Lazy loading: load only when needed
        if (realImage == null) {
            realImage = new RealImage(filename);
        }
        realImage.display();
    }
}

public class Main {
    public static void main(String[] args) {

        // Proxy created (fast, no heavy loading yet)
        Image img = new ImageProxy("cat.png");

        System.out.println("Proxy created. Nothing loaded yet.\n");

        // First display triggers real load
        img.display();

        System.out.println();

        // Second display does NOT load again (already cached)
        img.display();
    }
}
