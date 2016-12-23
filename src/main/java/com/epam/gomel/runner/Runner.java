package com.epam.gomel.runner;

import static com.epam.gomel.framework.config.GlobalConfig.getInstance;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.testng.ITestNGListener;
import org.testng.TestNG;
import org.testng.xml.Parser;
import org.testng.xml.XmlSuite;
import org.xml.sax.SAXException;

import com.epam.gomel.framework.listener.CustomListener;
import com.epam.gomel.framework.report.ExtentReportListener;
import com.epam.gomel.framework.report.Logger;
import com.epam.gomel.framework.report.TestNgLoggerListener;

public class Runner {

    public Runner(String[] args) {
        parseCli(args);
    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        new Runner(args).runTests();
    }

    private void parseCli(String[] args) {
        Logger.info("Parse cli params...");
        CmdLineParser parser = new CmdLineParser(getInstance());
        try {
            parser.parseArgument(args);
            Logger.info(getInstance().toString());
        } catch (CmdLineException e) {
            Logger.error("Failed to parse cli params: " + e.getMessage(), e);
            parser.printUsage(System.out);
            System.exit(1);
        }
    }

    private void runTests() throws IOException, SAXException, ParserConfigurationException {
        TestNG testNG = new TestNG();
        addListeners(testNG);
        configureSuites(testNG);
        Logger.info("Tests will be started");
        testNG.run();
    }

    private void configureSuites(TestNG testNG) throws ParserConfigurationException, SAXException, IOException {
        List<XmlSuite> suites = new ArrayList<>();
        for (String suitePath : getInstance().getSuites()) {
            InputStream suiteInClassPath = getSuiteInputStream(suitePath);
            if (suiteInClassPath != null) {
                suites.addAll(new Parser(suiteInClassPath).parse());
            } else {
                suites.addAll(new Parser(suitePath).parse());
            }
        }
        for (XmlSuite xmlSuite : suites) {
            testNG.setCommandLineSuite(xmlSuite);
        }
    }

    private void addListeners(TestNG testNG) {
        testNG.setUseDefaultListeners(false);
        List<ITestNGListener> listeners = new ArrayList<ITestNGListener>() {
            {
                add(new ExtentReportListener());
                add(new CustomListener());
                add(new TestNgLoggerListener());
            }
        };
        for (ITestNGListener listener : listeners) {
            testNG.addListener(listener);
        }
    }

    private InputStream getSuiteInputStream(String suite) {
        return this.getClass().getClassLoader().getResourceAsStream(suite);
    }
}
