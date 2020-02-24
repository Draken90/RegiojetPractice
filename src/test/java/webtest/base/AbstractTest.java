package webtest.base;

import BrowserstackREST.BrowserStackREST;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.*;
import webtest.base.step.AbstractDemoTestStep;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AbstractTest {

    @Parameters("withBrowserstack")
    @BeforeMethod
    public void inicializeDriver(@Optional("withBrowserstack") String withBrowserstack, Method method) {

        if (withBrowserstack.equals("true")) {
            DriverSettings.configureBrowserStack(method.getName());
        } else {
            DriverSettings.inicializeDriver();
        }

    }

    @Parameters("withBrowserstack")
    @BeforeTest
    public void inicializeLocalBs(@Optional("withBrowserstack") String withBrowserStack) {
        if (withBrowserStack.equals("true")) {
            DriverSettings.startLocalBrowserStack();
        }

    }

    @AfterMethod
    @Parameters("withBrowserstack")
    public void closeDriver(@Optional("withBrowserstack") String withBrowserStack, ITestResult result) {

        if (result.getStatus() == ITestResult.FAILURE) {
            File scrFile = ((TakesScreenshot) DriverSettings.getDriver()).getScreenshotAs(OutputType.FILE);
            try {
                FileUtils.copyFile(scrFile, new File("..\\ScreenShots\\" + result.getName() + "-"
                        + new SimpleDateFormat("ddMMHHmm").format(new Date()) + ".jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (withBrowserStack.equals("true")) {
                BrowserStackREST rest = new BrowserStackREST(DriverSettings.USERNAME, DriverSettings.AUTOMATE_KEY);
                rest.markSessionAsFailed(DriverSettings.getSesionId(), result.getThrowable().getMessage());
            }
        }

        DriverSettings.getDriver().close();
        DriverSettings.getDriver().quit();
    }

    @Parameters("withBrowserstack")
    @AfterTest
    public void destroyLocalBrowserStack(@Optional("withBrowserstack") String withBrowserStack) {
        if (withBrowserStack.equals("true")) {
            DriverSettings.destroyLocalBrowserstackProcess();
        }

    }

    protected void login() {
        AbstractDemoTestStep step = new AbstractDemoTestStep();
        step.login();
    }

    protected void logout() {
        AbstractDemoTestStep step = new AbstractDemoTestStep();
        step.logout();
    }

}
