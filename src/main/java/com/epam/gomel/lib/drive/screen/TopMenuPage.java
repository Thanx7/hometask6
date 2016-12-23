package com.epam.gomel.lib.drive.screen;

import org.openqa.selenium.By;

import com.epam.gomel.framework.browser.Browser;
import com.epam.gomel.framework.report.Logger;

public class TopMenuPage {

    public static final By FILES_LOCATOR = By.xpath("//a[@href='/client/disk']");
    public static final By MENU_TRASH_LOCATOR = By.xpath("//a[@href='/client/trash']");
    public static final By UPLOAD_INPUT_LOCATOR = By.className("button__attach");
    public static final By UPLOAD_BUTTON_LOCATOR = By.xpath("//*[@class='button__attach']/..");

    public static final By FINISHED_UPLOADING_LOCATOR = By.cssSelector(".b-item-upload__icon.b-item-upload__icon_done");
    public static final By CLOSE_UPLOAD_NOTIFICATION_LOCATOR = By.cssSelector("._nb-popup-close.ns-action.js-cross");

    public boolean isSuccessfulLogin() {
        return Browser.current().isDisplayed(UPLOAD_BUTTON_LOCATOR);
    }

    public FilesPage openFilesPage() {
        try {
            Browser.current().click(FILES_LOCATOR);
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            Logger.info("StaleElementReferenceException on TopMenuPage");
            Browser.current().refresh();
            Browser.current().click(FILES_LOCATOR);
        }
        return new FilesPage();
    }

    public TrashPage openTrashPage() {
        Browser.current().refresh();
        Browser.current().click(MENU_TRASH_LOCATOR);
        return new TrashPage();
    }

    public void uploadFile(String path) {
        Browser.current().waitForElementPresent(UPLOAD_BUTTON_LOCATOR);
        Browser.current().typeInUploadInput(UPLOAD_INPUT_LOCATOR, path);
        Browser.current().waitForElementPresent(FINISHED_UPLOADING_LOCATOR);
        Browser.current().click(CLOSE_UPLOAD_NOTIFICATION_LOCATOR);
    }

}
