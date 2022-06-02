package com.brickseek.Utility;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
public class ImagelessWebDriver {
    public static void main(String[] args) throws IOException {
        //System.setProperty("webdriver.chrome.driver", "C:\\Users\\ghs6kor\\Desktop\\Java\\chromedriver.exe");
        Map<String, Object> prefs = new HashMap<String, Object>();
        // browser setting to disable image
        prefs.put("profile.managed_default_content_settings.images", 2);
        //adding capabilities to browser
        ChromeOptions op = new ChromeOptions();
        op.setExperimentalOption("prefs", prefs);
        // putting desired capabilities to browser
        WebDriverManager.chromedriver().setup();
        WebDriver driver= new ChromeDriver(op);
        driver.get("https://www.tutorialspoint.com/index.htm/");
    }
}