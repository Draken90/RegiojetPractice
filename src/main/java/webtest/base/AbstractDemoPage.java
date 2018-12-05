package webtest.base;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


public abstract class AbstractDemoPage extends DriverSettings {

    private Logger logger = LoggerFactory.getLogger(AbstractDemoPage.class);
    protected static final String XPATH_ELEMENT_IS_NOT_PRESENT = "Expected Element xpath [%s] is not present.";
    protected static final String ID_ELEMENT_IS_NOT_PRESENT = "Expected Element id [%s] is not present.";
    protected static final String CLASSNAME_ELEMENT_IS_NOT_PRESENT = "Expected Element class name [%s] is not present.";

    protected WebElement findElementByXpath(String xpath) {
        isElementPresentByXpath(xpath);
        return findElement(By.xpath(xpath));
    }

    protected WebElement findElementById(String id) {
        isElementPresentById(id);
        return findElement(By.id(id));
    }

    protected WebElement findElementByClassName(String className) {
        isElementPresentByClassName(className);
        return findElement(By.className(className));
    }

    protected void performClick(WebElement element) {
        //highLight(element);
        scrollToElement(element);
        sleep(3_000);
        element.click();
        sleep(3_000);
    }



    public boolean isOpen() {
        return getPageIsNotOpenReasons().isEmpty();
    }

    public final List<String> getPageIsNotOpenReasons() {
        List<String> reasons = new ArrayList<>();
        addPageIsNotOpenReasons(reasons);
        return reasons;
    }

    protected abstract void addPageIsNotOpenReasons(List<String> reasons);


    public boolean isElementPresentByXpath(String xpath) {
        return isElementPresent(By.xpath(xpath));
    }

    public boolean isElementPresentById(String id) {
        return isElementPresent(By.id(id));
    }

    public boolean isElementPresentByClassName(String className) {
        return isElementPresent(By.className(className));
    }

    public void switchFrameToParent() {
        DriverSettings.getDriver().switchTo().parentFrame();
    }

    private boolean isElementPresent(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Integer.parseInt(PropertiesData.getPageLoadTimeout()));
            wait.until(driver -> findElement(by));
            highLight(findElement(by));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public List<WebElement> findElementsByClassname(String className) {
        return getDriver().findElements(By.className(className));
    }

    public List<WebElement> findElementsById(String id) {
        return getDriver().findElements(By.id(id));
    }
    public List<WebElement> findElementsByXpath(String xpath) {
        return getDriver().findElements(By.xpath(xpath));
    }
    protected void sleep(int milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void scrollToElement(WebElement element) {
        Actions a = new Actions(DriverSettings.getDriver());
        a.moveToElement(element);
        a.build().perform();
        sleep(250);

        Select v = new Select(element);
    }

    protected void setText(WebElement element, String text) {
        //highLight(element);
        element.clear();
        element.sendKeys(text);
        sleep(3_000);
    }

    public void highLight(WebElement element)
    {
        for (int i = 0; i <2; i++)
        {
                JavascriptExecutor js = (JavascriptExecutor) getDriver();
                String styleBackup = element.getAttribute("style");
                js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "color: black; outline: 4px solid red; " + styleBackup);
                sleep(500);
                js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, styleBackup);
                sleep(500);
        }

    }

}
