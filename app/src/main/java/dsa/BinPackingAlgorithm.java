package dsa;

import java.util.*;

public abstract class BinPackingAlgorithm implements PackingStrategy{
    protected List<Airplane> completedAirplanes;
    
    protected Map<Cargo, Integer> cargoData;

    @Override
    public List<Airplane> getCompletedAirplanes(){
        return completedAirplanes;
    }

    @Override
    public Map<Cargo, Integer> importCargoData(String filepath){
        return ExcelReader.ReadFromExcel(filepath);
    }

    @Override
    public void exportPackingResult(String inputFilepath, String outputFilepath){
        ExcelReader.exportToExcel(inputFilepath, outputFilepath, completedAirplanes);
    }

    @Override
    public abstract void pack();
}
