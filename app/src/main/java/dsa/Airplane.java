package dsa;

import java.util.ArrayList;
import java.util.UUID;

public class Airplane {
    private String ID;
    private int storageSpace;
    private ArrayList<Cargo> cargoList;

    public Airplane() {
        this.ID = UUID.randomUUID().toString(); //generate a random ID for the airplane
        this.storageSpace = 100; //set the storage space to 10
        this.cargoList = new ArrayList<>(); //initialize the cargo list with the storage space
    }

    public Airplane(String ID, int storageSpace, Cargo cargo) {
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