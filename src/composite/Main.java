package composite;

import java.util.ArrayList;
import java.util.List;

// Composite Pattern = treat individual objects and groups of objects uniformly.
// Example: File and Folder both implement Node.

interface Node {
    int getSize();
    void print(String indent);
}

// Leaf
class FileNode implements Node {
    private final String name;
    private final int size;

    public FileNode(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void print(String indent) {
        System.out.println(indent + "- File: " + name + " (" + size + " KB)");
    }
}

// Composite
class FolderNode implements Node {
    private final String name;
    private final List<Node> children = new ArrayList<>();

    public FolderNode(String name) {
        this.name = name;
    }

    public void add(Node node) {
        children.add(node);
    }

    public int getSize() {
        int total = 0;
        for (Node n : children) {
            total += n.getSize();
        }
        return total;
    }

    public void print(String indent) {
        System.out.println(indent + "+ Folder: " + name + " (total " + getSize() + " KB)");
        for (Node n : children) {
            n.print(indent + "  ");
        }
    }
}

public class Main {
    public static void main(String[] args) {

        // Leaves
        Node resume = new FileNode("resume.pdf", 120);
        Node photo = new FileNode("photo.png", 350);

        // Folder "docs"
        FolderNode docs = new FolderNode("docs");
        docs.add(resume);

        // Folder "root"
        FolderNode root = new FolderNode("root");
        root.add(docs);
        root.add(photo);
        root.add(new FileNode("notes.txt", 10));

        // Treat root (folder) same way as file: Node interface
        root.print("");
        System.out.println("\nRoot total size = " + root.getSize() + " KB");
    }
}
