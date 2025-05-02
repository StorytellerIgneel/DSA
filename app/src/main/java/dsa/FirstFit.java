package dsa;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FirstFit {

    private List<Airplane> completedAirplanes;
    private LinkedHashMap<Cargo, Integer> cargoMap;    // preserves insertion order + quantity
    private List<Cargo> allCargoItems;                  // flattened list for packing

    public FirstFit(String excelPath) {
        this.completedAirplanes = new ArrayList<>();

        // 1. Read into a LinkedHashMap<Cargo, quantity>
        this.cargoMap = ExcelReader.ReadFromExcel("src\\main\\java\\dsa\\airplane_cargo.xlsx");

        // 2. Expand into a flat list of Cargo items in exact Excel order
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

    // 3. First Fit packing
    public void pack() {
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
