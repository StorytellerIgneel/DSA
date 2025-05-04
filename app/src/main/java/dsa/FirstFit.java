package dsa;

import java.util.*;

import dsa.abstractClasses.BinPackingAlgorithm;

public class FirstFit extends BinPackingAlgorithm {

    private LinkedHashMap<Cargo, Integer> cargoMap; // Map of cargo items and their quantities; maintains insertion
                                                    // order
    private List<CargoWrapper> cargoWrapperList; // List of CargoWrapper objects, each representing a cargo item and its
                                                 // quantity

    public FirstFit() {
        super(new ExcelCargoDataHandler());
        this.completedAirplanes = new ArrayList<>();
        this.cargoMap = new LinkedHashMap<>();
        this.cargoWrapperList = new ArrayList<>();
    }

    public FirstFit(ExcelCargoDataHandler dataHandler, String filePath) {
        super(dataHandler);
        this.cargoMap = new LinkedHashMap<>(importCargoData(filePath)); // Read the cargoes from the Excel file
        this.cargoWrapperList = expandCargoItems(cargoMap); // Expand the cargo map into a flat list of cargo items
        this.completedAirplanes = new ArrayList<>(); // List of completed airplanes
    }

    @Override
    // First Fit packing: apply the First Fit algorithm to pack the cargo items into
    // airplanes
    public void pack() {
        if (cargoWrapperList == null || cargoWrapperList.isEmpty()) {
            System.err.println("No cargo items to pack.");
            return;
        }

        for (CargoWrapper wrapper : cargoWrapperList) {
            while (wrapper.getQuantity() > 0) {
                boolean placed = false;

                for (Airplane plane : completedAirplanes) {
                    if (plane.getStorageSpace() >= wrapper.getCargo().getSize()) {
                        try {
                            plane.addItem(new Cargo(wrapper.getCargo().getName(), wrapper.getCargo().getSize()));
                            wrapper.decrement();
                            placed = true;
                            break;
                        } catch (Exception e) {
                            System.err.println("Failed to add cargo to existing airplane: " + e.getMessage());
                        }
                    }
                }

                if (!placed) {
                    try {
                        Airplane newPlane = new Airplane();
                        newPlane.addItem(new Cargo(wrapper.getCargo().getName(), wrapper.getCargo().getSize()));
                        wrapper.decrement();
                        completedAirplanes.add(newPlane);
                    } catch (Exception e) {
                        System.err.println("Error while creating a new airplane or adding cargo: " + e.getMessage());
                    }
                }
            }
        }
    }

    public LinkedHashMap<Cargo, Integer> getCargoMap() {
        return cargoMap;
    }

    public void setCargoMap(LinkedHashMap<Cargo, Integer> cargoMap) {
        if (cargoMap == null) {
            throw new IllegalArgumentException("Cargo map cannot be null.");
        }

        this.cargoMap = cargoMap;
    }

    public List<CargoWrapper> getAllCargoItems() {
        return cargoWrapperList;
    }

    public void setAllCargoItems(List<CargoWrapper> cargoWrapperList) {
        if (cargoWrapperList == null) {
            throw new IllegalArgumentException("Cargo wrapper list cannot be null.");
        }

        this.cargoWrapperList = cargoWrapperList;
    }
}
