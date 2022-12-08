package com.krabelard.notatex.e2e.stepDefinition;

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

import java.util.concurrent.TimeUnit;

public class ManageNotesSteps {

    WebDriver driver;

    private NotatexPage notatexPage() {
        return new NotatexPage(driver.findElement(By.tagName("body")));
    }

    @Given("Application page is open")
    public void AppPageIsOpen() {
        ChromeOptions chromeOptions = new ChromeOptions();

        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver(chromeOptions);

        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        driver.get("https://google.com/");
    }


    @When("User clicks {string} button")
    public void userClicksButton(String buttonId) {
        notatexPage().button(buttonId).click();
    }

    @And("User adds text to note")
    public void userAddsTextToNote() {
        notatexPage().textField("edit").sendKeys("");
    }

    @And("User is logged in")
    public void userIsLoggedIn() {
        ManageAccounts manageAccounts = new ManageAccounts(driver);
        manageAccounts.userFillsLoginField();
        manageAccounts.userFillsPasswordField();
        notatexPage().button("log in").click();
    }

    @And("User has at least one note in note list")
    public void userHasAtLeastOneNoteInNoteList() {

    }

    @And("User selects note to add")
    public void userSelectsNoteToAdd() {

    }

    @Then("Note is in note list")
    public void noteIsInNoteList() {

    }

    @Then("Note is not in note list")
    public void noteIsNotInNoteList() {

    }

    @Then("Note is displayed in .tex format")
    public void noteIsDisplayedInTexFormat() {

    }

    @Then("Edited note contains added text")
    public void editedNoteContainsAddedText() {

    }

    @When("User selects note from note list")
    public void userSelectsNoteFromNoteList() {

    }

    @Then("Note is displayed in pure text format")
    public void noteIsDisplayedInPureTextFormat() {

    }
}
