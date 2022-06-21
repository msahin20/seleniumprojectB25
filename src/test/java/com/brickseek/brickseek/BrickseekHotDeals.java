package com.brickseek.brickseek;

import com.brickseek.Utility.ConfigurationReader;
import com.brickseek.Utility.Driver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BrickseekHotDeals {


    public static void main(String[] args) throws InterruptedException {
        int waitTime = 10;
        int nPages = 4;
        int pagesToSkip = 0;
        int percentCriteria = 59;
        String onlinePageNewest = "https://brickseek.com/deals?sort=newest";
        String onlinePageBestbuy = "https://brickseek.com/deals/?sort=newest&store_types%5B0%5D=12";
        String storePage = "https://brickseek.com/deals/?sort=newest&type=in-store&pg=3";
        String selectedCategoryPage="https://brickseek.com/deals/?sort=newest&categories%5B0%5D=3&categories%5B1%5D=4&categories%5B2%5D=7&categories%5B3%5D=15&categories%5B4%5D=5&categories%5B5%5D=9&categories%5B6%5D=10&categories%5B7%5D=8&categories%5B8%5D=13&categories%5B9%5D=14&categories%5B10%5D=16&categories%5B11%5D=17&categories%5B12%5D=18&categories%5B13%5D=19";
        String selectedCategoryPage2="https://brickseek.com/deals/?sort=newest&categories%5B0%5D=3&categories%5B1%5D=4&categories%5B2%5D=7&categories%5B3%5D=15&categories%5B4%5D=5&categories%5B5%5D=9&categories%5B6%5D=10&categories%5B7%5D=8&categories%5B8%5D=13&categories%5B9%5D=14&categories%5B10%5D=16&categories%5B11%5D=17&categories%5B12%5D=18&categories%5B13%5D=19&pg=2";
        String toPage = onlinePageNewest;
        ArrayList<String> itemLinks = new ArrayList<>();
        ArrayList<String> percentTexts = new ArrayList<>();
        ArrayList<String> upcLists = new ArrayList<>();
        ArrayList<String> hrefList = new ArrayList<>();
        ArrayList<String> pricesList = new ArrayList<>();
        ArrayList<String> rankList = new ArrayList<>();
        ArrayList<Double> ebayPricesList = new ArrayList<>();
        ArrayList<String> amazonPriceList = new ArrayList<>();
        ArrayList<String> amazonPriceChartList = new ArrayList<>();
        ArrayList<String> salesRankList = new ArrayList<>();






        for (int k = 0; k < nPages; k++) {




            //login to Brickseek page
            Driver.getDriver().get(toPage);

            //Thread.sleep(2000);

            //finding Next Page web element

            try {
                WebElement nextPage = Driver.getDriver().findElement(By.xpath("//a[@class='pagination__next']"));
                toPage = (nextPage.getAttribute("href"));
            } catch (RuntimeException e){
                k=nPages;
                e.printStackTrace();
            }
            if ((k+1)<=pagesToSkip){
                Driver.closeDriver();
                continue;
            }

            //Thread.sleep(3000);

            //sendKeys(Keys.BACK_SPACE + "5"  + Keys.ENTER + Keys.ENTER);
//            driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
            //Thread.sleep(100000);
            //finding deals items percents only
            List<WebElement> dealsList = Driver.getDriver().findElements(By.xpath("//div[@class='item-list__discount-meter-bar-fill-text']"));
            //finding deals View items
            List<WebElement> dealsListClickable = Driver.getDriver().findElements(By.xpath("//a[.='View deal']"));

            List<WebElement> dealsPrices = Driver.getDriver().findElements(By.xpath("//div[@class='item-list__discount-meter-bar-fill-text']/../../../..//div//div[contains(@class, 'price-column item')]"));

            //loop to convert percent to int and then adding href link of that item and name of it to two separate Arraylists

            int i = 0;
            String itemLink = "";
            for (WebElement each : dealsList) {
                //driver.navigate().to("https://login.yahoo.com/?.src=ym&pspid=1197806870&activity=header-signin&.lang=en-US&.intl=us&.done=https%3A%2F%2Flocalhost%3A4080%2Fd");
                //driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);

                //String strPercent= each.getText().substring(0, 2);
                //int intPercent = Integer.parseInt(each.getText().substring(0, 2));

                int intPercent = Integer.parseInt(each.getText().substring(0, each.getText().indexOf('%')));
                String hReflink = "";



                if (intPercent >= percentCriteria) {
                    hReflink= dealsListClickable.get(i).getAttribute("href");

                    hrefList.add(hReflink);


                    itemLinks.add(hReflink);

                    String price = dealsPrices.get(i).getText();
                    String iprice = price.substring(1, (price.indexOf('.'))-1);
                    String dprice = price.substring((price.indexOf('.')+2), (price.indexOf('.'))+4);
                    price = '$' + iprice + '.' + dprice;
                    pricesList.add(price);
                    System.out.println(price);

                    //adding upc
                    //pc upc1 = new upc();
                    //upcLists.add(upc1.upcFind(hReflink));

                    //driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);

                    percentTexts.add(each.getText());

                    System.out.println(percentTexts.size());
                    //System.out.println(itemLink);

                }

                i++;
            }
            Driver.closeDriver();

            for (int i1 = 0; i1<itemLinks.size(); i1++) {
                //adding upc
                //upcFind("https://brickseek.com/deal/swift-home-microfiber-solid-sheet/585259");
                //upcFind("https://brickseek.com/deal/fossil-women-s-42mm-charter-stainless/585280");


                String upcHref = itemLinks.get(i1);

                //upcFind(upcHref);

//                try {

                    String[] arrayStr = upc.upcFind(upcHref);
                    upcLists.add(arrayStr[0]);
                    rankList.add(arrayStr[1]);
//                }catch (RuntimeException e) {
//                    upcLists.add("N/A");
//                    rankList.add("N/A");
//                    Driver.closeDriver();
//                    e.printStackTrace();
//                }

                //driver.quit();

            }

            Driver.closeDriver();


            for (int i1 = 0; i1<itemLinks.size(); i1++) {

                if(!(upcLists.get(i1).equals("N/A"))){
                    continue;
                }else {


                    //adding upc
                    //upcFind("https://brickseek.com/deal/swift-home-microfiber-solid-sheet/585259");
                    //upcFind("https://brickseek.com/deal/fossil-women-s-42mm-charter-stainless/585280");


                    String upcHref = itemLinks.get(i1);

                    //upcFind(upcHref);

//                try {

                    String[] arrayStr = upc.upcFind2(upcHref);
                    upcLists.set(i1, arrayStr[0]);
                    rankList.set(i1,arrayStr[1]);
//                }catch (RuntimeException e) {
//                    upcLists.add("N/A");
//                    rankList.add("N/A");
//                    Driver.closeDriver();
//                    e.printStackTrace();
//                }

                    //driver.quit();
                }

            }
            Driver.closeDriver();


            for (int i1 = 0; i1<itemLinks.size(); i1++) {

                if(!(upcLists.get(i1).equals("N/A"))){
                    continue;
                }else {


                    //adding upc
                    //upcFind("https://brickseek.com/deal/swift-home-microfiber-solid-sheet/585259");
                    //upcFind("https://brickseek.com/deal/fossil-women-s-42mm-charter-stainless/585280");


                    String upcHref = itemLinks.get(i1);

                    //upcFind(upcHref);

//                try {
                    Thread.sleep(30000);

                    String[] arrayStr = upc.upcFind(upcHref);
                    upcLists.set(i1, arrayStr[0]);
                    rankList.set(i1,arrayStr[1]);

//                }catch (RuntimeException e) {
//                    upcLists.add("N/A");
//                    rankList.add("N/A");
//                    Driver.closeDriver();
//                    e.printStackTrace();
//                }

                    //driver.quit();
                }

            }

            Driver.closeDriver();




            //System.out.println("upcs");
            //System.out.println(upcLists);
            //System.out.println("______________");

            for (int i3 = 0; i3 < upcLists.size(); i3++) {
                if ((upcLists.get(i3).equals("N/A"))) {
                    continue;
                } else {
                    String upcString = upcLists.get(i3);
//                String ePrice = AmazonPriceTrack.amazonPrice(upcString);
//                amazonPriceList.add(ePrice);

                    //adding also PriceChart
                    String[] strArr = AmazonPriceTrack.priceChart2(upcString);
                    String priceChartHref = strArr[0];
                    amazonPriceChartList.add(priceChartHref);

                    //adding also salesRank in Category
                    String salesRank = strArr[1];
                    salesRankList.add(salesRank);


                }
            }
                Driver.closeDriver();

            for (int i2 = 0; i2 < upcLists.size(); i2++) {

                String upcString = upcLists.get(i2);
                Double ePrice = EbaySold.ebayLink(upcString);
                ebayPricesList.add(ePrice);


            }
            Driver.closeDriver();
            List<Map<String, String>> mapList = new ArrayList<>();
            for (int j1 = 0; j1 < itemLinks.size(); j1++) {
                Map<String, String> map = new LinkedHashMap<>();
                map.put("ItemLink", itemLinks.get(j1));
                map.put("PercentOff", percentTexts.get(j1));
                map.put("Upc", upcLists.get(j1));
                map.put("Price", pricesList.get(j1));
                map.put("Ranking", rankList.get(j1));
                map.put("SalesRank", salesRankList.get(j1));
                map.put("EbayAvePrice", (ebayPricesList.get(j1).toString()));
                map.put("AmazonPriceChart", amazonPriceChartList.get(j1));


                mapList.add(map);
            }
            int y = 0;
            for (Map<String, String> eachMap : mapList) {


                if(eachMap.get("AmazonPriceChart")!=null) {

                    if (eachMap.get("AmazonPriceChart").equals("N/A") && (!eachMap.get("Upc").equals("N/A"))) {
                        System.out.println("eachMap.get(\"Upc\")  = " + eachMap.get("Upc"));
                        String[] strArr2 = AmazonPriceTrack.priceChart3(eachMap.get("Upc"));
                        String priceChartHref2 = strArr2[0];
                        System.out.println("eachMap.get(\"AmazonPriceChart\") (before update) = " + eachMap.get("AmazonPriceChart"));
                        eachMap.put("AmazonPriceChart", priceChartHref2);
                        System.out.println(eachMap.get("AmazonPriceChart"));
                        amazonPriceChartList.set(y, priceChartHref2);


                    }
                }
                Driver.closeDriver();



                y++;

            }
            y = 0;
            for (Map<String, String> eachMap : mapList) {

                if(eachMap.get("AmazonPriceChart")!=null) {
                    if (eachMap.get("AmazonPriceChart").equals("N/A")) {
                        System.out.println("eachMap.get(\"Upc\") = " + eachMap.get("Upc"));
                        String[] strArr2 = AmazonPriceTrack.priceChart2(eachMap.get("Upc"));
                        String priceChartHref2 = strArr2[0];
                        eachMap.put("AmazonPriceChart", priceChartHref2);
                        amazonPriceChartList.set(y, priceChartHref2);


                    }
                    else System.out.println("eachMap.get(\"AmazonPriceChart\") WHY IT WAS NULL= " + eachMap.get("AmazonPriceChart"));
                }



                y++;

            }


            y = 0;
            for (Map<String, String> eachMap : mapList) {

                if(eachMap.get("AmazonPriceChart")!=null) {

                    if (eachMap.get("AmazonPriceChart").equals("N/A")) {
                        System.out.println("eachMap.get(\"Upc\") = " + eachMap.get("Upc"));
                        String[] strArr2 = AmazonPriceTrack.priceChart2(eachMap.get("Upc"));
                        String priceChartHref2 = strArr2[0];
                        eachMap.put("Upc", priceChartHref2);
                        amazonPriceChartList.set(y, priceChartHref2);


                    }
                }



                y++;

            }

            y=0;






//            driver = new ChromeDriver();
//            driver.manage().window().maximize();
//
//
//            driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);

            //login to yahoo mail and enter username and password


try {


    Driver.getDriver().get("https://login.yahoo.com/?.src=ym&pspid=1197806870&activity=header-signin&.lang=en-US&.intl=us&.done=https%3A%2F%2Flocalhost%3A4080%2Fd");

    WebElement mailMousePointer = Driver.getDriver().findElement(By.name("username"));
    Thread.sleep(1000);
    mailMousePointer.sendKeys(ConfigurationReader.getProperty("username") + Keys.ENTER);
    Thread.sleep(1000);
    Driver.getDriver().findElement(By.id("login-passwd")).sendKeys(ConfigurationReader.getProperty("password") + Keys.ENTER);
    Thread.sleep(2500);
    Driver.getDriver().findElement(By.linkText("Compose")).click();
    Thread.sleep(2500);
    Driver.getDriver().findElement(By.xpath("//input[@id='message-to-field']")).sendKeys("mrtshnmetu@yahoo.com" + Keys.ENTER);
    Thread.sleep(1000);


    for (int j1 = 0; j1 < itemLinks.size(); j1++) {

        //System.out.println("itemLinks = " + itemLinks.size());


        Driver.getDriver().findElement(By.xpath("//*[@id=\"editor-container\"]/div[1]/div[1]")).sendKeys("____________________________________________"
                + Keys.ENTER + itemLinks.get(j1) + Keys.ENTER);
        Thread.sleep(2000);
        Driver.getDriver().findElement(By.xpath("//*[@id=\"editor-container\"]/div[1]/div[1]")).sendKeys(" Page " + k + Keys.ENTER
                + percentTexts.get(j1) + Keys.ENTER
                + upcLists.get(j1) + Keys.ENTER
                + "price " + pricesList.get(j1) + Keys.ENTER
                + rankList.get(j1) + Keys.ENTER
                + salesRankList.get(j1) + Keys.ENTER
                + "Ebay Ave Price  " + ebayPricesList.get(j1) + Keys.ENTER
                //+  amazonPriceList.get(j1)+ Keys.ENTER
                + amazonPriceChartList.get(j1) + Keys.SPACE + Keys.ENTER
                + "__________________________________________________"
                + Keys.ENTER + Keys.ENTER + Keys.ENTER + Keys.ENTER);
    }

    Driver.getDriver().findElement(By.xpath("//input[@class='q_T y_Z2hYGcu je_0 jb_0 X_0 N_fq7 G_e A_6EqO C_Z281SGl ir_0 P_0 bj3_Z281SGl b_0 j_n d_72FG em_N']")).sendKeys("deal page" + k);

    Driver.getDriver().findElement(By.xpath("//button[.='Send']")).click();
    System.out.println("Email was successfully sent!!!!!!!!!!!!!!!");
    Driver.closeDriver();
} catch (RuntimeException e){
    System.out.println("Email COULD NOT BE SENT");

    Driver.closeDriver();
    System.out.println("mapList = " + mapList);
    e.printStackTrace();
}


            i=0;
            itemLinks.clear();
            percentTexts.clear();
            dealsList.clear();
            dealsListClickable.clear();
            upcLists.clear();
            pricesList.clear();
            rankList.clear();
            ebayPricesList.clear();
            amazonPriceList.clear();
            amazonPriceChartList.clear();
            salesRankList.clear();
            mapList.clear();



        }
    }


    public  static String[] upcFind (String itemLink) {

        String output[] = new String[2];

        WebDriverManager.chromedriver().setup();

        WebDriver driverObj = new ChromeDriver();
        driverObj.manage().window().maximize();
        driverObj.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        System.out.println(itemLink);
        driverObj.navigate().to(itemLink);

        //driverObj.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String updatedUrl = driverObj.findElement(By.xpath("//*[@id=\"main\"]/div/div[1]/div[2]/div[3]/div/a[2]")).getAttribute("href");
        driverObj.close();
        //System.out.println(updatedUrl);
        driverObj = new ChromeDriver();
        //driverObj.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driverObj.manage().window().maximize();
        driverObj.navigate().to(updatedUrl);
        driverObj.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //String UPCText = driverObj.findElement(By.xpath("//*[@id=\"main\"]/div/div[1]/div[2]/div[1]/div/div[3]")).getText();
        //div[@class='item-overview__meta-item']
        String rankingText = "Ranking:  " + driverObj.findElement(By.xpath("//div[@class='item-overview__meta-item']")).getText();
        if (rankingText.contains("MSRP") || rankingText.contains("UPC")) {
            rankingText="No Ranking";
        }
        System.out.println(rankingText);
        String UPCText = driverObj.findElement(By.xpath("//*[contains(text(),'View barcode')]/../..")).getText();

        UPCText = UPCText.substring(5);
        UPCText = UPCText.substring(0,UPCText.indexOf(' '));
        output[0]=UPCText;
        output [1] = rankingText;
        System.out.println(UPCText);
        driverObj.close();
        return output;
    }






}
