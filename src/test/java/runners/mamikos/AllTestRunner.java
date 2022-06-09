package runners.mamikos;

import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
			plugin = {"json:target/results/regression/cucumber-report.json",  "html:target/results/regression"},
			features = "src/test/resources/features",
			glue = "steps",
			tags = {"@addTocard"}
)

public class AllTestRunner extends BaseTestRunner 
{
	
}


