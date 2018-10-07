package testcases;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import excelInputAndOutput.ExcelReader;
import operation.ReadObject;
import operation.UIOperation;
import utility.Constant;
import utility.ReportGenerator;

public class excuteTest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		WebDriver driver = null;
		ReadObject reader = new ReadObject();
		Properties allObjects = reader.getObjectRepoository();
		if(allObjects.getProperty("browserType").equals("chrome")){
			System.setProperty("webdriver.chrome.driver", Constant.chromeDriverPath);
			driver = new ChromeDriver();
		}else if(allObjects.getProperty("browserType").equals("firefox")){
			System.setProperty("webdriver.chrome.driver", Constant.geckoDriverPath);
			driver = new FirefoxDriver();
		}else if(allObjects.getProperty("browserType").equals("ie")){
			System.setProperty("webdriver.chrome.driver", Constant.ieDriverPath);
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
			capabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, allObjects.getProperty("URL"));
			driver = new InternetExplorerDriver();
		}
		
		driver.manage().window().maximize();
		
		// Delete Previous Test Execution Report
		File file = new File(Constant.reportPath);
		if(file.exists()){
			file.delete();
		}
		
		ExcelReader obj = new ExcelReader();
		UIOperation operation = new UIOperation(driver);
		ReportGenerator report = new ReportGenerator();
		
		// Add System Information to the Test Execution Report
		report.generateReport("*******************************************");
		report.generateReport("Project Name : Flight Reservation");
		report.generateReport("Environement : Production");
		report.generateReport("Operating System : "+System.getProperty("os.name"));
		InetAddress myHost = InetAddress.getLocalHost();
		report.generateReport("Machine Name : "+myHost.getHostName());
		report.generateReport("User : "+System.getProperty("user.name"));
		report.generateReport("*******************************************");
		
		Sheet sheet = obj.readExcel(Constant.filePath, Constant.fileName);
		int rownum = sheet.getLastRowNum()-sheet.getFirstRowNum();
		for(int i=1; i<=rownum;i++){
			
			Row row = sheet.getRow(i);
			if(row.getCell(0).toString().length() == 0){
				String status = operation.perform(allObjects, row.getCell(1).getStringCellValue(), row.getCell(2).getStringCellValue(), row.getCell(3).getStringCellValue(), row.getCell(4).getStringCellValue());
				report.generateReport(row.getCell(1).getStringCellValue()+"---"+row.getCell(2).getStringCellValue()+"---"+row.getCell(3).getStringCellValue()+"---"+row.getCell(4).getStringCellValue()+"---"+status);
			}else{
				report.generateReport("===========================================================");
				report.generateReport("New Test Case : "+row.getCell(0).getStringCellValue());
				report.generateReport("===========================================================");
			}
		}
		
		
		driver.close();
		driver.quit();
		
	}

}
