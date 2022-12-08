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

import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

public class ManageAccounts {

    private final WebDriver driver;

    public ManageAccounts() {
        ChromeOptions chromeOptions = new ChromeOptions();
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(chromeOptions);
    }

    public ManageAccounts(WebDriver driver) {
        this.driver = driver;
    }

    private NotatexPage notatexPage() {
        return new NotatexPage(driver.findElement(By.tagName("body")));
    }

    @Given("Login page is open")
    public void loginPageIsOpen() {
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.of(20, SECONDS));
        driver.manage().timeouts().implicitlyWait(Duration.of(5, SECONDS));
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
