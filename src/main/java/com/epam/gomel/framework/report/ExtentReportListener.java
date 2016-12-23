package com.epam.gomel.framework.report;

import static com.epam.gomel.framework.report.IMdcContext.REPORT_METHOD;
import static com.epam.gomel.framework.report.IMdcContext.REPORT_TEST;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.MDC;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.internal.IResultListener2;
import org.testng.xml.XmlSuite;

import com.epam.gomel.framework.config.GlobalConfig;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentReportListener implements IResultListener2, IReporter {

    static volatile ExtentReports reports =
                    new ExtentReports(GlobalConfig.getInstance().getResultDir() + File.separator + "report.html", true);

    @Override
    public void beforeConfiguration(ITestResult result) {
        onTestStart(result);
    }

    @Override
    public void onConfigurationSuccess(ITestResult result) {
        onTestSuccess(result);
    }

    @Override
    public void onConfigurationFailure(ITestResult result) {
        onTestFailure(result);
    }

    @Override
    public void onConfigurationSkip(ITestResult result) {
        onTestSkipped(result);
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = (ExtentTest) MDC.get(REPORT_TEST);
        ExtentTest method = reports.startTest(result.getName());
        method.setStartedTime(new Date(System.nanoTime()));
        test.appendChild(method);
        MDC.put(REPORT_METHOD, method);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTest method = (ExtentTest) MDC.get(REPORT_METHOD);
        method.setEndedTime(new Date(System.nanoTime()));
        method.log(LogStatus.PASS, "green");
        reports.endTest(method);
        MDC.remove(REPORT_METHOD);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest method = (ExtentTest) MDC.get(REPORT_METHOD);
        method.setEndedTime(Calendar.getInstance().getTime());
        method.log(LogStatus.FAIL, "fail");
        method.log(LogStatus.FAIL, result.getThrowable());
        reports.endTest(method);
        MDC.remove(REPORT_METHOD);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTest method = (ExtentTest) MDC.get(REPORT_METHOD);
        method.setEndedTime(Calendar.getInstance().getTime());
        method.log(LogStatus.SKIP, "skip");
        reports.endTest(method);
        MDC.remove(REPORT_METHOD);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // NO OPS
    }

    @Override
    public void onStart(ITestContext context) {
        ExtentTest test = reports.startTest(context.getName());
        test.setStartedTime(Calendar.getInstance().getTime());
        MDC.put(REPORT_TEST, test);
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentTest test = (ExtentTest) MDC.get(REPORT_TEST);
        test.setEndedTime(Calendar.getInstance().getTime());
        reports.endTest(test);
        MDC.remove(REPORT_TEST);
    }

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        reports.flush();
        reports.close();
    }
}
