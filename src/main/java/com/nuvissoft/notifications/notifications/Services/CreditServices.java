package com.nuvissoft.notifications.notifications.Services;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Data.Domain.Credits;
import Data.Repositories.ExcelRepository;

public class CreditServices implements ExcelRepository<Credits> {

    String filePath = "Jireh Testing (1).xlsx";
    FileInputStream excelFile;
    List<Credits> allCredits;
    XSSFWorkbook creditBook;
    XSSFSheet sheet;
    // CellFormat cellFormat = new CellFormat();

    @Override
    public List<Credits> getAllCreditsWithLate() {
        this.allCredits = new ArrayList<Credits>();
        try {

            this.excelFile = new FileInputStream(this.filePath);
            this.creditBook = new XSSFWorkbook(excelFile);
            this.sheet = this.creditBook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();
            Row currentRow;

            //To avoid titles
            iterator.next();
            System.out.println("Total Rows amount: " + (sheet.getLastRowNum() - 1));

            while (iterator.hasNext()) {

                currentRow = iterator.next();
                if (currentRow.getCell(0) != null) {

                    this.allCredits.add(
                            new Credits(
                                    0,
                                    currentRow.getCell(0).toString(),
                                    currentRow.getCell(1).toString(),
                                    Double.parseDouble(currentRow.getCell(5).toString()),
                                    new GregorianCalendar().getTime(),
                                    currentRow.getCell(6).toString(),
                                    currentRow.getCell(7).toString(),
                                    currentRow.getCell(2).toString().charAt(0)));

                }
            }

        } catch (IOException notFounded) {
            System.err.println(notFounded.getLocalizedMessage());
            notFounded.printStackTrace();
        }

        return this.allCredits;
    }

}
