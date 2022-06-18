package com.brickseek.brickseek;

import com.brickseek.Utility.Driver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AmazonPriceTrack {

    public static void main(String[] args) throws InterruptedException {
        //System.out.println( amazonPrice("889842519099"));
        System.out.println(priceChart3("889842519099")[0]);
        //System.out.println(priceChart2("070330682719")[1]);
    }

    public static String amazonPrice(String upc) throws InterruptedException {
//        WebDriverManager.chromedriver().setup();
//
//        WebDriver driver = new ChromeDriver();
//        driver.manage().window().maximize();


        //driver.navigate().to("https://www.ebay.com");
        //driver.findElement(By.xpath("//*[@id=\"gh-ug\"]/a")).click();

        Driver.getDriver().get("https://www.camelcamelcamel.com//");
        Thread.sleep(1000);
        try {
            WebElement searchInput = Driver.getDriver().findElement(By.xpath("//input[@class='input-group-field has-tip']"));
            searchInput.sendKeys(upc + Keys.ENTER);
            Thread.sleep(1000);
        } catch (RuntimeException h){
            h.printStackTrace();
        }

        String output = "";
        try {

            List<WebElement> elementList = Driver.getDriver().findElements(By.xpath("//div[@class='row column search_results']//td[@class='text-right']"));
            ArrayList<Double> amazonPrices = new ArrayList<>();

            //Double sum = 0.00;
            //for (int i = 0; i < 2; i++) {
            output += "Amazon price " + elementList.get(0).getText() + "     Amazon 3rd party price  " + elementList.get(1).getText();

            System.out.println(output);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();

            try {

            output += "Amazon price " + Driver.getDriver().findElement(By.xpath("//div[@class='column small-8 medium-12']//span[@class='green']")).getText();
            //output += " Amazon price not available  ";

             } catch (RuntimeException g) {
                g.printStackTrace();
                output += " Amazon price not available  ";
            }

        }
        catch (RuntimeException f) {
            f.printStackTrace();
            output += " Amazon price not available  ";
        }



        String updatedUrl = Driver.getDriver().getCurrentUrl();
        //System.out.println(updatedUrl);
        Driver.closeDriver();
        return output;

    }

    public static String priceChart(String upc) throws InterruptedException {
        String priceChartHref ="N/A";
//        WebDriverManager.chromedriver().setup();
//
//        WebDriver driver = new ChromeDriver();
//        driver.manage().window().maximize();
        try {



            //driver.navigate().to("https://www.ebay.com");
            //driver.findElement(By.xpath("//*[@id=\"gh-ug\"]/a")).click();

            Driver.getDriver().get("https://www.camelcamelcamel.com/");
            Thread.sleep(1000);
            WebElement searchInput = Driver.getDriver().findElement(By.xpath("//input[@class='input-group-field has-tip']"));
            searchInput.sendKeys(upc + Keys.ENTER);
            Thread.sleep(1000);
            WebElement priceChart = Driver.getDriver().findElement(By.xpath("//img[@id='summary_chart']"));
            priceChartHref = priceChart.getAttribute("src");
            Driver.closeDriver();

        }
        catch(RuntimeException e) {
            Driver.closeDriver();
            e.printStackTrace();
        }

        if (priceChartHref.contains("N/A")){
            try {

                WebDriverManager.edgedriver().setup();

                WebDriver driver = new EdgeDriver();
                driver.manage().window().maximize();



                //driver.navigate().to("https://www.ebay.com");
                //driver.findElement(By.xpath("//*[@id=\"gh-ug\"]/a")).click();

                driver.get("https://www.camelcamelcamel.com/");
                Thread.sleep(1000);
                WebElement searchInput = driver.findElement(By.xpath("//input[@class='input-group-field has-tip']"));
                searchInput.sendKeys(upc + Keys.ENTER);
                Thread.sleep(1000);
                WebElement priceChart = driver.findElement(By.xpath("//img[@id='summary_chart']"));
                priceChartHref = priceChart.getAttribute("src");
                driver.close();

            }
            catch(RuntimeException e) {

                e.printStackTrace();
            }

        }






        return priceChartHref;



    }


    public static String[] priceChart2(String upc) throws InterruptedException {
        String priceChartHref ="N/A";
        String salesRank = "N/A";
        String[] output = {priceChartHref, salesRank};
//        WebDriverManager.chromedriver().setup();
//
//        WebDriver driver = new ChromeDriver();
//        driver.manage().window().maximize();
        Driver.getDriver().get("https://www.camelcamelcamel.com/");
        try {


            //driver.navigate().to("https://www.ebay.com");
            //driver.findElement(By.xpath("//*[@id=\"gh-ug\"]/a")).click();

//            driver.navigate().to("https://www.camelcamelcamel.com//");
//            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            WebElement searchInput = Driver.getDriver().findElement(By.xpath("//input[@class='input-group-field has-tip']"));
            searchInput.sendKeys(upc + Keys.ENTER);
            //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            try {
                Driver.getDriver().findElement(By.xpath("//*[@id=\"content\"]//p[contains(text(),'Sorry, we didn')]"));
                output[0] ="Not found";
            } catch (RuntimeException e) {
                e.printStackTrace();
            }

            try {
                WebElement priceChart = Driver.getDriver().findElement(By.xpath("//img[@id='summary_chart']"));
                priceChartHref = priceChart.getAttribute("src");
                output[0]=priceChartHref;
                salesRank = Driver.getDriver().findElement(By.xpath("//table[@class='product_fields']//tr[10]//td[1]")).getText() + ": "
                        + Driver.getDriver().findElement(By.xpath("//table[@class='product_fields']//tr[10]//td[2]")).getText()
                        + " in "+ Driver.getDriver().findElement(By.xpath("//table[@class='product_fields']//tr[2]//td[2]")).getText();
                output[1]= salesRank;
                //System.out.println("priceChartHref " + output[0]);
                //System.out.println("salesRank "+ output[1]);
                //driver.quit();
            } catch (RuntimeException e){
                e.printStackTrace();
            }
//            try {
//                WebElement firstImage = Driver.getDriver().findElement(By.xpath("//img[1]"));
//                firstImage.click();
//                WebElement priceChart = Driver.getDriver().findElement(By.xpath("//img[@id='summary_chart']"));
//                priceChartHref = priceChart.getAttribute("src");
//                output[0]=priceChartHref;
//                salesRank = Driver.getDriver().findElement(By.xpath("//table[@class='product_fields']//tr[10]//td[1]")).getText() + ": "
//                        + Driver.getDriver().findElement(By.xpath("//table[@class='product_fields']//tr[10]//td[2]")).getText()
//                        + " in "+ Driver.getDriver().findElement(By.xpath("//table[@class='product_fields']//tr[2]//td[2]")).getText();
//                output[1]= salesRank;
//                //System.out.println("priceChartHref " + output[0]);
//                //System.out.println("salesRank "+ output[1]);
//
//
//            }catch (RuntimeException e){
//                e.printStackTrace();
//            }


        }
        catch(Exception e) {
            //driver.quit();
            e.printStackTrace();
        }
        Driver.closeDriver();
        System.out.println("priceChartHref " + output[0]);
        System.out.println("salesRank "+ output[1]);



        return output;



    }

    public static String[] priceChart3(String upc) throws InterruptedException {
        String priceChartHref ="N/A";
        String salesRank = "N/A";
        String[] output = {priceChartHref, salesRank};
        WebDriver driver=null;

        try {
            WebDriverManager.firefoxdriver().setup();

            driver = new FirefoxDriver();
            driver.manage().window().maximize();
            driver.get("https://www.camelcamelcamel.com/");


            //driver.navigate().to("https://www.ebay.com");
            //driver.findElement(By.xpath("//*[@id=\"gh-ug\"]/a")).click();

//            driver.navigate().to("https://www.camelcamelcamel.com//");
//            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            WebElement searchInput = driver.findElement(By.xpath("//input[@class='input-group-field has-tip']"));
            searchInput.sendKeys(upc + Keys.ENTER);
            Thread.sleep(2500);
            //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            try {
                driver.findElement(By.xpath("//*[@id=\"content\"]//p[contains(text(),'Sorry, we didn')]"));
                output[0] ="Not found";
            } catch (RuntimeException e) {
                e.printStackTrace();
            }

            try {
                WebElement priceChart = driver.findElement(By.xpath("//img[@id='summary_chart']"));
                priceChartHref = priceChart.getAttribute("src");
                output[0]=priceChartHref;
                salesRank = driver.findElement(By.xpath("//table[@class='product_fields']//tr[10]//td[1]")).getText() + ": "
                        + driver.findElement(By.xpath("//table[@class='product_fields']//tr[10]//td[2]")).getText()
                        + " in "+ driver.findElement(By.xpath("//table[@class='product_fields']//tr[2]//td[2]")).getText();
                output[1]= salesRank;
                //System.out.println("priceChartHref " + output[0]);
                //System.out.println("salesRank "+ output[1]);
                //driver.quit();
            } catch (RuntimeException e){

                e.printStackTrace();
            }


        }
        catch(RuntimeException e) {
            //driver.quit();
            e.printStackTrace();
        }

        System.out.println("priceChartHref " + output[0]);
        System.out.println("salesRank "+ output[1]);
        if (driver!=null) driver.close();



        return output;



    }

}
