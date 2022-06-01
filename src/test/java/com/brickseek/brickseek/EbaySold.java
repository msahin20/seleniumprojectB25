package com.brickseek.brickseek;

import com.brickseek.Utility.Driver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class EbaySold {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("output " + ebayLink("889842519099  "));
    }

    public static Double ebayLink(String upc) throws InterruptedException {

        Double avePrice = 0.00;

//        WebDriverManager.chromedriver().setup();
//
//        WebDriver driver = new ChromeDriver();
//        driver.manage().window().maximize();
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);


        //driver.navigate().to("https://www.ebay.com");
        //driver.findElement(By.xpath("//*[@id=\"gh-ug\"]/a")).click();

        Driver.getDriver().get("https://www.ebay.com/");

        try {



            Thread.sleep(1000);
            WebElement searchInput = Driver.getDriver().findElement(By.xpath("//*[@id=\"gh-ac\"]"));
            searchInput.sendKeys(upc + Keys.ENTER);
            Thread.sleep(1000);
            List<WebElement> elementList = Driver.getDriver().findElements(By.xpath("//ul[@class='srp-results srp-list clearfix']//span[@class='s-item__price']"));
            ArrayList<Double> ebayprices = new ArrayList<>();
            Double sum = 0.00;
            int count = 0;
            for (int i = 0; i < elementList.size(); i++) {
                String strString = elementList.get(i).getText();
                if (strString.contains("to")) {
                    strString = strString.substring(1, strString.indexOf(' '));
                } else if (strString.contains("" + ',')) {
                    strString = strString.substring(1, strString.indexOf(',')) + strString.substring(strString.indexOf(',') + 1, strString.indexOf(',') + 4) + '.' + strString.substring(strString.indexOf('.') + 1, strString.indexOf('.') + 3);
                } else if (strString.contains("See")) {
                    System.out.println("see    " + strString);
                    strString = elementList.get(0).getText();
                } else {
                    strString = strString.substring(1);
                }
                //System.out.println(strString);
                double strDouble = 0.00;
                try {
                    strDouble = Double.parseDouble(strString);
                } catch (RuntimeException e) {
                    count--;
                    e.printStackTrace();

                }
                //System.out.println(strDouble);
                //ebayprices.add(strDouble);
                //System.out.println(ebayprices);

                sum += strDouble;
                count++;
                //System.out.println(strDouble);

            }
             avePrice = sum / count;
            //System.out.println(avePrice);


            //String strDouble = elementList.get(0).getText();
            //strDouble = strDouble.substring(1);
            //double str1 = Double.parseDouble(strDouble);

            //System.out.println("elementList.get(1).getText() = " + strDouble);
            //System.out.println(str1);

            //double str1 = Double.parseDouble(str);

            String updatedUrl = Driver.getDriver().getCurrentUrl();
            //System.out.println(updatedUrl);
            //driver.quit();
        } catch (RuntimeException e){

            e.printStackTrace();
        }
        Driver.closeDriver();

        return avePrice;
        //driver.quit();
        //WebDriverManager.edgedriver().setup();

        //WebDriver driver2 = new EdgeDriver();
        //driver2.manage().window().maximize();


        //driver2.get(updatedUrl);

        //Thread.sleep(5000);
        //WebElement soldBox = driver2.findElement(By.xpath("//*[@id=\"x-refine__group__7\"]/ul/li[5]/div/a/div/span/input"));

        //soldBox.click();
        //Thread.sleep(5000);
        //String updatedUrl = driver.getCurrentUrl();
        //driver.close();
        //System.out.println(updatedUrl);
        //driver = new ChromeDriver();
        //driver.manage().window().maximize();
        //driver.get(updatedUrl);

        //List<WebElement> soldElements = driver.findElements(By.xpath("//*[@id=\"srp-river-results\"]//li//div//div//div//div//span//span[@class='POSITIVE']"));

        //System.out.println(soldElements.get(0).getText());
        //driver.close();

/*
        for (WebElement each : soldElements) {

            int intPercent = Integer.parseInt(each.getText().substring(0, 2));
            System.out.println(intPercent);

        }
        */
    }
}
