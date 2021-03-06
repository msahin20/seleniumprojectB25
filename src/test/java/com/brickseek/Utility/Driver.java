package com.brickseek.Utility;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;


import java.util.HashMap;
import java.util.Map;


public class Driver {

    /*
    Creating a private constructor, so we are closing
    access to the object of this class from outside the class
     */
    private Driver() {
    }

    /*
    We make WebDriver private, because we want to close access from outside the class.
    We make it static because we will use it in a static method.
     */
    private static WebDriver driver; // value is null by default

    /*
    Create a re-usable utility method which will return same driver instance when we call it
     */
    public static WebDriver getDriver() {

        if (driver == null) {

            /*
            We read our browserType from configuration.properties.
            This way, we can control which browser is opened from outside our code, from configuration.properties.
             */
            String browserType = ConfigurationReader.getProperty("browser");


            /*
                Depending on the browserType that will be return from configuration.properties file
                switch statement will determine the case, and open the matching browser
            */
            switch (browserType) {
                case "chromeNoimage":
                    WebDriverManager.chromedriver().setup();

                    //System.setProperty("webdriver.chrome.driver", "C:\\Users\\ghs6kor\\Desktop\\Java\\chromedriver.exe");
                    Map <String, Object> prefs = new HashMap<String, Object>();
                    // browser setting to disable image
                    prefs.put("profile.managed_default_content_settings.images", 2);
                    //adding capabilities to browser
                    ChromeOptions op = new ChromeOptions();
                    op.setExperimentalOption("prefs", prefs);
                    // putting desired capabilities to browser
                    WebDriverManager.chromedriver().setup();
                    driver= new ChromeDriver(op);
                    driver.manage().window().maximize();




//                    WebDriverManager.chromedriver().setup();
////
//                    driver = new ChromeDriver();
//                    driver.manage().window().maximize();
                    //driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    driver.manage().window().maximize();
                    //driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
                    break;
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    driver.manage().window().maximize();
                    //driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
                    break;

                case "edge":
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                    driver.manage().window().maximize();
                    //driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
                    break;

            }
        }

        return driver;

    }

    /*
    This method will make sure our driver value is always null after using quit() method
     */
    public static void closeDriver() {
        if (driver != null) {
            driver.quit(); // this line will terminate the existing session. value will not even be null
            driver = null;
        }
    }
}
