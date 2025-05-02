package dsa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BestFitDecreasing {
    private ArrayList<Airplane> completedAirplanes; //list of airplanes in the airport
    private Airplane availableAirplane; //list of available airplanes in the airport
    private TreeMap<Integer, List<CargoWrapper>> cargoTreeMap;

    public BestFitDecreasing(){
        this.completedAirplanes = new ArrayList<Airplane>(); //initialize the completed airplanes list
        this.availableAirplane = new Airplane();
        this.cargoTreeMap = new TreeMap<>(); //read the cargoes from the excel file
    }

    public BestFitDecreasing(String filepath) {
        this.completedAirplanes = new ArrayList<Airplane>(); //initialize the completed airplanes list
        this.availableAirplane = new Airplane();
        this.cargoTreeMap = transformToTreeMap(ExcelReader.ReadFromExcel("src\\main\\java\\dsa\\airplane_cargo.xlsx")); //read the cargoes from the excel file
    }

    public ArrayList<Airplane> getCompletedAirplanes() {
        return completedAirplanes; //return the completed airplanes list
    }
    
    public Airplane getAvailableAirplane() {
        return availableAirplane; //return the available airplanes list
    }

    public TreeMap<Integer, List<CargoWrapper>> getCargoTreeMap() {
        return cargoTreeMap;
    }

    public void sortCargo(String outputFilename, String outputSheetName){
        while (!cargoTreeMap.isEmpty()) {
            Integer key = cargoTreeMap.firstKey(); // largest key
            List<CargoWrapper> wrappers = cargoTreeMap.get(key);

            while (!wrappers.isEmpty()) {
                Cargo cargo = wrappers.get(0).getCargo();
                loadCargo(cargo);
            }

            if (wrappers.isEmpty()) {
                cargoTreeMap.remove(key);
            }
        }

        //int sum = 0;
        //int limit = 30; // Number of airplanes to show
        // System.out.println("First few completed airplanes:");
        // List<Airplane> completed = bfd.getCompletedAirplanes();
        // for (int i = 0; i < Math.min(limit, completed.size()); i++) {
        //     Airplane plane = completed.get(i);
        //     sum += plane.getCargoListSize();
        //     System.out.println("Airplane #" + (i + 1));
        //     System.out.println(plane);
        // }
        // System.out.println("...");
        // System.out.println("Total Airplanes used: " + completed.size());
        // System.out.println("Total Cargoes loaded: " + sum);

        ExcelReader.exportToExcel("BFD_report.xlsx", getCompletedAirplanes(), "BFD_report");
    }

    //main logic
    //runs similar to recursive, add cargo -> search for cargo with less remaining space -> until no more cargo that fits
    public void loadCargo(Cargo initialCargo) {
        int remainingSpace = 10;
        Airplane airplane = new Airplane();
    
        Cargo currentCargo = initialCargo;
        while (currentCargo != null && remainingSpace >= currentCargo.getSpace()) {
            airplane.addCargo(currentCargo);
            remainingSpace -= currentCargo.getSpace();
            // System.out.println("current cargo: " + currentCargo.getName());
            // System.out.println("remaining space: "+ remainingSpace);
            removeCargoFromMap(currentCargo);
    
            // if (remainingSpace == 6){
            //     System.out.println(cargoTreeMap);
            // }
            Integer fitKey = cargoTreeMap.ceilingKey(remainingSpace);
            // System.out.println("current key: " + fitKey);
            currentCargo = getNextAvailableCargo(fitKey);
        }
    
        completedAirplanes.add(airplane);
    }

    //remove the cargo from the treemap
    //removes the entire key if the list is empty
    private void removeCargoFromMap(Cargo cargo) {
        int space = cargo.getSpace();
        List<CargoWrapper> list = cargoTreeMap.get(space);
    
        if (list == null || list.isEmpty()) return;
    
        CargoWrapper wrapper = list.get(0);
        wrapper.decrement(); // decrease quantity inside the wrapper
    
        if (wrapper.getQuantity() <= 0) {
            list.remove(0); // remove wrapper if used up
        }
    
        if (list.isEmpty()) {
            cargoTreeMap.remove(space);
        }
    }
    
    //return the first cargo in line
    private Cargo getNextAvailableCargo(Integer space) {
        if (space == null) return null;
        List<CargoWrapper> list = cargoTreeMap.get(space);
        if (list == null || list.isEmpty()) return null;
        return list.get(0).getCargo();
    }  

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
}
