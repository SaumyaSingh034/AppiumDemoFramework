package stepDefinitions;

import base.DriverManagerEmulator;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import io.cucumber.java.en.Given;
import pages.ControlsPage;
import pages.HomePage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class ControlStepDef {
    private AndroidDriver driver;
    private HomePage homePage;
    private ControlsPage controlsPage;

    @Given("Launch the app")
    public void launch_the_app() throws IOException, URISyntaxException, InterruptedException {

        driver = DriverManagerEmulator.initializeDriver();
        homePage = new HomePage(driver);
        controlsPage = new ControlsPage(driver);
    }

    @When("Navigate to Views and Controls")
    public void navigate_to_views_and_controls() {
        homePage.clickViews();
        controlsPage.clickControls();
        controlsPage.clickLightTheme();
    }

    @Then("Validate that text box is visible")
    public void validate_text_box_visible() {
        Assert.assertTrue(controlsPage.isTextBoxVisible());
    }

    @Then("Validate that checkbox is clickable")
    public void validate_checkbox_clickable() {
        Assert.assertTrue(controlsPage.isCheckBoxClickable());
    }

    @Then("Validate that radio button is clickable")
    public void validate_radio_button_clickable() {
        Assert.assertTrue(controlsPage.isRadioButtonClickable());
    }

    @When("Enter text {string} in text box")
    public void enter_text_in_text_box(String text) {
        controlsPage.enterText(text);
    }

    @When("Toggle the checkbox")
    public void toggle_checkbox() {
        controlsPage.toggleCheckbox();
    }

    @When("Select the radio button")
    public void select_radio_button() {
        controlsPage.selectRadioButton();
    }

    @Then("Verify checkbox is selected")
    public void verify_checkbox_selected() {
        Assert.assertTrue(controlsPage.isCheckBoxSelected());
    }

    @Then("Verify radio button is selected")
    public void verify_radio_selected() {
        Assert.assertTrue(controlsPage.isRadioButtonSelected());
    }

    @And("close the app and shutdown the mobile")
    public void closeTheAppAndShutdownTheMobile() {
        DriverManagerEmulator.quitDriver();
    }
}


