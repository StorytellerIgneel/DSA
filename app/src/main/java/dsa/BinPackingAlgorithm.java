package dsa;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class BinPackingAlgorithm implements PackingStrategy{
    protected List<Airplane> completedAirplanes;

    protected Map<Cargo, Integer> cargoData;

    public BinPackingAlgorithm(){
        this.completedAirplanes = new ArrayList<>();
    }

    @Override
    public List<Airplane> getCompletedAirplanes(){
        return completedAirplanes;
    }

    @Override
    public LinkedHashMap<Cargo, Integer> importCargoData(String filepath){
        if (filepath == null || filepath.trim().isEmpty()) {
            throw new IllegalArgumentException("Filepath cannot be null or empty.");
        }

        try {
            return ExcelReader.ReadFromExcel(filepath);
        } catch (Exception e) {
            System.err.println("Error importing cargo data from file: " + e.getMessage());
            return new LinkedHashMap<>(); // Return an empty map if an error occurs
        }
    }

    @Override
    public void exportPackingResult(String inputFilepath, String outputFilepath){
        if (outputFilepath == null || outputFilepath.trim().isEmpty()) {
            throw new IllegalArgumentException("Output filepath cannot be null or empty.");
        }

        try {
            ExcelReader.exportToExcel(outputFilepath, "Packing Results", completedAirplanes);
        } catch (Exception e) {
            System.err.println("Error exporting packing results to file: " + e.getMessage());
        }
    }

    @Override
    public abstract void pack();
}
