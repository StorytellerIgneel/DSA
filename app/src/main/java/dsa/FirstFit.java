package dsa;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FirstFit extends BinPackingAlgorithm {

    private LinkedHashMap<Cargo, Integer> cargoMap; // Map of cargo items and their quantities; maintains insertion order
    private List<CargoWrapper> cargoWrapperList; // List of CargoWrapper objects, each representing a cargo item and its quantity

    public FirstFit() {
        this.completedAirplanes = new ArrayList<>();
        this.cargoMap = new LinkedHashMap<>();
        this.cargoWrapperList = new ArrayList<>();
    }

    public FirstFit(String filePath) {
        super();
        this.cargoMap = importCargoData(filePath); // Read the cargoes from the Excel file
        this.cargoWrapperList = expandCargoItems(cargoMap); // Expand the cargo map into a flat list of cargo items
        this.completedAirplanes = new ArrayList<>(); // List of completed airplanes
    }

    @Override
    // First Fit packing: apply the First Fit algorithm to pack the cargo items into airplanes
    public void pack() {
        if (cargoWrapperList == null || cargoWrapperList.isEmpty()) {
            System.err.println("No cargo items to pack.");
            return;
        }

        for (CargoWrapper wrapper : cargoWrapperList) {
            while (wrapper.getQuantity() > 0) {
                boolean placed = false;
                for (Airplane plane : completedAirplanes) {
                    try {
                        if (plane.getStorageSpace() >= wrapper.getCargo().getSpace()) {
                            plane.addCargo(new Cargo(wrapper.getCargo().getName(), wrapper.getCargo().getSpace()));
                            wrapper.decrement();
                            placed = true;
                            break;
                        }
                    } catch (Exception e) {
                        System.err.println("Error while adding cargo to airplane: " + e.getMessage());
                    }
                }
                try {
                    Airplane newPlane = new Airplane();
                    newPlane.addCargo(new Cargo(wrapper.getCargo().getName(), wrapper.getCargo().getSpace()));
                    wrapper.decrement();
                    completedAirplanes.add(newPlane);
                } catch (Exception e) {
                    System.err.println("Error while creating a new airplane or adding cargo: " + e.getMessage());
                }
            }
        }
    }

    // Convert the cargo map into a list of CargoWrapper items
    private List<CargoWrapper> expandCargoItems(LinkedHashMap<Cargo, Integer> cargoMap) {
        List<CargoWrapper> expandedCargoItems = new ArrayList<>();
        
        for (Map.Entry<Cargo, Integer> entry : cargoMap.entrySet()) {

            if (entry.getKey() == null || entry.getValue() == null) {
                System.err.println("Skipping invalid cargo entry: null value found.");
                continue;
            }

            if (entry.getValue() <= 0) {
                System.err.println("Skipping cargo with non-positive quantity: " + entry.getKey().getName());
                continue;
            }

            expandedCargoItems.add(new CargoWrapper(entry.getKey(), entry.getValue()));
        }
        return expandedCargoItems;
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
