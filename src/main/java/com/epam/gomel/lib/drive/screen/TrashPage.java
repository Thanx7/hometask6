package com.epam.gomel.lib.drive.screen;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

import com.epam.gomel.framework.browser.Browser;
import com.epam.gomel.framework.report.Logger;

public class TrashPage extends TopMenuPage {

    public static final String FILE_LOCATOR = "//div[contains(@data-id, 'trash/%s')]";

    public DetailsPage markFile(String name) {
        Browser.current().click(By.xpath(String.format(FILE_LOCATOR, name)));
        return new DetailsPage();
    }

    public boolean isFilePresent(String name) {
        Browser.current().refresh();
        try {
            return Browser.current().isDisplayed(By.xpath(String.format(FILE_LOCATOR, name)));
        } catch (TimeoutException te) {
            Logger.info("Timeout on TrashPage");
            Browser.current().click(MENU_TRASH_LOCATOR);
            Browser.current().refresh();
            return Browser.current().isDisplayed(By.xpath(String.format(FILE_LOCATOR, name)));
        }
    }

}
