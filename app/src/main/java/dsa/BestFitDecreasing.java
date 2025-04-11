package dsa;

import java.util.*;
import dsa.ExcelReader;

public class BestFitDecreasing {
    public static TreeMap<Integer, List<CargoWrapper>> transformToTreeMap(Map<Cargo, Integer> cargoMap){
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
                cargoTreeMap.put(cargo.getSpace(), cargoList); //put the list into the tree map with the space as key
            }
        }
        return cargoTreeMap; //return the tree map
    }

    public static void main(String[] args) {
        Map<Cargo, Integer> cargoMap = new HashMap<>(); 
        cargoMap = ExcelReader.ReadFromExcel("C:\\Users\\Teoh Wei Hong\\Documents\\Programming\\Own study\\DSA\\DSA\\app\\src\\main\\java\\dsa\\airplane_cargo.xlsx"); //read the cargoes from the excel file
        TreeMap<Integer, List<CargoWrapper>> cargoTreeMap = transformToTreeMap(cargoMap); //transform the map to a tree map

        System.out.println("Cargo Tree Map: " + cargoTreeMap); //print the tree map
    }
}
