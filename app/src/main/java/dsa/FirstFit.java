package dsa;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FirstFit {

    private List<Airplane> completedAirplanes;
    private LinkedHashMap<Cargo, Integer> cargoMap;
    private List<Cargo> allCargoItems;

    public FirstFit() {
        this.completedAirplanes = new ArrayList<>();

        this.cargoMap = ExcelReader.ReadFromExcel("C:\\Users\\Teoh Wei Hong\\Documents\\Programming\\Own study\\DSA\\DSA\\app\\src\\main\\java\\dsa\\airplane_cargo.xlsx");

        //Expand into a flat list of Cargo items in exact Excel order
        this.allCargoItems = new ArrayList<>();
        for (Map.Entry<Cargo, Integer> e : cargoMap.entrySet()) {
            Cargo c = e.getKey();
            int qty = e.getValue();
            for (int i = 0; i < qty; i++) {
                allCargoItems.add(c);
            }
        }
    }

    public List<Airplane> getCompletedAirplanes() {
        return completedAirplanes;
    }

    // First Fit packing
    public void FFPacking() {
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
