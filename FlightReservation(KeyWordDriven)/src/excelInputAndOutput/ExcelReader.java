package excelInputAndOutput;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	Workbook workbook;
public Sheet readExcel(String filePath, String fileName) throws IOException{
	
	File file = new File(filePath+"\\"+fileName);
	FileInputStream stream = new FileInputStream(file);
	String fileExtensionName = fileName.substring(fileName.indexOf("."));
	if(fileExtensionName.equals(".xlsx")){
		workbook = new XSSFWorkbook(stream);
	}else if(fileExtensionName.equals(".xls")){
		workbook = new HSSFWorkbook(stream);
	}
	
	Sheet sheet = workbook.getSheet("TestData");
	
	return sheet;
}

public void generateReport(String filePath,String fileName) throws IOException {
	File file = new File(filePath+"\\"+fileName);
	FileOutputStream outputstream = new FileOutputStream(file);
	workbook.write(outputstream);
	outputstream.close();
	
}

public CellStyle customizeTestExecutionReport(String status) {
	CellStyle style = workbook.createCellStyle();
	Font font = workbook.createFont();
	style.setBorderBottom(BorderStyle.THIN);
	style.setBorderLeft(BorderStyle.THIN);
	style.setBorderRight(BorderStyle.THIN);
	style.setBorderTop(BorderStyle.THIN);
	if(status.equals("Pass")) {
		style.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	}else if(status.equals("Fail")) {
		style.setFillForegroundColor(IndexedColors.RED.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	}else if(status.equals("header")) {
		style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		font.setBold(true);
	}
	
	return style;
}

public void closeWorkbook() throws IOException {
	workbook.close();
}
}
