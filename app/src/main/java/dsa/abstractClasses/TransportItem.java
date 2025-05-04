package dsa.abstractClasses;

import dsa.interfaces.Loadable;

public abstract class TransportItem implements Loadable{
    private final String name;
    private final int size;

    public TransportItem(String name, int size) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be greater than zero.");
        }
        
        this.name = name;
        this.size = size;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{name='" + name + "', size=" + size + '}';
    }
}
