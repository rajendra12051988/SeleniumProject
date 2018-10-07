package excelInputAndOutput;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
public Sheet readExcel(String filePath, String fileName) throws IOException{
	Workbook workbook = null;
	File file = new File(filePath+"\\"+fileName);
	FileInputStream stream = new FileInputStream(file);
	String fileExtensionName = fileName.substring(fileName.indexOf("."));
	if(fileExtensionName.equals(".xlsx")){
		workbook = new XSSFWorkbook(stream);
	}else if(fileExtensionName.equals(".xls")){
		workbook = new HSSFWorkbook(stream);
	}
	
	Sheet sheet = workbook.getSheet("TestData");
	workbook.close();
	return sheet;
}
}
