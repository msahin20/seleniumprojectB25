package com.brickseek.step_definitons;

import com.brickseek.brickseek.BrickseekHotDeals;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class StepDef {
    static {
        String [] arg = {"1"};
        try {
            BrickseekHotDeals.main(arg);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Given("it starts")
    public void it_starts() {
        System.out.println("Test Started");
    }



    @Then("I should see the results")
    public void i_should_see_the_results() throws InterruptedException {
        for (int i = 0; i <2 ; i++) {
            System.out.println("Hello: " + i);
            Thread.sleep(1500);


        }


    }

}
