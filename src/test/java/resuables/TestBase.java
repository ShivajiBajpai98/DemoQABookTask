package resuables;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {


    public WebDriver driver;
    public Properties configuration = new Properties();
    public Properties locators = new Properties();
    public FileInputStream fis;

    @BeforeSuite
    public void setUp() {
        if (driver == null) {

            try {
                String path = "." + File.separator + "src" + File.separator + "test" + File.separator + "java" + File.separator + "resources" + File.separator + "Configuration.properties";
                fis = new FileInputStream(path);
            } catch (FileNotFoundException e) {

                e.printStackTrace();
            }
            try {
                configuration.load(fis);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fis = new FileInputStream("." + File.separator + "src" + File.separator + "test" + File.separator + "java" + File.separator + "resources" + File.separator + "locators.properties");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                locators.load(fis);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (configuration.getProperty("browser").equals("firefox")) {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
            } else if (configuration.getProperty("browser").equals("chrome")) {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            }


            driver.get(configuration.getProperty("testUrl"));
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Integer.parseInt(configuration.getProperty("implicit.wait")), TimeUnit.SECONDS);
            //wait = new WebDriverWait(driver, );
        }

    }

    public boolean isElementPresent(String locatorKey) {
        try {
            if (locatorKey.endsWith("_XPATH")) {
                driver.findElement(By.xpath(locators.getProperty(locatorKey)));
            } else if (locatorKey.endsWith("_CSS")) {
                driver.findElement(By.cssSelector(locators.getProperty(locatorKey)));
            } else if (locatorKey.endsWith("_ID")) {
                driver.findElement(By.id(locators.getProperty(locatorKey)));
            }
        } catch (Throwable t) {

            return false;

        }
        return true;
    }

    public void findElement_SendKeys(String locatorKey, String value) {
        if (locatorKey.endsWith("_XPATH")) {
            driver.findElement(By.xpath(locators.getProperty(locatorKey))).sendKeys(value);
        } else if (locatorKey.endsWith("_CSS")) {
            driver.findElement(By.cssSelector(locators.getProperty(locatorKey))).sendKeys(value);
        } else if (locatorKey.endsWith("_ID")) {
            driver.findElement(By.id(locators.getProperty(locatorKey))).sendKeys(value);
        }

    }

    public void findElement_Click(String locatorKey) {
        if (locatorKey.endsWith("_XPATH")) {
            driver.findElement(By.xpath(locators.getProperty(locatorKey))).click();
        } else if (locatorKey.endsWith("_CSS")) {
            driver.findElement(By.cssSelector(locators.getProperty(locatorKey))).click();
        } else if (locatorKey.endsWith("_ID")) {
            driver.findElement(By.id(locators.getProperty(locatorKey))).click();
        }
    }

    public WebElement findElement_Only(String locatorKey) {
        if (locatorKey.endsWith("_XPATH")) {
            WebElement webElement = driver.findElement(By.xpath(locators.getProperty(locatorKey)));

            return webElement;

        } else if (locatorKey.endsWith("_CSS")) {
            WebElement webElement = driver.findElement(By.cssSelector(locators.getProperty(locatorKey)));
            return webElement;
        } else if (locatorKey.endsWith("_ID")) {
            WebElement webElement = driver.findElement(By.id(locators.getProperty(locatorKey)));
            return webElement;
        }

        return null;
    }

    public List<WebElement> findElements_Only(String locatorKey) {
        if (locatorKey.endsWith("_XPATH")) {
            List<WebElement> elements = driver.findElements(By.xpath(locators.getProperty(locatorKey)));
            return elements;
        } else if (locatorKey.endsWith("_CSS")) {
            List<WebElement> elements = driver.findElements(By.cssSelector(locators.getProperty(locatorKey)));
            return elements;
        } else if (locatorKey.endsWith("_ID")) {
            List<WebElement> elements = driver.findElements(By.id(locators.getProperty(locatorKey)));
            return elements;
        }
        return null;
    }



	/*@AfterSuite
	public void tearDown() {

		if (driver != null) {
			driver.quit();
		}


	}*/
}
