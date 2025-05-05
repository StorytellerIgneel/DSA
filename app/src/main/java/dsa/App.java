/*
 * Test program to call the algos and export the results to excel
 */
package dsa;

public class App {

    public static void main(String[] args) {
        ExcelCargoDataHandler handler = new ExcelCargoDataHandler();

        BestFitDecreasing bfd = new BestFitDecreasing(handler, "app\\src\\main\\java\\dsa\\airplane_cargo.xlsx");
        bfd.pack();
        bfd.exportPackingResult("BFD_report.xlsx", "Test Case A BC10");

        // FF
        FirstFit ff = new FirstFit(handler, "app\\src\\main\\java\\dsa\\airplane_cargo.xlsx");
        ff.pack();
        ff.exportPackingResult("FirstFit_report.xlsx", "Test Case A BC10");
    }

}
