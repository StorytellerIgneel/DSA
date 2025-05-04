package dsa.abstractClasses;

import java.util.*;

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
        this.completedAirplanes = new ArrayList<>();
        this.dataHandler = dataHandler;
    }

    @Override
    public List<Airplane> getCompletedAirplanes(){
        return completedAirplanes;
    }

    @Override
    public Map<Cargo, Integer> importCargoData(String filepath){
        return dataHandler.read(filepath);
    }

    @Override
    public void exportPackingResult(String inputFilepath, String outputFilepath){
        dataHandler.write(inputFilepath, outputFilepath, completedAirplanes);
    }

    //can be used for multiple algorithms, so placed in abstract class
    protected List<CargoWrapper> expandCargoItems(Map<Cargo, Integer> cargoMap) {
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

    //general transforming function that can be used for multiple algos too
    public static TreeMap<Integer, List<CargoWrapper>> transformToTreeMap(Map<Cargo, Integer> cargoMap) {
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
