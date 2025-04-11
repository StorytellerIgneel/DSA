package dsa;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
    public static Map<Cargo, Integer> ReadFromExcel(String filepath){
        Map<Cargo, Integer> cargoMap = new HashMap<>(); //for the DS
        //lets say we have 10 books, it would then look like this: (book, 10)
        //the key is the book, and the value is the quantity of the book

        try{
            FileInputStream file = new FileInputStream(new File(filepath));
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            //read header
            Row headerRow = rowIterator.next();
            List<String> header = new ArrayList<>();

            for (Cell cell : headerRow){
                header.add(cell.getStringCellValue()); //register the headers， there  will be 4
            }

            //for each row
            while(rowIterator.hasNext()){
                Row row = rowIterator.next();
                Cell nameCell = row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                Cell spaceCell = row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                Cell quantityCell = row.getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                String name = nameCell.toString();
                int space = (int)Double.parseDouble(spaceCell.toString());
                int quantity = (int) Double.parseDouble(quantityCell.toString());

                Cargo newCargo = new Cargo(name, space);
                cargoMap.put(newCargo, quantity); //put the cargo into the map with its quantity
                //quantity is added directly as we have conformation that the records are unique
            }
            workbook.close();
            file.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return cargoMap;
    }
}
