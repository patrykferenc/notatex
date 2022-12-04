package com.example.notatex;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
		monochrome = true,
		features = "src/test/resources/features/",
		dryRun = false,
		plugin = { "pretty", "html:target/cucumber-reports/report.html", "json:target/cucumber-reports/cucumber-json-report.json"},
		glue = {"com.example.notatex.stepDefinition"}
)

public class CucumberRunnerTest{

}
