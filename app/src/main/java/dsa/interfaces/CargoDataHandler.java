//interface for the cargo data handler as part of SRP concept

package dsa.interfaces;

import java.util.*;

import dsa.Airplane;
import dsa.Cargo;

public interface CargoDataHandler {
    Map<Cargo, Integer> read(String filepath);
    void write(String inputFilePath, String outputFilePath, List<Airplane> airplanes);
}
