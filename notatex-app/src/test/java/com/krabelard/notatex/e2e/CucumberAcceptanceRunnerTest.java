package com.krabelard.notatex.e2e;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/scenarios/acceptance",
        plugin = {"pretty", "html:target/cucumber-reports/report.html", "json:target/cucumber-reports/cucumber-json-report.json"},
        glue = {"com.example.notatex.e2e.stepDefinition"}
)

public class CucumberAcceptanceRunnerTest {

}
