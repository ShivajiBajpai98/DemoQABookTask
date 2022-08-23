package utilites;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ExcelReaders
{
    public FileInputStream fileInputStream;

    public XSSFWorkbook workbook;
    public XSSFSheet sheet;
    public XSSFRow row;
    public XSSFCell cell;
    public CellType style;
    int rowCount;
    int cellCount;
    String data;
    String path;

   public ExcelReaders(String path)
    {
        this.path=path;
    }

    public int getRowCount(String sheetName)
    {
        try {
            fileInputStream=new FileInputStream(path);
            workbook=new XSSFWorkbook(fileInputStream);
            sheet=workbook.getSheet(sheetName);
           rowCount = sheet.getLastRowNum();
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rowCount;
    }

    public int getCellCount(String sheetName, int rowNum)
    {
        try {
            fileInputStream=new FileInputStream(path);
            workbook=new XSSFWorkbook(fileInputStream);
            sheet=workbook.getSheet(sheetName);
           row= sheet.getRow(rowNum);
        cellCount=row.getLastCellNum();
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cellCount;
    }

    public String getCellData(String sheetName, int rownum, int colnum)
    {
        try {
            fileInputStream=new FileInputStream(path);
            workbook =new XSSFWorkbook(fileInputStream);
            sheet=workbook.getSheet(sheetName);
            row=sheet.getRow(rownum);
            cell=row.getCell(colnum);

            DataFormatter formatter= new DataFormatter();

            try {
                data=formatter.formatCellValue(cell);
            }
            catch (Exception e)
            {
                data="";
            }
            workbook.close();
            fileInputStream.close();



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }




    public static void main(String[] args) {

        ExcelReaders xlsxUtility = new ExcelReaders("src/test/java/resources/BookData.xlsx");
      int i=  xlsxUtility.getRowCount("Book");
        System.out.println(i);
       int j= xlsxUtility.getCellCount("Book",1);
        System.out.println(j);

    }

}
