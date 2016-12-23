package com.epam.gomel.lib.drive.screen;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

import com.epam.gomel.framework.browser.Browser;
import com.epam.gomel.framework.report.Logger;

public class FilesPage extends TopMenuPage {

    public static final String FILE_LOCATOR = "(//div[contains(@data-id, 'disk/%s')])[1]";
    public static final By TRASH_PICTURE_LOCATOR = By.xpath("//div[@data-id='/trash']");

    public boolean isTrueFile(String name) {
        Browser.current().refresh();
        try {
            return Browser.current().isDisplayed(By.xpath(String.format(FILE_LOCATOR, name)));
        } catch (TimeoutException te) {
            Logger.info("Timeout on FilesPage");
            Browser.current().click(FILES_LOCATOR);
            Browser.current().refresh();
            return Browser.current().isDisplayed(By.xpath(String.format(FILE_LOCATOR, name)));
        }
    }

    public DetailsPage markFile(String name) {
        Browser.current().click(By.xpath(String.format(FILE_LOCATOR, name)));
        return new DetailsPage();
    }

    public void removeFile(String name) {
        Browser.current().dragAndDrop(By.xpath(String.format(FILE_LOCATOR, name)), TRASH_PICTURE_LOCATOR);
    }

    public void removeFiles(List<String> filesName) {
        List<By> locators = new ArrayList<By>();
        for (String fileName : filesName) {
            locators.add(By.xpath(String.format(FILE_LOCATOR, fileName)));
        }

        try {
            dragAndDrop(locators);
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            Logger.info("StaleElementReferenceException on FilesPage");
            Browser.current().refresh();
            dragAndDrop(locators);
        }
    }

    private void dragAndDrop(List<By> locators) {
        By locator = Browser.current().selectSeveralElements(locators);
        Browser.current().dragAndDrop(locator, TRASH_PICTURE_LOCATOR);
    }
}
