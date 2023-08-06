package webtest.base

import BrowserstackREST.BrowserStackREST
import org.apache.commons.io.FileUtils
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.testng.ITestResult
import org.testng.annotations.*
import org.testng.annotations.Optional
import webtest.page.app.LoginPage
import webtest.page.app.MainPage
import java.io.File
import java.io.IOException
import java.lang.reflect.Method
import java.text.SimpleDateFormat
import java.util.*

abstract class AbstractTestNew {

    @Parameters("withBrowserstack")
    @BeforeMethod
    fun initializeDriver(@Optional("withBrowserstack") withBrowserstack: String, method: Method) {
        if (withBrowserstack == "true") {
            DriverSettings.configureBrowserStack(method.name)
        } else {
            DriverSettings.initializeDriver()
        }
    }

    @Parameters("withBrowserstack")
    @BeforeTest
    fun initializeLocalBs(@Optional("withBrowserstack") withBrowserStack: String) {
        if (withBrowserStack == "true") {
            DriverSettings.startLocalBrowserStack()
        }
    }

    @AfterMethod
    @Parameters("withBrowserstack")
    fun closeDriver(@Optional("withBrowserstack") withBrowserStack: String, result: ITestResult) {
        if (result.status == ITestResult.FAILURE) {
            val scrFile = (DriverSettings.getDriver() as TakesScreenshot).getScreenshotAs(OutputType.FILE)
            try {
                FileUtils.copyFile(
                    scrFile, File(
                        "..\\ScreenShots\\" + result.name + "-"
                                + SimpleDateFormat("ddMMHHmm").format(Date()) + ".jpg"
                    )
                )
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if (withBrowserStack == "true") {
                val rest = BrowserStackREST(DriverSettings.getUSERNAME(), DriverSettings.getAutomateKey())
                rest.markSessionAsFailed(DriverSettings.getSesionId(), result.throwable.message)
            }
        }
        DriverSettings.getDriver().close()
        DriverSettings.getDriver().quit()
    }

    @Parameters("withBrowserstack")
    @AfterTest
    fun destroyLocalBrowserStack(@Optional("withBrowserstack") withBrowserStack: String) {
        if (withBrowserStack == "true") {
            DriverSettings.destroyLocalBrowserstackProcess()
        }
    }

    /**
     * Zmínit se jak to lze napsat jinak za pouziti core selenia Webdriver, rozdil mezi fce run/let/also ...
     */
    fun login(): MainPage {
        LoginPage().run {
            fillLogin(PropertiesData.getUsername(), PropertiesData.getPassword())
            clickOnLoginButton()
        }
        return MainPage()
    }


}