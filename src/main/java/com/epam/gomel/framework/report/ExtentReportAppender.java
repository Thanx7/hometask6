package com.epam.gomel.framework.report;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.MDC;
import org.apache.log4j.spi.LoggingEvent;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentReportAppender extends AppenderSkeleton {

    @Override
    protected void append(LoggingEvent event) {
        ExtentTest method;
        switch (event.getLevel().toInt()) {
            case LoggerLevel.TEST_STARTED_INT:
            case LoggerLevel.TEST_FINISHED_INT:
            case LoggerLevel.CONF_STARTED_INT:
            case LoggerLevel.CONF_SUCCESS_INT:
            case LoggerLevel.CONF_FAILED_INT:
            case LoggerLevel.CONF_SKIPPED_INT:
            case LoggerLevel.METHOD_STARTED_INT:
            case LoggerLevel.METHOD_SUCCESS_INT:
            case LoggerLevel.METHOD_FAILED_INT:
            case LoggerLevel.METHOD_SKIPPED_INT:
                break;
            case Level.ERROR_INT:
                method = (ExtentTest) MDC.get(IMdcContext.REPORT_METHOD);
                if (method != null && event.getMessage() != null) {
                    Throwable throwable = event.getThrowableInformation().getThrowable();
                    if (throwable != null) {
                        method.log(LogStatus.ERROR, event.getMessage().toString(), throwable);
                    } else {
                        method.log(LogStatus.ERROR, event.getMessage().toString());
                    }
                }
                break;
            case LoggerLevel.SAVE_INT:
                method = (ExtentTest) MDC.get(IMdcContext.REPORT_METHOD);
                if (method != null && event.getMessage() != null) {
                    method.log(LogStatus.INFO, method.addScreenCapture(event.getMessage().toString()));
                }
                break;
            default:
                method = (ExtentTest) MDC.get(IMdcContext.REPORT_METHOD);
                if (method != null && event.getMessage() != null) {
                    LogStatus level = (level = convertLogLevel(event.getLevel())) == null ? LogStatus.INFO : level;
                    method.log(level, event.getMessage().toString());
                }
                break;
        }
    }

    private LogStatus convertLogLevel(Level level) {
        try {
            return LogStatus.valueOf(level.toString().toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void close() {

    }

    @Override
    public boolean requiresLayout() {
        return false;
    }
}
