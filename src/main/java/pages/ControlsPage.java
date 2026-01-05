package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class ControlsPage {

    public AndroidDriver driver;

    public ControlsPage(AndroidDriver driver){
        this.driver = driver;
    }

    private By controlsOption = By.xpath("//android.widget.TextView[@content-desc='Controls']");
    private By lightTheme = By.xpath("//android.widget.TextView[@content-desc='1. Light Theme']");

    private By textBox = By.id("io.appium.android.apis:id/edit");
    private By checkBox = By.id("io.appium.android.apis:id/check1");
    private By radioButton = By.id("io.appium.android.apis:id/radio1");


    public void clickControls() {
        driver.findElement(controlsOption).click();
    }

    public void clickLightTheme() {
        driver.findElement(lightTheme).click();
    }

    public boolean isTextBoxVisible() {
        return driver.findElement(textBox).isDisplayed();
    }

    public boolean isCheckBoxClickable() {
        return driver.findElement(checkBox).isEnabled();
    }

    public boolean isRadioButtonClickable() {
        return driver.findElement(radioButton).isEnabled();
    }

    public void enterText(String text) {
        driver.findElement(textBox).sendKeys(text);
    }

    public void toggleCheckbox() {
        driver.findElement(checkBox).click();
    }

    public void selectRadioButton() {
        driver.findElement(radioButton).click();
    }

    public boolean isCheckBoxSelected() {
        return driver.findElement(checkBox).getAttribute("checked").equals("true");
    }

    public boolean isRadioButtonSelected() {
        return driver.findElement(radioButton).getAttribute("checked").equals("true");
    }


}
