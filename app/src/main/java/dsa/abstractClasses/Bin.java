package dsa.abstractClasses;

import java.util.ArrayList;
import java.util.List;

public abstract class Bin {
    protected int storageSpace;
    protected List<TransportItem> cargoList;

    public Bin(int storageSpace) {
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
        this.cargoList = cargoList;
    }

    public void addItem(TransportItem item) {
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
