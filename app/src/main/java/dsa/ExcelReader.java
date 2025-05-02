package dsa;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

    public static LinkedHashMap<Cargo, Integer> ReadFromExcel(String filepath) {
        LinkedHashMap<Cargo, Integer> cargoList = new LinkedHashMap<>();

        try {
            FileInputStream file = new FileInputStream(new File(filepath));
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next(); // Skip header

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                String name = row.getCell(1).toString();
                int space = (int) Double.parseDouble(row.getCell(2).toString());
                int quantity = (int) Double.parseDouble(row.getCell(3).toString());

                Cargo cargo = new Cargo(name, space);
                cargoList.put(cargo, quantity);  // Store cargo and its quantity
            }

            workbook.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cargoList;
    }

    public static void exportToExcel(String filepath, List<Airplane> completedAirplanes, String sheetName) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);

        //header row
        Row headerRow = sheet.createRow(0);
        String[] headerStrings = {"Airplane ID", "Cargo Name", "Cargo Space", "Remaining Space"};
        for (int i = 0; i < headerStrings.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headerStrings[i]);
        }

        //start to fill in the rows here
        int rowNum = 1;

        int airplaneNum = 1;

        for (Airplane airplane : completedAirplanes) {
            int startRow = rowNum;

            for (Cargo cargo : airplane.getCargoList()) {
                Row row = sheet.createRow(rowNum);
                rowNum++;
                // 
                row.createCell(1).setCellValue(cargo.getName());
                row.createCell(2).setCellValue(cargo.getSpace());
                row.createCell(3).setCellValue(airplane.getStorageSpace()); // same remaining space repeated
            }

            if (startRow != rowNum - 1) {
                sheet.addMergedRegion(new CellRangeAddress(startRow, rowNum - 1, 0, 0));
            }
            Row firstRow = sheet.getRow(startRow);
            firstRow.createCell(0).setCellValue("Plane #" + airplaneNum);
            airplaneNum++;
        }

        // Autosize columns
        for (int i = 0; i < headerStrings.length; i++) {
            sheet.autoSizeColumn(i);
        }

        //write to file
        try (FileOutputStream fileOut = new FileOutputStream(filepath)) {
            workbook.write(fileOut);
            System.out.println("Report now available in current dir with name: " + filepath);
        } catch (IOException E) {
            E.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
