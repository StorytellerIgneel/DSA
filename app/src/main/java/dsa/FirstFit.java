package dsa;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FirstFit extends BinPackingAlgorithm {
    private LinkedHashMap<Cargo, Integer> cargoMap; // Map of cargo items and their quantities; LinkedHashMap to maintain insertion order
    private List<Cargo> allCargoItems; // Flat list of all cargo items to be packed

    public FirstFit(String filePath)
    {
        this.completedAirplanes = new ArrayList<>(); // Initialize the completed airplanes list
        this.cargoMap = ExcelReader.ReadFromExcel(filePath); // Read the cargoes from the excel file
        this.allCargoItems = expandCargoItems(cargoMap); // Expand the cargo map into a flat list of cargo items
    }

    // Method to expand the LinkedHashMap into a flat list of Cargo items (a list of individual Cargo items)
    private List<Cargo> expandCargoItems(LinkedHashMap<Cargo, Integer> cargoMap)
    {
        List<Cargo> expandedCargoItems = new ArrayList<>(); // List to store the expanded cargo items

        // Iterate through the cargoMap and add each Cargo item to the expanded list based on its quantity
        for (Map.Entry<Cargo, Integer> entry : cargoMap.entrySet()) {
            Cargo cargo = entry.getKey();
            int quantity = entry.getValue();
            for (int i = 0; i < quantity; i++) {
                expandedCargoItems.add(new Cargo(cargo.getName(), cargo.getSpace()));
            }
        }
        return expandedCargoItems;
    }

    // First Fit packing: apply the First Fit algorithm to pack the cargo items into airplanes
    public void pack()
    {
        // Iterate through all cargo items and try to place them in the completed airplanes
        // If no airplane can accommodate the cargo, create a new airplane and place the cargo in it
        for (Cargo cargo : allCargoItems) {
            boolean placed = false;
            for (Airplane plane : completedAirplanes) {
                if (plane.getStorageSpace() >= cargo.getSpace()) {
                    plane.addCargo(cargo);
                    placed = true;
                    break;
                }
            }
            if (!placed) {
                Airplane newPlane = new Airplane();
                newPlane.addCargo(cargo);
                completedAirplanes.add(newPlane);
            }
        }
    }
}
