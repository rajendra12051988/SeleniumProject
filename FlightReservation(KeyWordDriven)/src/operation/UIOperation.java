package operation;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;


public class UIOperation {
	public WebDriver driver;
	
	public UIOperation(WebDriver driver){
		this.driver = driver;
	}
	
	public String perform(Properties p,String operation,String objectType,String objectName,String value){
		String status = "Pass";
		Logger logger = Logger.getLogger("devpinoyLogger");
		switch(operation.toUpperCase()){
		case "GOTOURL":
			logger.info("Launching Application : "+(p.getProperty("URL")));
			driver.get(p.getProperty("URL"));
			logger.info("Launched Application : "+(p.getProperty("URL")));
			break;
		case "ASSERTTITLE":
			if(driver.getTitle().equals(value)){
				status = "Pass";
			}else{
				status = "Fail";
				logger.error("Status -- Failed | Expected Title : "+value+" | Actual Title : "+driver.getTitle());
			}
			break;
		case "CLICK":
			logger.info("Clicking on '"+objectName+"'");
			WebElement elementTobeClicked = driver.findElement(this.getObject(p,objectType,objectName));
			highlightElement(driver, elementTobeClicked);
			elementTobeClicked.click();
			logger.info("Clicked on '"+objectName+"'");
			break;
		case "SELECT":
			logger.info("Selecting '"+value+"' from '"+objectName+"' dropdown");
			WebElement elementTobeSelected = driver.findElement(this.getObject(p, objectType, objectName));
			highlightElement(driver, elementTobeSelected);
			Select select = new Select(elementTobeSelected);
			select.selectByVisibleText(value);
			logger.info("Selected'"+value+"' from '"+objectName+"' dropdown");
			break;
		case "SET":
			logger.info("Entering '"+value+"' in the '"+objectName+"' edit field");
			WebElement elemntTobeSet = driver.findElement(this.getObject(p, objectType, objectName));
			highlightElement(driver,elemntTobeSet);
			elemntTobeSet.sendKeys(value);
			logger.info("Entered'"+value+"' in the '"+objectName+"' edit field");
			break;
		case "VERIFYTEXT":
			logger.info("Verifying text '"+value+"' on web page...");
			if(driver.getPageSource().contains(value)){
				status = "Pass";
			}else{
				status = "Fail";
			}
			logger.info("Verified text '"+value+"' on web page...");
		}
		return status;
	}
	
	private By getObject(Properties p, String objectType,String objectName){
		if(objectType.equals("NAME")){
			return By.name(p.getProperty(objectName));
		}else if(objectType.equals("XPATH")){
			return By.xpath(p.getProperty(objectName));
		}else if(objectType.equals("ID")){
			return By.id(p.getProperty(objectName));
		}else if(objectType.equals("LINKTEXT")){
			return By.linkText(p.getProperty(objectName));
		}else{
			return null;
		}
	}
	
	private void highlightElement(WebDriver driver,WebElement element){
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].setAttribute('style','background: yellow; border: 2px solid red;');",element);
	}
	
	
}
