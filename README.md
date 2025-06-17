# ✈️ Airplane Cargo Bin Packing (Java DSA Project)

A Java-based simulation of the **Bin Packing Problem**, using an airplane-cargo scenario where cargo items (e.g., luggage) are packed into airplanes (bins) using two classic algorithms: **First Fit (FF)** and **Best Fit Decreasing (BFD)**.

The program reads item data from an Excel sheet, processes it with both algorithms, and exports the bin packing results and performance metrics to a new Excel file.

---

## 🚀 Features

- Implements **First Fit** and **Best Fit Decreasing** bin packing strategies
- Accepts item input and writes output using **Apache POI** (Excel file I/O)
- Calculates performance metrics like **number of bins used** for comparison
- Error checking for invalid inputs
- Customizable airplane (bin) capacity
- Demonstrates **Object-Oriented Analysis and Design (OOAD)**:
  - Uses **interfaces**
  - **Abstract classes**
  - Clean separation of algorithms and data logic

---

## 📂 Project Structure

```
src/
├── main/
│   └── java/
│       └── dsa/
│           ├── algorithms/
│           │   ├── BinPackingStrategy.java    // Interface
│           │   ├── FirstFit.java              // First Fit implementation
│           │   └── BestFitDecreasing.java     // BFD implementation
│           ├── model/
│           │   ├── Airplane.java              // Bin abstraction
│           │   └── Cargo.java                 // Item data
│           └── Main.java                      // Entry point
│           └── airplane_cargo.xlsx            // Input Excel file
```

---

## 📥 Input Format

Excel file: `airplane_cargo.xlsx` (located in `src/main/java/dsa/`)

| Row No. | Name              | Space | Quantity |
| ------- | ----------------- | ----- | -------- |
| 1       | Passenger Luggage | 2     | 14       |

- **Name**: Unique cargo item name
- **Space**: Volume per unit
- **Quantity**: Number of items

> ⚙️ To change airplane (bin) capacity:\
> Edit the constructor value in `Airplane.java` (default: 10 units)

---

## 📤 Output Format

After running the program, the results are exported to a new Excel file, which includes:

- Which items are placed in which bins
- Total number of bins used by each algorithm (FF vs BFD)

---

## 🛠 How to Run

1. Clone or download the repository
2. Open the project in your preferred Java IDE (e.g., IntelliJ, Eclipse)
3. Ensure **Apache POI** is installed and in your classpath
4. Run `Main.java`

> 🔪 No command-line arguments needed — just edit the Excel file and run.

---

## 📊 Sample Output Snapshot

| Algorithm | Total Bins Used |
| --------- | --------------- |
| First Fit | 5               |
| BFD       | 4               |

Each sheet in the output Excel also details the distribution of items per bin.

---

## 💡 OOAD Concepts Demonstrated

- `BinPackingStrategy` interface allows for easy addition of new algorithms
- Abstract `Airplane` class encapsulates bin logic
- Separation of concerns between model, algorithm, and I/O layers

---

## 🧰 Technologies Used

- Java (JDK 8+)
- Apache POI (Excel Read/Write)
- OOP Design (Interfaces, Abstraction)

---

## 📜 License

This project is for educational and portfolio purposes. Feel free to fork or adapt.

