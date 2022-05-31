package com.brickseek.runners;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                //"pretty",
                "html:target/cucumber-report.html",
                "rerun:target/rerun.txt",
                "me.jvt.cucumber.report.PrettyReports:target/cucumber",

        },

        features = "src/test/resources/features",
        glue = "com/brickseek/step_definitons",


        dryRun = false,
        tags = "@smoke",
        publish = false

)
public class CukesRunner {
}

