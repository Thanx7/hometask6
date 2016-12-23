package com.epam.gomel.framework.report;

import static com.epam.gomel.framework.report.LoggerLevel.CONF_FAILED;
import static com.epam.gomel.framework.report.LoggerLevel.CONF_SKIPPED;
import static com.epam.gomel.framework.report.LoggerLevel.CONF_STARTED;
import static com.epam.gomel.framework.report.LoggerLevel.CONF_SUCCESS;
import static com.epam.gomel.framework.report.LoggerLevel.METHOD_FAILED;
import static com.epam.gomel.framework.report.LoggerLevel.METHOD_SKIPPED;
import static com.epam.gomel.framework.report.LoggerLevel.METHOD_STARTED;
import static com.epam.gomel.framework.report.LoggerLevel.METHOD_SUCCESS;
import static com.epam.gomel.framework.report.LoggerLevel.TEST_FINISHED;
import static com.epam.gomel.framework.report.LoggerLevel.TEST_STARTED;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.internal.IResultListener2;

public class TestNgLoggerListener implements IResultListener2 {

    @Override
    public void beforeConfiguration(ITestResult itr) {
        Logger.log(CONF_STARTED, itr.getName());
    }

    @Override
    public void onConfigurationSuccess(ITestResult itr) {
        Logger.log(CONF_SUCCESS, itr.getName());
    }

    @Override
    public void onConfigurationFailure(ITestResult itr) {
        Logger.log(CONF_FAILED, itr.getName());
    }

    @Override
    public void onConfigurationSkip(ITestResult itr) {
        Logger.log(CONF_SKIPPED, itr.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        Logger.log(METHOD_STARTED, result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Logger.log(METHOD_SUCCESS, result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Logger.log(METHOD_FAILED,
                        result.getTestClass().getRealClass().getSimpleName() + "." + result.getMethod().getMethodName(),
                        result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        Logger.log(METHOD_SKIPPED, result.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // NO OPS
    }

    @Override
    public void onStart(ITestContext context) {
        Logger.log(TEST_STARTED, context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        Logger.log(TEST_FINISHED, context.getName());
    }
}
