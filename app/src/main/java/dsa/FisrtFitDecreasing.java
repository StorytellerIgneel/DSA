package dsa;

import java.util.*;

public class FisrtFitDecreasing {

    private List<Airplane> completedAirplanes; //list of airplanes in the airport
    private TreeMap<Integer, List<CargoWrapper>> cargoTreeMap;

    public FisrtFitDecreasing() {
        this.completedAirplanes = new ArrayList<>(); //initialize the completed airplanes list
        this.cargoTreeMap = transformToTreeMap(ExcelReader.ReadFromExcel("C:\\Users\\User\\Documents\\Y2S3\\PROBLEM SOLVING\\Assignment\\DSA\\app\\src\\main\\java\\dsa\\airplane_cargo.xlsx")); //read the cargoes from the excel file
    }

    public List<Airplane> getCompletedAirplanes() {
        return completedAirplanes; //return the completed airplanes list
    }

    public TreeMap<Integer, List<CargoWrapper>> getCargoTreeMap() {
        return cargoTreeMap;
    }

    // sort cargo items in descending order based on their space
    public List<Cargo> sortCargoList(List<Cargo> cargoList) {
        Collections.sort(cargoList, (c1, c2) -> Integer.compare(c2.getSpace(), c1.getSpace()));
        return cargoList;
    }

    // expand all cargo items into a list of individual cargo items
    public List<Cargo> expandCargoItems() {
        List<Cargo> allCargoItems = new ArrayList<>();

        for (Map.Entry<Integer, List<CargoWrapper>> entry : cargoTreeMap.entrySet()) {
            for (CargoWrapper wrapper : entry.getValue()) {
                for (int i = 0; i < wrapper.getQuantity(); i++) {
                    allCargoItems.add(wrapper.getCargo());
                }
            }
        }
        return allCargoItems;
    }

    // FFD packing algorithm
    public void FFDPacking() {

        List<Cargo> allCargoItems = expandCargoItems();
        sortCargoList(allCargoItems);

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

    //transform the cargo map to a tree map
    public static TreeMap<Integer, List<CargoWrapper>> transformToTreeMap(Map<Cargo, Integer> cargoMap) {
        TreeMap<Integer, List<CargoWrapper>> cargoTreeMap = new TreeMap<>(Collections.reverseOrder()); //create a tree map with reverse order

        for (Map.Entry<Cargo, Integer> entry : cargoMap.entrySet()) { //for each entry in the map
            Cargo cargo = entry.getKey(); //get the cargo
            int quantity = entry.getValue(); //get the quantity
            CargoWrapper cargoWrapper = new CargoWrapper(cargo, quantity); //create a new cargo wrapper

            if (cargoTreeMap.containsKey(cargo.getSpace())) { //if the tree map already contains the space of the cargo
                cargoTreeMap.get(cargo.getSpace()).add(cargoWrapper); //add the cargo wrapper to the list of that space
            } else {
                List<CargoWrapper> cargoList = new ArrayList<>(); //create a new list of cargo wrappers
                cargoList.add(cargoWrapper); //add the cargo wrapper to the list
                cargoTreeMap.put(cargo.getSpace(), cargoList); //put the list in the tree map
            }
        }
        return cargoTreeMap; //return the tree map
    }
}
