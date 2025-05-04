package dsa.interfaces;

import java.util.*;

import dsa.Airplane;
import dsa.Cargo;

public interface PackingStrategy {
    public void pack();

    public List<Airplane> getCompletedAirplanes();
    
    public Map<Cargo, Integer> importCargoData(String filepath);
    
    public void exportPackingResult(String inputFilepath, String outputFilepath);
}
