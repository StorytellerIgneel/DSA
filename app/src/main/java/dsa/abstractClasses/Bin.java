package dsa.abstractClasses;

import java.util.ArrayList;
import java.util.List;

public abstract class Bin {
    protected int storageSpace;
    protected List<TransportItem> cargoList;

    public Bin(int storageSpace) {
        if (storageSpace <= 0) {
            throw new IllegalArgumentException("Storage space must be greater than zero.");
        }
        this.storageSpace = storageSpace;
        this.cargoList = new ArrayList<>();
    }

    public int getStorageSpace() {
        return storageSpace;
    }

    public List<TransportItem> getCargoList() {
        return cargoList;
    }

    public int getCargoListSize() {
        return cargoList.size();
    }

    public void setCargoList(List<TransportItem> cargoList) {
        if (cargoList == null) {
            throw new IllegalArgumentException("Cargo list cannot be null.");
        }
        this.cargoList = cargoList;
    }

    public void addItem(TransportItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Transport item cannot be null.");
        }
        if (item.getSize() > this.storageSpace) {
            throw new IllegalArgumentException("Not enough storage space to add this item.");
        }
        cargoList.add(item);
        this.storageSpace -= item.getSize();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Bin { \n");
        sb.append("  Remaining Space: ").append(this.getStorageSpace()).append("\n");
        sb.append("  Loaded Items: \n");

        for (TransportItem item : this.getCargoList()) {
            sb.append("    - ").append(item.toString()).append("\n");
        }

        sb.append("}\n");
        return sb.toString();
    }
}
