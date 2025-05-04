package dsa;

import java.util.*;

import dsa.abstractClasses.BinPackingAlgorithm;

public class BestFitDecreasing extends BinPackingAlgorithm {
    private TreeMap<Integer, List<CargoWrapper>> cargoTreeMap;

    public BestFitDecreasing(){
        super(new ExcelCargoDataHandler());
        this.cargoTreeMap = new TreeMap<>();
    }

    public BestFitDecreasing(ExcelCargoDataHandler dataHandler) {
        super(dataHandler);
        this.cargoTreeMap = new TreeMap<>(); // read the cargoes from the excel file
    }

    public BestFitDecreasing(ExcelCargoDataHandler dataHandler, String filepath) {
        super(dataHandler);
        this.cargoTreeMap = transformToTreeMap(importCargoData(filepath)); // read the cargoes from the excel file
    }

    @Override
    public void pack() {
        while (!cargoTreeMap.isEmpty()) {
            Integer key = cargoTreeMap.firstKey(); // largest key
            List<CargoWrapper> wrappers = cargoTreeMap.get(key);

            while (!wrappers.isEmpty()) {
                Cargo cargo = wrappers.get(0).getCargo();
                packToAirplane(cargo);
            }

            if (wrappers.isEmpty()) {
                cargoTreeMap.remove(key);
            }
        }
    }

    // main logic
    // runs similar to recursive, add cargo -> search for cargo with less remaining
    // space -> until no more cargo that fits
    public void packToAirplane(Cargo initialCargo) {
        int remainingSpace = 10;
        Airplane airplane = new Airplane();

        Cargo currentCargo = initialCargo;
        while (currentCargo != null && remainingSpace >= currentCargo.getSize()) {
            airplane.addItem(currentCargo);
            remainingSpace -= currentCargo.getSize();
            removeCargoFromMap(currentCargo);
            Integer fitKey = cargoTreeMap.ceilingKey(remainingSpace);

            currentCargo = getNextAvailableCargo(fitKey);
        }

        completedAirplanes.add(airplane);
    }

    // remove the cargo from the treemap
    // removes the entire key if the list is empty
    private void removeCargoFromMap(Cargo cargo) {
        int space = cargo.getSize();
        List<CargoWrapper> list = cargoTreeMap.get(space);

        if (list == null || list.isEmpty())
            return;

        CargoWrapper wrapper = list.get(0);
        wrapper.decrement(); // decrease quantity inside the wrapper

        if (wrapper.getQuantity() <= 0) {
            list.remove(0); // remove wrapper if used up
        }

        if (list.isEmpty()) {
            cargoTreeMap.remove(space);
        }
    }

    // return the first cargo in line
    private Cargo getNextAvailableCargo(Integer space) {
        if (space == null)
            return null;
        List<CargoWrapper> list = cargoTreeMap.get(space);
        if (list == null || list.isEmpty())
            return null;
        return list.get(0).getCargo();
    }

    public TreeMap<Integer, List<CargoWrapper>> getCargoTreeMap() {
        return cargoTreeMap;
    }

    public void setCargoTreeMap(TreeMap<Integer, List<CargoWrapper>> cargoTreeMap) {
        this.cargoTreeMap = cargoTreeMap;
    }
}
