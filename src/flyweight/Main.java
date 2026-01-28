package flyweight;

import java.util.HashMap;
import java.util.Map;

// Flyweight Pattern = share common object data to reduce memory usage.
// Intrinsic (shared): type, color, texture
// Extrinsic (unique per use): x, y position

// Flyweight (shared object)
class TreeType {
    private final String name;
    private final String color;

    public TreeType(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public void draw(int x, int y) {
        // x,y are EXTRINSIC (passed each time)
        System.out.println("Draw " + name + " (" + color + ") at (" + x + "," + y + ")");
    }
}

// Flyweight Factory (cache)
class TreeFactory {
    private static final Map<String, TreeType> cache = new HashMap<>();

    public static TreeType getTreeType(String name, String color) {
        String key = name + "|" + color;

        // Create once, reuse many times
        if (!cache.containsKey(key)) {
            cache.put(key, new TreeType(name, color));
            System.out.println("Created new TreeType: " + key);
        }

        return cache.get(key);
    }

    public static int cachedTypesCount() {
        return cache.size();
    }
}

// Context (has extrinsic data)
class Tree {
    private final int x;
    private final int y;
    private final TreeType type; // shared flyweight

    public Tree(int x, int y, TreeType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public void draw() {
        type.draw(x, y);
    }
}

public class Main {
    public static void main(String[] args) {
        // Create many trees, but only a few shared TreeType objects
        Tree t1 = new Tree(10, 20, TreeFactory.getTreeType("Oak", "Green"));
        Tree t2 = new Tree(11, 21, TreeFactory.getTreeType("Oak", "Green"));
        Tree t3 = new Tree(50, 60, TreeFactory.getTreeType("Pine", "DarkGreen"));
        Tree t4 = new Tree(70, 80, TreeFactory.getTreeType("Oak", "Green"));

        System.out.println("\nCached TreeTypes = " + TreeFactory.cachedTypesCount() + "\n");

        t1.draw();
        t2.draw();
        t3.draw();
        t4.draw();
    }
}
