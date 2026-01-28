package iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

// Iterator Pattern = provide a way to traverse a collection
// without exposing its internal representation.

class NameCollection implements Iterable<String> {
    private final String[] names;

    public NameCollection(String[] names) {
        this.names = names;
    }

    // This is the key: return an Iterator
    public Iterator<String> iterator() {
        return new NameIterator();
    }

    // Inner iterator class
    private class NameIterator implements Iterator<String> {
        private int index = 0;

        public boolean hasNext() {
            return index < names.length;
        }

        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return names[index++];
        }
    }
}

public class Main {
    public static void main(String[] args) {
        NameCollection list = new NameCollection(new String[]{"Mohit", "Aman", "Riya"});

        // Because NameCollection implements Iterable, we can use for-each
        for (String name : list) {
            System.out.println(name);
        }
    }
}
