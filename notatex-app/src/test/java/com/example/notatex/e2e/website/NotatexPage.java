package com.example.notatex.e2e.website;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class NotatexPage {

    private final WebElement pageBody;

    public NotatexPage(WebElement element) {
        this.pageBody = element;
    }

    public WebElement button(String buttonId) {
        return pageBody.findElement(By.xpath("//*[@data-e2e=\"" + buttonId + "\"]"));
    }

    public WebElement textField(String fieldName) {
        return pageBody.findElement(By.xpath("//*[@data-e2e=\"" + fieldName + "\"]"));
    }


}
