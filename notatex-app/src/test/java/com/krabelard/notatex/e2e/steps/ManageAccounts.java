package com.krabelard.notatex.e2e.steps;

import com.krabelard.notatex.e2e.website.NotatexPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

@SpringBootTest(classes = Config.class)
public class ManageAccounts {

    @Autowired
    WebDriver driver;

    private NotatexPage notatexPage() {
        return new NotatexPage(driver.findElement(By.tagName("body")));
    }

    @Given("Login page is open")
    public void loginPageIsOpen() {
    }

    @And("User is logged in")
    public void userIsLoggedIn() {
    }


    @When("User fills login field")
    public void userFillsLoginField() {
        notatexPage().textField("login").sendKeys("admin");
    }


    @And("User fills password field")
    public void userFillsPasswordField() {
        notatexPage().textField("password").sendKeys("123");
    }

    @And("User fills confirm password field")
    public void userFillsConfirmPasswordField() {
        notatexPage().textField("confirm_password").sendKeys("123");
    }

    @Then("User cannot log in to the deleted account")
    public void userCannotLogInToTheDeletedAccount() {

    }

    @Then("User is on home page")
    public void userIsOnHomePage() {

    }

    @Then("User can log in to created account")
    public void userCanLogInToCreatedAccount() {

    }
}
