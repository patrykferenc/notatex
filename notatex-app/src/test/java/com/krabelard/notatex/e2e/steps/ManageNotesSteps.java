package com.krabelard.notatex.e2e.steps;

import com.krabelard.notatex.e2e.website.NotatexPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.time.temporal.ChronoUnit.SECONDS;

public class ManageNotesSteps {

    WebDriver driver;

    int noteCount;

    private NotatexPage notatexPage() {
        return new NotatexPage(driver.findElement(By.tagName("body")));
    }

    @Given("Application page is open")
    public void AppPageIsOpen() {
        ChromeOptions chromeOptions = new ChromeOptions();

        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver(chromeOptions);

        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.of(20, SECONDS));
        driver.manage().timeouts().implicitlyWait(Duration.of(5, SECONDS));

        driver.get("http://localhost:3000/");
    }


    @When("User clicks {string}")
    public void userClicksButton(String buttonId) {
        notatexPage().button(buttonId).click();
    }

    @And("User adds text to note")
    public void userAddsTextToNote() {
        notatexPage().textField("edit").sendKeys("");
    }

    @And("User is logged in")
    public void userIsLoggedIn() {

    }

    @And("User fills {string} with {string}")
    public void userFillsWith(String fieldname, String content) {
        notatexPage().textField(fieldname).sendKeys(content);
    }

    @And("Note count is known")
    public void noteCountIsKnown() {
        List<WebElement> notes = driver.findElements(By.className("col"));
        noteCount = notes.size();
    }

    @And("Note is in note list")
    public void noteIsInNoteList() {
        List<WebElement> notes = driver.findElements(By.className("col"));
        int noteCountAfter = notes.size();
        int difference = noteCountAfter - noteCount;
        Assertions.assertEquals(1, difference);
    }
}
