package com.epam.gomel.framework.listener;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.epam.gomel.framework.browser.Browser;
import com.epam.gomel.framework.report.Logger;
import com.epam.gomel.lib.common.Test;

public class CustomListener extends TestListenerAdapter {

    @Override
    public void onStart(ITestContext context) {
        Test.setName(context.getName());
        Test.setSuitName(context.getSuite().getName());
        Test.setStartDate(context.getStartDate().toString());
        super.onStart(context);
        Logger.info("Started test " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        super.onFinish(context);
        Logger.info("Finished test " + context.getName());
    }

    @Override
    public void onTestFailure(ITestResult testResult) {
        super.onTestFailure(testResult);
        Browser.current().takeScreenshot();
        Logger.error("Failed test  " + testResult.getThrowable().getMessage(), testResult.getThrowable());
    }
}
