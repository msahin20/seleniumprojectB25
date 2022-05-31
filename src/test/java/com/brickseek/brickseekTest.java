package com.brickseek;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class brickseekTest {
    public static void main(String[] args) throws InterruptedException {
        String previousdeal = "";
        for (int i = 0; i < 10000; i++) {
            int waitTime = 30;


            WebDriverManager.chromedriver().setup();
            WebDriver driver = new ChromeDriver();
            driver.manage().window().maximize();

            //driver.get("https://brickseek.com/deals/?sort=newest&type=online&store_types%5B0%5D=3");
            driver.get("https://brickseek.com/deals/?sort=newest&type=online&store_types%5B0%5D=12");
            driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);

            //WebElement imgLocation = driver.findElement(By.className("item-list__image-container"));
            WebElement imgLocation = driver.findElement(By.xpath("//*[@id=\"main\"]/div[2]/div[1]/div[1]/div/a"));
            String itemLink = imgLocation.getAttribute("href");




            if (itemLink.equals(previousdeal)){

                driver.close();
                Thread.sleep(300000);

                continue;
            }





            //imgLocation.click();

            //String itemLink = driver.getCurrentUrl();



            //driver.navigate().back();


            WebElement PercentLocation = driver.findElement(By.className("item-list__discount-meter"));
            String percentOff = PercentLocation.getText();
            System.out.println(PercentLocation.getText());

            driver.navigate().to("https://login.yahoo.com/?.src=ym&pspid=1197806870&activity=header-signin&.lang=en-US&.intl=us&.done=https%3A%2F%2Flocalhost%3A4080%2Fd");
            driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
            WebElement mailMousePointer = driver.findElement(By.name("username"));
            driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
            mailMousePointer.sendKeys("mrtshnmetu" + Keys.ENTER);
            driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
            driver.findElement(By.id("login-passwd")).sendKeys("Turkiye52@" + Keys.ENTER);
            driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
            driver.findElement(By.linkText("Compose")).click();
            driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
            driver.findElement(By.id("message-to-field")).sendKeys("mrtshnmetu@yahoo.com" + Keys.ENTER);
            driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
            //driver.findElement(By.id("editor-container")).sendKeys("mrtshnmetu@yahoo.com" + Keys.ENTER);
            driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
            driver.findElement(By.xpath("//*[@id=\"editor-container\"]/div[1]/div[1]")).sendKeys(itemLink + Keys.ENTER + percentOff);

            driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);

            driver.findElement(By.xpath("//*[@id=\"mail-app-component\"]/div/div/div[1]/div[3]/div/div/input")).sendKeys("deal");


            driver.findElement(By.xpath("//*[@id=\"mail-app-component\"]/div/div/div[2]/div[2]/div/button/span")).click();
            driver.close();
            previousdeal= itemLink;

        }










    }
}
