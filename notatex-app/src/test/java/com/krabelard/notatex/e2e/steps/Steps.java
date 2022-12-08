package com.krabelard.notatex.e2e.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class Steps {

    WebDriver driver;

    @Given("Google page is open")
    public void googlePageIsOpen() {
        ChromeOptions chromeOptions = new ChromeOptions();

        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver(chromeOptions);

        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        driver.get("https://youtu.be/gDjMZvYWUdo");
    }


    @When("User searches for {string}")
    public void userSearchesFor(String arg0) {

    }

    @Then("Google returns results for {string}")
    public void googleReturnsResultsFor(String arg0) {
    }
}
