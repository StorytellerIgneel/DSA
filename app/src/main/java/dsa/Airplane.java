package dsa;

import java.util.UUID;

import dsa.abstractClasses.Bin;
import dsa.abstractClasses.TransportItem;

public class Airplane extends Bin {
    private final String ID;

    public Airplane() {
        super(10); // default storage space
        this.ID = UUID.randomUUID().toString();
    }

    public Airplane(String ID, int storageSpace) {
        super(storageSpace);
        
        if (ID == null || ID.trim().isEmpty()) {
            throw new IllegalArgumentException("Airplane ID cannot be null or empty.");
        }
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Airplane { \n");
        sb.append("  ID: ").append(this.getID()).append("\n");
        sb.append("  Remaining Space: ").append(this.getStorageSpace()).append("\n");
        sb.append("  Loaded Cargoes: \n");

        for (TransportItem item : this.getCargoList()) {
            sb.append("    - ").append(item.toString()).append("\n");
        }

        sb.append("}\n");
        return sb.toString();
    }
}
