package Runners;

import java.io.IOException;

import TestCases.TestBase;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/java/features",
glue = {"Steps"},
plugin = {"pretty","html:target/cucumber-html-report"})
public class TestRunner extends TestBase {

	public TestRunner() throws IOException {
		super();
	}

}
