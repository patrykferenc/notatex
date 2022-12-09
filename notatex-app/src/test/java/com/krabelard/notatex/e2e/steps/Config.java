package com.krabelard.notatex.e2e.steps;

import io.cucumber.spring.CucumberContextConfiguration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;

import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;


@CucumberContextConfiguration
@SpringBootTest(classes = Config.class)
@ComponentScan
public class Config {
    @Bean
    @Scope("cucumber-glue")
    public WebDriver webDriver(){
        ChromeOptions chromeOptions = new ChromeOptions();
        WebDriverManager.chromedriver().setup();

        WebDriver driver = new ChromeDriver(chromeOptions);

        driver.manage().timeouts().pageLoadTimeout(Duration.of(20, SECONDS));
        driver.manage().timeouts().implicitlyWait(Duration.of(5, SECONDS));

        return driver;
    }
}
