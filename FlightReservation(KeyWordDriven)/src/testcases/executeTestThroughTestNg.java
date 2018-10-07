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
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import excelInputAndOutput.ExcelReader;
import operation.ReadObject;
import operation.UIOperation;
import utility.Constant;
import utility.ReportGenerator;

public class  executeTestThroughTestNg {
	@Parameters({"browser"})
    @Test
	public void executeTest(String browser) throws IOException {
		// TODO Auto-generated method stub
		WebDriver driver = null;
		ReadObject reader = new ReadObject();
		Properties allObjects = reader.getObjectRepoository();
		if(browser.equals("chrome")){
			System.setProperty("webdriver.chrome.driver", Constant.chromeDriverPath);
			driver = new ChromeDriver();
		}else if(browser.equals("firefox")){
			System.setProperty("webdriver.gecko.driver", Constant.geckoDriverPath);
			driver = new FirefoxDriver();
		}else if(browser.equals("ie")){
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
		
		System.out.println("*******************************************");
		System.out.println("Project Name : Flight Reservation");
		System.out.println("Environement : Production");
		System.out.println("Operating System : "+System.getProperty("os.name"));
		InetAddress myHost1 = InetAddress.getLocalHost();
		System.out.println("Machine Name : "+myHost1.getHostName());
		System.out.println("User : "+System.getProperty("user.name"));
		System.out.println("*******************************************");
		
		Sheet sheet = obj.readExcel(Constant.filePath, Constant.fileName);
		int rownum = sheet.getLastRowNum()-sheet.getFirstRowNum();
		for(int i=1; i<=rownum;i++){
			
			Row row = sheet.getRow(i);
			if(row.getCell(0).toString().length() == 0){
				String status = operation.perform(allObjects, row.getCell(1).getStringCellValue(), row.getCell(2).getStringCellValue(), row.getCell(3).getStringCellValue(), row.getCell(4).getStringCellValue());
				report.generateReport(row.getCell(1).getStringCellValue()+"---"+row.getCell(2).getStringCellValue()+"---"+row.getCell(3).getStringCellValue()+"---"+row.getCell(4).getStringCellValue()+"---"+status);
				System.out.println(row.getCell(1).getStringCellValue()+"---"+row.getCell(2).getStringCellValue()+"---"+row.getCell(3).getStringCellValue()+"---"+row.getCell(4).getStringCellValue()+"---"+status);
			}else{
				report.generateReport("===========================================================");
				System.out.println("===========================================================");
				report.generateReport("New Test Case : "+row.getCell(0).getStringCellValue());
				System.out.println("New Test Case : "+row.getCell(0).getStringCellValue());
				report.generateReport("===========================================================");
				System.out.println("===========================================================");
			}
		}
		
		
		driver.close();
		//driver.quit();
		
	}

}

