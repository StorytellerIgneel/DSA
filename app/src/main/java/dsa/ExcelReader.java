package dsa;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.PriorityQueue;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
    public static PriorityQueue<Cargo> ReadFromExcel(String filepath){
        PriorityQueue<Cargo> queue = new PriorityQueue<>();

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

                for (int i = 0; i < quantity; i++){
                    Cargo newCargo = new Cargo(name, space);
                    System.out.println(newCargo.getName());
                    System.out.println(newCargo.getSpace());
                    //add inside DS
                }
                workbook.close();
                file.close();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return queue;
    }
}
