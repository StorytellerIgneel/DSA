package dsa;

import java.util.ArrayList;
import java.util.UUID;

public class Airplane {
    private final String ID;
    private int storageSpace;
    private ArrayList<Cargo> cargoList;

    public Airplane() {
        this.ID = UUID.randomUUID().toString(); //generate a random ID for the airplane
        this.storageSpace = 10; //set the storage space to 10
        this.cargoList = new ArrayList<>(); //initialize the cargo list with the storage space
    }

    public Airplane(String ID, int storageSpace, Cargo cargo) {
        if (ID == null || ID.isEmpty()) {
            throw new IllegalArgumentException("Airplane ID cannot be null or empty.");
        }

        if (storageSpace <= 0) {
            throw new IllegalArgumentException("Storage space must be greater than zero.");
        }

        this.ID = ID;
        this.storageSpace = storageSpace;
        this.cargoList = new ArrayList<>(); //initialize the cargo list with the storage space
    }

    public String getID() {
        return ID;
    }

    public int getStorageSpace() {
        return storageSpace;
    }

    public ArrayList<Cargo> getCargoList() {
        return cargoList;
    }

    public int getCargoListSize() {
        return cargoList.size(); //return the size of the cargo list
    }

    public void setCargoList(ArrayList<Cargo> cargoList) {
        this.cargoList = cargoList;
    }

    public void addCargo(Cargo cargo) {
        if (cargo == null) {
            throw new IllegalArgumentException("Cargo cannot be null.");
        }

        if (cargo.getSpace() > this.storageSpace) {
            throw new IllegalArgumentException("Not enough storage space to add this cargo.");
        }
        
        cargoList.add(cargo); //add the cargo to the list
        this.storageSpace -= cargo.getSpace(); //decrease the storage space by the cargo space
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Airplane { \n");
        sb.append("  Remaining Space: ").append(this.getStorageSpace()).append("\n");
        sb.append("  Loaded Cargoes: \n");

        for (Cargo cargo : this.getCargoList()) {
            sb.append("    - Cargo(space=").append(cargo.getSpace()).append(", id=")
            .append(cargo.getName()).append(")\n"); // assuming `id` or similar exists
        }

        sb.append("}\n");
        return sb.toString();
    }

}