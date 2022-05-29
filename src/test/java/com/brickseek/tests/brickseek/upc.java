package com.brickseek.tests.brickseek;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;


public class upc {
 public static void main(String[] args) throws Exception {
  upcFind("https://brickseek.com/deal/just-mercy-dvd-digital/586257");

 }


 public  static String[] upcFind (String itemLink) {

  String [] output = new String[2];
  try {


   //String output[] = new String[2];

   WebDriverManager.chromedriver().setup();

   WebDriver driverObj = new ChromeDriver();
   driverObj.manage().window().maximize();
   driverObj.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
   //System.out.println(itemLink);
   driverObj.navigate().to(itemLink);

   //driverObj.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
   String updatedUrl = driverObj.findElement(By.xpath("//*[@id=\"main\"]/div/div[1]/div[2]/div[3]/div/a[2]")).getAttribute("href");
   driverObj.quit();
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
    rankingText = "No Ranking";
   }
   //System.out.println(rankingText);
   String UPCText = driverObj.findElement(By.xpath("//*[contains(text(),'View barcode')]/../..")).getText();
   driverObj.quit();

   UPCText = UPCText.substring(5);
   UPCText = UPCText.substring(0, UPCText.indexOf(' '));
   output[0] = UPCText;
   output[1] = rankingText;
  } catch (RuntimeException e) {
   output[0] = "N/A";
   output[1] = "N/A";
   e.printStackTrace();
  }
  System.out.println(output[0]);



  return output;

    }
}