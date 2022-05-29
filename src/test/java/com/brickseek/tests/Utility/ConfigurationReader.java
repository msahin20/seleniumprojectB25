package com.brickseek.tests.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {


    // 1- Create the object of the properties
    private static Properties properties = new Properties();


    static {

        try {
            // 2- open the file in java memory
            FileInputStream file = new FileInputStream("configuration.properties");
            // 3-Load the properties object using FileInputStream object
            properties.load(file);

        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public static String getProperty(String key){

        return properties.getProperty(key);

    }



}