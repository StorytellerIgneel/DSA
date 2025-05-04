package dsa;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import dsa.abstractClasses.TransportItem;

public class ExcelReader {
    public static LinkedHashMap<Cargo, Integer> ReadFromExcel(String filepath) {
        LinkedHashMap<Cargo, Integer> cargoMap = new LinkedHashMap<>();
        // Map<Cargo, Integer> cargoMap = new HashMap<>(); //for the DS
        // lets say we have 10 books, it would then look like this: (book, 10)
        // the key is the book, and the value is the quantity of the book

        try {
            FileInputStream file = new FileInputStream(new File(filepath));
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            // read header
            Row headerRow = rowIterator.next();
            List<String> header = new ArrayList<>();

            for (Cell cell : headerRow) {
                header.add(cell.getStringCellValue()); // register the headers， there will be 4
            }

            // for each row
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Cell nameCell = row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                Cell spaceCell = row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                Cell quantityCell = row.getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                String name = nameCell.toString();
                int space = (int) Double.parseDouble(spaceCell.toString());
                int quantity = (int) Double.parseDouble(quantityCell.toString());

                Cargo newCargo = new Cargo(name, space);
                cargoMap.put(newCargo, quantity); // put the cargo into the map with its quantity
                // quantity is added directly as we have conformation that the records are
                // unique
            }
            workbook.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cargoMap;
    }

    public static void exportToExcel(String filepath, String sheetName, List<Airplane> completedAirplanes) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);

        // header row
        Row headerRow = sheet.createRow(0);
        String[] headerStrings = { "Airplane ID", "Cargo Name", "Cargo Space", "Remaining Space" };
        for (int i = 0; i < headerStrings.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headerStrings[i]);
        }

        // start to fill in the rows here
        int rowNum = 1;

        int airplaneNum = 1;

        for (Airplane airplane : completedAirplanes) {
            int startRow = rowNum;

            for (TransportItem cargo : airplane.getCargoList()) {
                Row row = sheet.createRow(rowNum);
                rowNum++;
                //
                row.createCell(1).setCellValue(cargo.getName());
                row.createCell(2).setCellValue(cargo.getSize());
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

        // write to file
        try (FileOutputStream fileOut = new FileOutputStream(filepath)) {
            workbook.write(fileOut);
            System.out.println("Report now available in current dir with name: " + filepath);
        } catch (IOException E) {
            E.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                System.err.println("Error closing the workbook: " + e.getMessage());
            }
        }
    }
}
