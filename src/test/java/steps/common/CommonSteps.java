package steps.common;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import utilities.Constants;
import utilities.SeleniumHelpers;
import utilities.ThreadManager;

public class CommonSteps {

	private WebDriver driver = ThreadManager.getDriver();
	private SeleniumHelpers selenium = new SeleniumHelpers(driver);


		/**
		 * <p> If base url https://owner-jambu.kerupux.com then format should be owner /{path}
		 * <p> If base url https://jambu.kerupux.com then format should be mamikos /{path}
		 * <p> use switch condition when url cant be define with if condition
		 * <p> make sure both environment are match
		 */
	@Given("user navigates to {string}")
    public void user_navigates_to(String application) throws InterruptedException {
        selenium.navigateToPage(application);

	}
}
