package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class HomePage {

    public AndroidDriver driver;

    public HomePage(AndroidDriver driver){
        this.driver = driver;
    }

    private By viewsOptions = AppiumBy.accessibilityId("Views");

    public void clickViews(){
        driver.findElement(viewsOptions).click();
    }
}
