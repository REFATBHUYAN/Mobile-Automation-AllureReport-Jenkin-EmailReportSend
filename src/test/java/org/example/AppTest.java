package org.example;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.options.BaseOptions;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

@Listeners(io.qameta.allure.testng.AllureTestNg.class)
public class AppTest {

    private AndroidDriver driver;

    @BeforeMethod
    public void setUp() throws MalformedURLException {
        String apkPath = System.getProperty("user.dir") + "/src/test/resources/General-Store.apk";
        BaseOptions options = new BaseOptions()
                .amend("platformName", "android")
                .amend("appium:automationName", "UiAutomator2")
                .amend("appium:deviceName", "local")
                .amend("appium:app", apkPath)
                .amend("appium:appPackage", "com.androidsample.generalstore")
                .amend("appium:appActivity", "com.androidsample.generalstore.SplashActivity")
                .amend("appium:ensureWebviewsHavePages", true)
                .amend("appium:nativeWebScreenshot", true)
                .amend("appium:newCommandTimeout", 3600)
                .amend("appium:connectHardwareKeyboard", true);

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    @Description("Validating app user login")
    public void sampleTest() {
        selectCountry("Algeria");
        enterName("Refat");
        selectGender("Female");
        clickShop();
    }

    @Step("Selecting country: {0}")
    public void selectCountry(String country) {
        WebElement countryDropdown = driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/spinnerCountry"));
        countryDropdown.click();
        WebElement countryOption = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"" + country + "\")"));
        countryOption.click();
    }

    @Step("Entering user name: {0}")
    public void enterName(String name) {
        WebElement nameField = driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/nameField"));
        nameField.sendKeys(name);
    }

    @Step("Selecting gender: {0}")
    public void selectGender(String gender) {
        WebElement genderOption = driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/radio" + gender));
        genderOption.click();
    }

    @Step("Clicking on 'Let's Shop'")
    public void clickShop() {
        WebElement shopButton = driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/btnLetsShop"));
        shopButton.click();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
//            driver.quit();
            driver.removeApp("com.androidsample.generalstore");
        }

    }
}
