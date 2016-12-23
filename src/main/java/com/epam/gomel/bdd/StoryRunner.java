package com.epam.gomel.bdd;

import java.util.ArrayList;
import java.util.List;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.testng.annotations.Test;

import com.epam.gomel.bdd.steps.YandexDriveSteps;

public class StoryRunner extends JUnitStories {

    @Test(description = "Run stories")
    @Override
    public void run() throws Throwable {
        super.run();
    }

    @Override
    protected List<String> storyPaths() {
        List<String> paths = new ArrayList<String>() {
            {
                add("stories/YandexDrive.story");
            }
        };
        return paths;
    }

    @Override
    public Configuration configuration() {
        return new MostUsefulConfiguration().useStoryLoader(new LoadFromClasspath())
                        .usePendingStepStrategy(new FailingUponPendingStep())
                        .useStoryReporterBuilder(new StoryReporterBuilder().withDefaultFormats()
                                        .withFormats(Format.CONSOLE, Format.TXT));
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new YandexDriveSteps());
    }
}
