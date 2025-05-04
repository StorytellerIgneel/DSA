//interface for the cargo data handler as part of SRP concept

package dsa;

import java.util.*;

public interface CargoDataHandler {
    Map<Cargo, Integer> read(String filepath);
    void write(String inputFilePath, String outputFilePath, List<Airplane> airplanes);
}
