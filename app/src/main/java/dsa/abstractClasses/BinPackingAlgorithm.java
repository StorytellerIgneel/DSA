//abstract class for all Algorithms

package dsa.abstractClasses;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import dsa.Airplane;
import dsa.Cargo;
import dsa.CargoWrapper;
import dsa.interfaces.CargoDataHandler;
import dsa.interfaces.PackingStrategy;

public abstract class BinPackingAlgorithm implements PackingStrategy{
    protected List<Airplane> completedAirplanes;
    protected Map<Cargo, Integer> cargoData;
    protected CargoDataHandler dataHandler;

    public BinPackingAlgorithm(CargoDataHandler dataHandler){
        if (dataHandler == null) {
            throw new IllegalArgumentException("Data handler cannot be null.");
        }
        this.completedAirplanes = new ArrayList<>();
        this.dataHandler = dataHandler;
    }

    @Override
    public List<Airplane> getCompletedAirplanes(){
        return completedAirplanes;
    }

    @Override
    public Map<Cargo, Integer> importCargoData(String filepath){
        if (filepath == null || filepath.trim().isEmpty()) {
            throw new IllegalArgumentException("Filepath cannot be null or empty.");
        }

        try {
            return dataHandler.read(filepath);
        } catch (Exception e) {
            System.err.println("Error importing cargo data from file: " + e.getMessage());
            return new LinkedHashMap<>(); // Return an empty map if an error occurs
        }
    }

    @Override
    public void exportPackingResult(String filepath, String sheetname){
        if (sheetname == null || sheetname.trim().isEmpty()) {
            throw new IllegalArgumentException("Sheetname cannot be null or empty.");
        }

        try {
            dataHandler.write(filepath, sheetname, completedAirplanes);
        } catch (Exception e) {
            System.err.println("Error exporting packing results to file: " + e.getMessage());
        }
    }

    //can be used for multiple algorithms, so placed in abstract class
    protected List<CargoWrapper> expandCargoItems(Map<Cargo, Integer> cargoMap) {
        if (cargoMap == null) {
            throw new IllegalArgumentException("Cargo map cannot be null.");
        }

        List<CargoWrapper> expandedCargoItems = new ArrayList<>();
        for (Map.Entry<Cargo, Integer> entry : cargoMap.entrySet()) {
            try {
                if (entry.getKey() == null || entry.getValue() == null) {
                    throw new IllegalArgumentException("Invalid cargo entry: null value found.");
                }
                if (entry.getValue() <= 0) {
                    throw new IllegalArgumentException("Invalid cargo quantity for " + entry.getKey().getName() + ": must be greater than zero.");
                }
                expandedCargoItems.add(new CargoWrapper(entry.getKey(), entry.getValue()));
            } catch (IllegalArgumentException e) {
                System.err.println("Skipping invalid cargo entry: " + e.getMessage());
            }
        }
        return expandedCargoItems;
    }

    //general transforming function that can be used for multiple algos too
    public static TreeMap<Integer, List<CargoWrapper>> transformToTreeMap(Map<Cargo, Integer> cargoMap) {
        if (cargoMap == null) {
            throw new IllegalArgumentException("Cargo map cannot be null.");
        }
        
        TreeMap<Integer, List<CargoWrapper>> cargoTreeMap = new TreeMap<>(Comparator.reverseOrder()); // create a tree
                                                                                                      // map with
                                                                                                      // reverse order

        for (Map.Entry<Cargo, Integer> entry : cargoMap.entrySet()) { // for each entry in the map
            Cargo cargo = entry.getKey(); // get the cargo
            int quantity = entry.getValue(); // get the quantity
            CargoWrapper cargoWrapper = new CargoWrapper(cargo, quantity); // create a new cargo wrapper

            if (cargoTreeMap.containsKey(cargo.getSize())) { // if the tree map already contains the space of the cargo
                cargoTreeMap.get(cargo.getSize()).add(cargoWrapper); // add the cargo wrapper to the list of that space
            } else {
                List<CargoWrapper> cargoList = new ArrayList<>(); // create a new list of cargo wrappers
                cargoList.add(cargoWrapper); // add the cargo wrapper to the list
                cargoTreeMap.put(cargo.getSize(), cargoList); // put the list into the tree map with the space as key
            }
        }
        return cargoTreeMap; // return the tree map
    }

    @Override
    public abstract void pack();
}
