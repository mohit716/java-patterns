package builder;

// Builder Pattern = build a complex object step-by-step,
// without calling a huge constructor with 10 parameters.

class House {
    // Final "product" fields
    private final int rooms;
    private final boolean hasGarage;
    private final boolean hasGarden;
    private final String roofType;

    // Private constructor: only Builder can create a House
    private House(Builder b) {
        this.rooms = b.rooms;
        this.hasGarage = b.hasGarage;
        this.hasGarden = b.hasGarden;
        this.roofType = b.roofType;
    }

    // Nice toString for printing
    public String toString() {
        return "House{rooms=" + rooms
                + ", garage=" + hasGarage
                + ", garden=" + hasGarden
                + ", roofType='" + roofType + "'}";
    }

    // The Builder (nested static class is common in Java)
    public static class Builder {
        // default values
        private int rooms = 1;
        private boolean hasGarage = false;
        private boolean hasGarden = false;
        private String roofType = "flat";

        public Builder rooms(int rooms) {
            this.rooms = rooms;
            return this; // return Builder so we can chain calls
        }

        public Builder garage(boolean value) {
            this.hasGarage = value;
            return this;
        }

        public Builder garden(boolean value) {
            this.hasGarden = value;
            return this;
        }

        public Builder roofType(String type) {
            this.roofType = type;
            return this;
        }

        public House build() {
            // You can validate here if needed
            if (rooms <= 0) {
                throw new IllegalArgumentException("rooms must be >= 1");
            }
            return new House(this);
        }
    }
}

public class Main {
    public static void main(String[] args) {

        // Build a house step-by-step (this is the pattern)
        House h1 = new House.Builder()
                .rooms(3)
                .garage(true)
                .garden(true)
                .roofType("sloped")
                .build();

        System.out.println(h1);

        // Another house, different config, same builder
        House h2 = new House.Builder()
                .rooms(1)
                .roofType("flat")
                .build();

        System.out.println(h2);
    }
}
