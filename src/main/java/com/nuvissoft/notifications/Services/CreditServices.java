package com.nuvissoft.notifications.Services;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.nuvissoft.notifications.Data.Domain.Credits;
import com.nuvissoft.notifications.Data.Repositories.ExcelRepository;
import com.nuvissoft.notifications.Utilities.Environment;

public class CreditServices implements ExcelRepository<Credits> {

    String filePath = Environment.env.get("XLSX_PATH_FILE");
    FileInputStream excelFile;
    List<Credits> allCredits;

    XSSFWorkbook creditBook;
    XSSFSheet sheet;

    @Override
    public List<Credits> getAllCreditsWithLate() {

        this.allCredits = new ArrayList<Credits>();

        try {

            this.excelFile = new FileInputStream(this.filePath);
            this.creditBook = new XSSFWorkbook(excelFile);
            this.sheet = this.creditBook.getSheetAt(0);
            XSSFRow xssfRow;

            for (int rowIndex = 1; rowIndex <= this.sheet.getLastRowNum(); rowIndex++) {

                xssfRow = this.sheet.getRow(rowIndex);
                this.allCredits.add(this.fillCreditObject(xssfRow));

            }

            this.creditBook.close();
            this.excelFile.close();

        } catch (IOException notFounded) {

            System.err.println(notFounded.getLocalizedMessage());

            notFounded.printStackTrace();
        }

        return this.allCredits;
    }

    private Credits fillCreditObject(XSSFRow row) {

        XSSFCell cellPhoneNumber = row.getCell(7);

        cellPhoneNumber.setCellType(CellType.STRING);
        System.out.println(row.getCell(9).getCellType().name());

        Credits credit;

        credit = new Credits(
                row.getCell(0).getStringCellValue(),
                row.getCell(1).getStringCellValue(),
                row.getCell(2).getStringCellValue(),
                row.getCell(3).getStringCellValue(),
                row.getCell(4).getStringCellValue(),
                row.getCell(5).getStringCellValue(),
                row.getCell(6).getStringCellValue(),
                cellPhoneNumber.getStringCellValue(),
                Integer.parseInt(
                        row.getCell(8).getRawValue()),
                Double.parseDouble(
                        row.getCell(9).getRawValue()));

        return credit;

    }

}
