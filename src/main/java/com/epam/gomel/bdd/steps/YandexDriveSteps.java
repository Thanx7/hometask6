package com.epam.gomel.bdd.steps;

import static org.testng.Assert.assertTrue;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.epam.gomel.framework.browser.Browser;
import com.epam.gomel.lib.common.Account;
import com.epam.gomel.lib.common.AccountBuilder;
import com.epam.gomel.lib.drive.service.LoginService;

public class YandexDriveSteps {

    Account correctAccount = AccountBuilder.getDefaultAccount();
    Account wrongAccount = AccountBuilder.getAccountWithWrongPass();

    @Given("a login page")
    public void givenALoginPage() {
        LoginService.login(correctAccount);
    }

    @When("I try to login with $param account")
    public void logging(String param) {
        if (param == "correct") {
            LoginService.login(correctAccount);
        } else {
            LoginService.login(wrongAccount);
        }
    }

    @Then("is opened drive page")
    public void thenIsOpenedDrivePage() {
        assertTrue(LoginService.isSuccessfulLogin());
        Browser.current();
        Browser.kill();
    }

    @Then("I see the error message")
    public void thenISeeTheErrorMessage() {
        assertTrue(LoginService.isErrorMessageDisplayed());
        Browser.current();
        Browser.kill();
    }
}
