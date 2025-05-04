package dsa.abstractClasses;

import dsa.interfaces.Loadable;

public abstract class TransportItem implements Loadable{
    private String name;
    private int size;

    public TransportItem(String name, int size) {
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
