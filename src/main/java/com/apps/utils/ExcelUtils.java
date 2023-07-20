package com.apps.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	private static XSSFSheet excelSheet;
	private static XSSFWorkbook excelWorkbook;
	private static XSSFCell cell;
	private static XSSFRow row;
	private static String sheetPath = PropertyUtils.getProperty("data.path") + PropertyUtils.getProperty("excel.name");
	private static String sheetName = PropertyUtils.getProperty("sheet.name");
	private static final Logger LOG = LogManager.getLogger(ExcelUtils.class);

	private static void setExcelFile() throws IOException {
		LOG.info("Getting sheets from the workbook.");
		FileInputStream excelFile = new FileInputStream(new File(sheetPath).getAbsolutePath());
		excelWorkbook = new XSSFWorkbook(excelFile);
		excelSheet = excelWorkbook.getSheet(sheetName);
	}

	private static int getDataRow(String dataKey, int dataColumn) {
		int rowCount = excelSheet.getLastRowNum();
		for (int row = 0; row <= rowCount; row++) {
			if (ExcelUtils.getCellData(row, dataColumn).equalsIgnoreCase(dataKey)) {
				return row;
			}
		}
		return 0;
	}

	private static String getCellData(int rowNumb, int colNumb) {
		cell = excelSheet.getRow(rowNumb).getCell(colNumb);
		// LOG.info("Getting cell data.");
		if (cell.getCellType() == CellType.NUMERIC) {
			cell.setCellType(CellType.STRING);
		}
		String cellData = cell.getStringCellValue();
		return cellData;
	}

	public static void setCellData(String result, int rowNumb, int colNumb, String sheetPath, String sheetName)
			throws Exception {
		try {
			row = excelSheet.getRow(rowNumb);
			cell = row.getCell(colNumb);
			LOG.info("Setting results into the excel sheet.");
			if (cell == null) {
				cell = row.createCell(colNumb);
				cell.setCellValue(result);
			} else {
				cell.setCellValue(result);
			}

			LOG.info("Creating file output stream.");
			FileOutputStream fileOut = new FileOutputStream(sheetPath + sheetName);
			excelWorkbook.write(fileOut);
			fileOut.flush();
			fileOut.close();

		} catch (Exception exp) {
			LOG.info("Exception occured in setCellData: " + exp);
		}
	}

	public static Map<String, String> getData(String dataKey) throws Exception {
		Map<String, String> dataMap = new HashMap<String, String>();
		setExcelFile();
		int dataRow = getDataRow(dataKey.trim(), 0);
		LOG.info("Test Data Found in Row: " + dataRow);
		if (dataRow == 0) {
			throw new Exception("NO DATA FOUND for dataKey: " + dataKey);
		}
		int columnCount = excelSheet.getRow(dataRow).getLastCellNum();
		for (int i = 0; i < columnCount; i++) {
			cell = excelSheet.getRow(dataRow).getCell(i);
			String cellData = null;
			if (cell != null) {
				if (cell.getCellType() == CellType.NUMERIC) {
					cell.setCellType(CellType.STRING);
				}
				cellData = cell.getStringCellValue();
			}
			dataMap.put(excelSheet.getRow(0).getCell(i).getStringCellValue(), cellData);
		}
		return dataMap;
	}

	static XSSFWorkbook ExcelWBook;
	static XSSFSheet ExcelWSheet;

	public static Object[][] getTableArray() throws Exception {

		String[][] tabArray = null;

		try {

			FileInputStream ExcelFile = new FileInputStream(sheetPath);

			// Access the required test data sheet

			ExcelWBook = new XSSFWorkbook(ExcelFile);

			ExcelWSheet = ExcelWBook.getSheet(sheetName);

			int startRow = 1;
			int startCol = 1;
			int ci, cj;
			int totalRows = ExcelWSheet.getLastRowNum();
			// you can write a function as well to get Column count
			int totalCols = 2;
			tabArray = new String[totalRows][totalCols];

			ci = 0;
			for (int i = startRow; i <= totalRows; i++, ci++) {
				cj = 0;
				for (int j = startCol; j <= totalCols; j++, cj++) {
					tabArray[ci][cj] = getCellData1(i, j);
					System.out.println(tabArray[ci][cj]);

				}

			}

		}

		catch (FileNotFoundException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();

		}

		catch (IOException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();

		}

		return (tabArray);

	}

	public static String getCellData1(int RowNum, int ColNum) throws Exception {

		try {
			XSSFCell Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			CellType dataType = Cell.getCellType();
			if (dataType == CellType.ERROR) {
				return "";
			} else {
				String CellData = Cell.getStringCellValue();
				return CellData;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw (e);
		}
	}

	public static void main(String[] args) throws Exception {
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap = getData("updateBooking21");
		for (Map.Entry<String, String> data : dataMap.entrySet()) {
			LOG.info(data.getKey() + " ==> " + data.getValue());
		}
	}
}
