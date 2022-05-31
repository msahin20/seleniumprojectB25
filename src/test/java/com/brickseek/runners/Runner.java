package com.brickseek.runners;

import com.brickseek.brickseek.BrickseekHotDeals;
import org.junit.Assert;
import org.junit.Test;

public class Runner {
    static {

        String [] test = {"1", "2"};
        try {
            BrickseekHotDeals.main(test);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void brickseektest (){
        for (int i = 0; i < 100; i++) {
            System.out.println("hello");
            Assert.assertTrue(true);
        }


    }

}
