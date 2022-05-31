package com.brickseek.Utility;

import com.brickseek.brickseek.AmazonPriceTrack;
import com.brickseek.brickseek.EbaySold;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;



public class Walmart {
    public static void main(String[] args) throws InterruptedException {
        String clearancePage ="https://www.walmart.com/browse/video-games/2636?facet=special_offers%3AClearance&sort=price_low";
        String rollPage = "https://www.walmart.com/browse/0/0?_refineresult=true&facet=special_offers%3ARollback";
        String toPage = clearancePage;
        ArrayList<String> webLinks = new ArrayList<>();
        ArrayList<String> upcList = new ArrayList<>();
        ArrayList<Double> ebayPricesList = new ArrayList<>();
        ArrayList<String> amazonPriceList = new ArrayList<>();
        ArrayList<String> amazonPriceChartList = new ArrayList<>();
        ArrayList<String> priceList = new ArrayList<>();
        ArrayList<String> hRefLink = new ArrayList<>();
        ArrayList<String> salesRank = new ArrayList<>();

        int waitTime = 10;
        int numberofDeals = 30;
        int group = 1;


        WebDriverManager.edgedriver().setup();
        WebDriver driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //login to Brickseek page
        driver.get(toPage);


        for (WebElement element : driver.findElements(By.xpath("//a[@class='absolute w-100 h-100 z-1']"))) {

            //String WalmartLink = element.getAttribute("hRef");
            hRefLink.add(element.getAttribute("hRef"));
        }
        driver.quit();

        int i = 0;
        int j = numberofDeals * (group - 1);


        for (int z1 = j; z1 < j + numberofDeals; z1++) {


            String WalmartLink = hRefLink.get(z1);


            webLinks.add(WalmartLink);
            String walArray[] = getUPCfromWalmart2(WalmartLink);
            String upc = walArray[0];
            String price = walArray[1];
            if (upc.contains("ng")) {
                Thread.sleep(30000);
                walArray = getUPCfromWalmart(WalmartLink);
                upc = walArray[0];
                price = walArray[1];


            }




            upcList.add(upc);
            priceList.add(price);

            //String upcString = upcList.get(i);
            Double ePrice = EbaySold.ebayLink(upc);
            ebayPricesList.add(ePrice);


            String eAPrice = AmazonPriceTrack.amazonPrice(upc);
            amazonPriceList.add(eAPrice);
            //adding also PriceChart
            String amazonArr[] = AmazonPriceTrack.priceChart2(upc);
            String priceChartHref = amazonArr[0];
            //String priceChartHref = AmazonPriceTrack.priceChart(upc);
            amazonPriceChartList.add(priceChartHref);

            //adding Sales rank
            salesRank.add(amazonArr[1]);


        }


        driver = new ChromeDriver();
        driver.manage().window().maximize();


        driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);

        //login to yahoo mail and enter username and password

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


        //System.out.println("itemLinks = " + itemLinks.size());
        for (int j1 = 0; j1 < webLinks.size(); j1++) {

            driver.findElement(By.xpath("//*[@id=\"editor-container\"]/div[1]/div[1]")).sendKeys("____________________________________________"
                    + Keys.ENTER + webLinks.get(j1) + Keys.ENTER);
            Thread.sleep(3000);
            driver.findElement(By.xpath("//*[@id=\"editor-container\"]/div[1]/div[1]")).sendKeys(" Page " + "0" + Keys.ENTER
                    //  + percentTexts.get(j1) + Keys.ENTER
                    + upcList.get(j1) + Keys.ENTER
                    + "price " + priceList.get(j1) + Keys.ENTER
                    //  + rankList.get(j1) + Keys.ENTER
                    + "Ebay Ave Price  " + ebayPricesList.get(j1) + Keys.ENTER
                    + amazonPriceList.get(j1) + Keys.ENTER
                    + amazonPriceChartList.get(j1) + Keys.SPACE + Keys.ENTER
                    + salesRank.get(j1) + Keys.ENTER
                    + "__________________________________________________"
                    + Keys.ENTER + Keys.ENTER + Keys.ENTER + Keys.ENTER);
        }

        driver.findElement(By.xpath("//*[@id=\"mail-app-component\"]/div/div/div[1]/div[3]/div/div/input")).sendKeys("deal page" + "W");


        driver.findElement(By.xpath("//*[@id=\"mail-app-component\"]/div/div/div[2]/div[2]/div/button/span")).click();
        i = 0;
        webLinks.clear();
        //percentTexts.clear();
        //dealsList.clear();
        //dealsListClickable.clear();
        upcList.clear();
        priceList.clear();
        //rankList.clear();
        ebayPricesList.clear();
        amazonPriceList.clear();
        amazonPriceChartList.clear();
        salesRank.clear();
        hRefLink.clear();
        driver.close();


    }

    public static void main2(String[] args) {
        getUPCfromWalmart("https://www.walmart.com/ip/Cosco-Three-Step-Steel-Folding-Step-Stool-with-200-lb-Load-Capacity/139730785?athbdg=L1600");
    }


    public static String[] getUPCfromWalmart(String toPage) {
        String upc = "Not found";
        String price = "N/A";
        String pageSrc = "";
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {

            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            //login to Brickseek page
            driver.get(toPage);
            Thread.sleep(3000);
             pageSrc = driver.getPageSource();
            System.out.println(pageSrc);
            int l1 = pageSrc.indexOf("gtin13") + 9;
            int l3 = pageSrc.indexOf("priceCurrency") + 29;
            System.out.println("we were here");
            upc = pageSrc.substring(l1, l1 + 12);
            System.out.println("price-1 here");
            price = pageSrc.substring(l3, l3 + 7);
            //System.out.println(price);
            //System.out.println("price0 here");
            price = price.substring(0, price.indexOf(','));
            System.out.println("price1 here");
            driver.close();

            System.out.println(price);


            if (upc.contains("ng")) {

                Thread.sleep(30000);
                WebDriverManager.edgedriver().setup();
                WebDriver driver2 = new EdgeDriver();
                driver2.manage().window().maximize();
                driver2.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

                //login to Brickseek page
                driver2.get(toPage);
                //Thread.sleep(3000);
                pageSrc = driver2.getPageSource();
                //System.out.println(pageSrc);
                int l11 = pageSrc.indexOf("gtin13") + 9;
                int l33 = pageSrc.indexOf("price") + 29;
                upc = pageSrc.substring(l11, l11 + 12);
                price = pageSrc.substring(l33, l33 + 7);
                price = price.substring(0, price.indexOf(','));
                System.out.println("price2 here");
                driver2.close();
                //Thread.sleep(30000);


            } else if (price.contains("title")) {
                Thread.sleep(30000);
                WebDriverManager.firefoxdriver().setup();
                WebDriver driver3 = new FirefoxDriver();
                driver3.manage().window().maximize();
                driver3.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

                //login to Brickseek page
                driver3.get(toPage);
                //Thread.sleep(3000);
                pageSrc = driver3.getPageSource();
                int l2 = pageSrc.indexOf("gtin13") + 9;
                int l4 = pageSrc.indexOf("price") + 29;
                //upc = pageSrc.substring(l2, l2 + 12);
                price = pageSrc.substring(l4, l4 + 7);
                price = price.substring(0, price.indexOf(','));
                //System.out.println("price3 here");


                driver3.close();
                //Thread.sleep(30000);

            }


        } catch (RuntimeException | InterruptedException e) {
            //System.out.println("it was caught here");
            driver.close();
            e.printStackTrace();
        }

        System.out.println(upc);
        System.out.println(price);
        if(!(Character.isDigit(upc.charAt(0)))){
            upc= "N/A";

        }
        String[] output = {upc, price};

        return output;


    }

    public static String[] getUPCfromWalmart2(String toPage) {
        String upc = "Not found";
        String price = "N/A";
        String pageSrc = "";
        WebDriverManager.edgedriver().setup();
        WebDriver driver = new EdgeDriver();

        try {

            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            //login to Brickseek page
            driver.get(toPage);
            Thread.sleep(3000);
            pageSrc = driver.getPageSource();
            int l1 = pageSrc.indexOf("gtin13") + 9;
            int l3 = pageSrc.indexOf("priceCurrency") + 29;
            System.out.println("we were here");
            upc = pageSrc.substring(l1, l1 + 12);
            System.out.println("price-1 here");
            price = pageSrc.substring(l3, l3 + 7);
            //System.out.println(price);
            //System.out.println("price0 here");
            price = price.substring(0, price.indexOf(','));
            System.out.println("price1 here");
            driver.close();

            System.out.println(price);


            if (upc.contains("ng")) {

                Thread.sleep(30000);
                WebDriverManager.chromedriver().setup();
                WebDriver driver2 = new ChromeDriver();
                driver2.manage().window().maximize();
                driver2.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

                //login to Brickseek page
                driver2.get(toPage);
                //Thread.sleep(3000);
                pageSrc = driver2.getPageSource();
                int l11 = pageSrc.indexOf("gtin13") + 9;
                int l33 = pageSrc.indexOf("price") + 29;
                upc = pageSrc.substring(l11, l11 + 12);
                price = pageSrc.substring(l33, l33 + 7);
                price = price.substring(0, price.indexOf(','));
                System.out.println("price2 here");
                driver2.close();
                //Thread.sleep(30000);


            } else if (price.contains("title")) {
                Thread.sleep(30000);
                WebDriverManager.firefoxdriver().setup();
                WebDriver driver3 = new FirefoxDriver();
                driver3.manage().window().maximize();
                driver3.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

                //login to Brickseek page
                driver3.get(toPage);
                //Thread.sleep(3000);
                pageSrc = driver3.getPageSource();
                int l2 = pageSrc.indexOf("gtin13") + 9;
                int l4 = pageSrc.indexOf("price") + 29;
                //upc = pageSrc.substring(l2, l2 + 12);
                price = pageSrc.substring(l4, l4 + 7);
                price = price.substring(0, price.indexOf(','));
                //System.out.println("price3 here");


                driver3.close();
                //Thread.sleep(30000);

            }


        } catch (RuntimeException | InterruptedException e) {
            //System.out.println("it was caught here");
            driver.close();
            e.printStackTrace();
        }

        System.out.println(upc);
        System.out.println(price);
        String[] output = {upc, price};

        return output;


    }


//erase the below if above worked fine
    public static String[] getUPCfromWalmart3(String toPage) {
        String upc = "Not found";
        String price = "N/A";
        WebDriverManager.edgedriver().setup();
        WebDriver driver = new EdgeDriver();

        try {

            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            //login to Brickseek page
            driver.get(toPage);
            Thread.sleep(3000);
            String pageSrc = driver.getPageSource();
            int l1 = pageSrc.indexOf("gtin13") + 9;
            int l3 = pageSrc.indexOf("price") + 29;
            upc = pageSrc.substring(l1, l1 + 12);
            price = pageSrc.substring(l3, l3 + 7);
            price = price.substring(0, price.indexOf(','));
            driver.close();

            System.out.println(price);

            if (upc.contains("ng")) {

                Thread.sleep(30000);
                WebDriverManager.chromedriver().setup();
                WebDriver driver2 = new ChromeDriver();
                driver2.manage().window().maximize();
                driver2.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

                //login to Brickseek page
                driver2.get(toPage);
                //Thread.sleep(3000);
                pageSrc = driver2.getPageSource();
                l1 = pageSrc.indexOf("gtin13") + 9;
                l3 = pageSrc.indexOf("price") + 29;
                upc = pageSrc.substring(l1, l1 + 12);
                price = pageSrc.substring(l3, l3 + 7);
                price = price.substring(0, price.indexOf(','));
                driver2.close();
                //Thread.sleep(30000);


            } else if (price.contains("title")) {
                Thread.sleep(30000);
                WebDriverManager.firefoxdriver().setup();
                WebDriver driver2 = new FirefoxDriver();
                driver2.manage().window().maximize();
                driver2.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

                //login to Brickseek page
                driver2.get(toPage);
                //Thread.sleep(3000);
                pageSrc = driver2.getPageSource();
                //l1 = pageSrc.indexOf("gtin13") + 9;
                l3 = pageSrc.indexOf("price") + 29;
                //upc = pageSrc.substring(l1, l1 + 12);
                price = pageSrc.substring(l3, l3 + 7);
                price = price.substring(0, price.indexOf(','));
                driver2.close();
                //Thread.sleep(30000);

            }


        } catch (RuntimeException | InterruptedException e) {
            System.out.println("it was here");
            driver.close();
            e.printStackTrace();
        }

        System.out.println(upc);
        System.out.println(price);
        String[] output = {upc, price};

        return output;


    }
}

