package dsa;

public class Cargo {
    private String name;
    private int space;

    public Cargo(String name, int capacity) {
        this.name = name;
        this.space = capacity;
    }

    public String getName() {
        return name;
    }

    public int getSpace() {
        return space;
    }
}
