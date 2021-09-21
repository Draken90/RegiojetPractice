package webtest.base;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static java.lang.String.format;
import static webtest.base.PropertiesData.getUrl;

public class DriverSettings {

    private static WebDriver driver;
    private static Logger logger = LoggerFactory.getLogger(DriverSettings.class);
    protected static final String USERNAME = "georgehope1";
    protected static final String AUTOMATE_KEY = "BzRyA435eK5NAsSfyP3T";
    protected static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";
    protected static final String COMMAND_START_LOCAL_BS = "C:\\Apps\\bslocal\\BrowserStackLocal.exe --key %s --local-identifier %s";
    protected static final long TIMETOUT = 30;
    private static Process localBrowserStack = null;
    public static WebDriver getDriver() {
        return driver;
    }

    public static void inicializeDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\Apps\\chrome\\chromedriver.exe");
        logger.info("Creating local WebDriver");
        driver = new ChromeDriver();
        driver.get(getUrl());
        driver.get(getUrl());
        driver.manage().window().maximize();
    }

    public static WebDriverWait getWait() {
        return new WebDriverWait(DriverSettings.getDriver(), TIMETOUT);
    }

    public static Actions getActions() {
        return new Actions(driver);
    }

    public static String getUSERNAME() {
        return USERNAME;
    }

    public static String getAutomateKey() {
        return AUTOMATE_KEY;
    }

    public static void destroyLocalBrowserstackProcess() {
        localBrowserStack.destroy();
    }

    public static void startLocalBrowserStack() {
        try {
            logger.info("Executing browserstacklocal.exe");
            localBrowserStack = Runtime.getRuntime().exec(format(COMMAND_START_LOCAL_BS, AUTOMATE_KEY, ConfigUtils.getPCName()));
            Thread.sleep(8_000);
            Scanner kb = new Scanner(localBrowserStack.getInputStream());
            while (kb.hasNextLine()) {
                String line = kb.nextLine();
                System.out.println(line);
                if (line.contains("Press Ctrl-C to exit")) {
                    break;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getSesionId() {
        return ((RemoteWebDriver) driver).getSessionId().toString();
    }

    public static void configureBrowserStack(String testName) {
        DesiredCapabilities caps = new DesiredCapabilities();
        int i = 0;
        caps.setCapability("browser", "Chrome");
        caps.setCapability("browser_version", "62.0");
        caps.setCapability("os", "Windows");

        caps.setCapability("os_version", "8.1");
        caps.setCapability("resolution", "1920x1080");
        caps.setCapability("name", testName);
        caps.setCapability("browserstack.local", "true");
        caps.setCapability("browserstack.localIdentifier", ConfigUtils.getPCName());
        startRemoteDriver(caps);
    }


    private static void startRemoteDriver(DesiredCapabilities caps) {
        logger.info("Starting RemoteDriver");
        try {
            driver = new RemoteWebDriver(new URL(URL), caps);
            driver.manage().window().maximize();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        driver.get(getUrl());
    }

    protected WebElement findElement(By by) {
        return getDriver().findElement(by);
    }
}
