//concrete implementation of the cargo data handler interface. Variation of excel
//Excelreader will only be accessed here for low coupling
package dsa;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dsa.interfaces.CargoDataHandler;

public class ExcelCargoDataHandler implements CargoDataHandler {

    @Override
    public Map<Cargo, Integer> read(String filepath) {
        if (filepath == null || filepath.trim().isEmpty()) {
            throw new IllegalArgumentException("Filepath cannot be null or empty.");
        }

        try {
            return ExcelReader.readFromExcel(filepath);
        } catch (Exception e) {
            System.err.println("Error reading cargo data from Excel file: " + e.getMessage());
            return new LinkedHashMap<>(); // Return an empty map if an error occurs
        }
    }

    @Override
    public void write(String filepath, String sheetname, List<Airplane> airplanes) {
        if (sheetname == null || sheetname.trim().isEmpty()) {
            throw new IllegalArgumentException("Output filepath cannot be null or empty.");
        }
        if (airplanes == null || airplanes.isEmpty()) {
            throw new IllegalArgumentException("Airplanes list cannot be null or empty.");
        }

        try {
            ExcelReader.exportToExcel(filepath, sheetname, airplanes);
        } catch (Exception e) {
            System.err.println("Error writing packing results to Excel file: " + e.getMessage());
        }
    }
}

