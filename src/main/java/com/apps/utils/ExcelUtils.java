package com.apps.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	private static String sheetPath = PropertyUtils.getProperty("data.path") + PropertyUtils.getProperty("excel.name");
	private static final Logger LOG = LogManager.getLogger(ExcelUtils.class);

	@SuppressWarnings("resource")
	private static XSSFSheet getSheet(String sheetName) throws IOException {
        FileInputStream fis = new FileInputStream(new File(sheetPath).getAbsolutePath());
        XSSFSheet sheet = new XSSFWorkbook(fis).getSheet(sheetName);
        return sheet;
    }
	
    public static List<LinkedHashMap<String, String>> getExcelAsMap(String sheetName) throws IOException {
        XSSFSheet sheet = getSheet(sheetName);
        List<LinkedHashMap<String, String>> completeSheetData = new ArrayList<LinkedHashMap<String, String>>();
        List<String> columnHeader = new ArrayList<String>();
        XSSFRow row = sheet.getRow(0);
        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            columnHeader.add(cellIterator.next().getStringCellValue());
        }
        int rowCount = sheet.getLastRowNum();
        int columnCount = row.getLastCellNum();
        for (int i = 1; i <= rowCount; i++) {
            LinkedHashMap<String, String> singleRowData = new LinkedHashMap<String, String>();
            XSSFRow row1 = sheet.getRow(i);
            for (int j = 0; j < columnCount; j++) {
                Cell cell = row1.getCell(j);
                if(cell==null)
                	singleRowData.put(columnHeader.get(j), "");
                else
                	singleRowData.put(columnHeader.get(j), getCellValueString(cell));
            }
            LOG.info(singleRowData);
            completeSheetData.add(singleRowData);
        }
        return completeSheetData;
    }
    
    public static Object[][] getExcelAsObjects(String sheetName) throws IOException{
    	List<LinkedHashMap<String, String>> m=getExcelAsMap(sheetName);
    	Object[][] f=new Object[m.size()][m.get(0).size()];
    	int[] i= {0};
    	m.forEach(x->{
    		f[i[0]++]=new Object[] {x};
    	});
    	return f;
    }
    
    public static String getCellValueString(Cell cell) {
        String cellValue = null;
        switch (cell.getCellType()) {
            case NUMERIC:
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA:
                cellValue= cell.getCellFormula();
            case BLANK:
                cellValue="BLANK";
            default:
                cellValue ="DEFAULT";
        }
        return cellValue;
    }

	public static void main(String[] args) throws Exception {
		getExcelAsMap("google");
	}
}
