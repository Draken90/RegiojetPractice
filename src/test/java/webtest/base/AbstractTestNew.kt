package webtest.base

import org.apache.commons.io.FileUtils
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.testng.ITestResult
import org.testng.annotations.*
import webtest.page.app.CookiePage
import webtest.page.app.LanguagePage
import webtest.page.app.LoginPage
import webtest.page.app.MainPage
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

abstract class AbstractTestNew {

    val mainPage = MainPage()

    @BeforeMethod
    fun initializeDriver() = DriverSettings.initializeDriver()


    @AfterMethod
    fun closeDriver(result: ITestResult) {
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

        }
        DriverSettings.getDriver().close()
        DriverSettings.getDriver().quit()
    }

    fun acceptCookies(): LanguagePage {


        CookiePage().eatCookies()

        return LanguagePage()
    }

    fun selectCzechMutation(): MainPage{
        LanguagePage().selectCzechMutation()
        return MainPage()
    }

    fun switchWindows(): CookiePage{
        CookiePage().switchWindows()
        return CookiePage()
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