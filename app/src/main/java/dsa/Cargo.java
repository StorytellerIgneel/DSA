package dsa;

public class Cargo implements Comparable<Cargo> {
    private String name;
    private int size;

    public Cargo(String name, int capacity) {
        this.name = name;
        this.size = capacity;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    @Override
    public int compareTo(Cargo other) {
        return Integer.compare(this.size, other.size);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true; // same pointer reference
        if (obj == null || getClass() != obj.getClass())
            return false; // check if the object is null or not the same class

        Cargo cargo = (Cargo) obj;
        return size == cargo.size && name.equals(cargo.name); // check if the space and name are the same
    }

    @Override
    public int hashCode() {
        int result = name.hashCode(); // get the hashcode of the name
        result = 31 * result + size; // multiply by 31 and add the space
        return result; // return the hashcode
    }

    @Override
    public String toString() {
        return "Cargo{" + "name='" + name + '\'' + ", space=" + size + '}';
    }
}
