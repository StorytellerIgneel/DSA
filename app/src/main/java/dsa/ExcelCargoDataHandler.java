//concrete implementation of the cargo data handler interface. Variation of excel
//Excelreader will only be accessed here for low coupling
package dsa;

import java.util.*;

public class ExcelCargoDataHandler implements CargoDataHandler {
    public Map<Cargo, Integer> read(String filepath) {
        return ExcelReader.ReadFromExcel(filepath);
    }

    public void write(String inputFilePath, String outputFilePath, List<Airplane> airplanes) {
        ExcelReader.exportToExcel(inputFilePath, outputFilePath, airplanes);
    }
}

