package webtest.demo.demosmoke;

import org.openqa.selenium.WebElement;
import webtest.base.AbstractDemoPage;
import webtest.base.DriverSettings;

import java.util.List;

import static java.lang.String.format;

public class CreateNewPagePage extends AbstractDemoPage {

    private static final String TITLE_TEXT_FIELD_XPATH = "//div[@id='titlewrap']/input";
    private static final String TEXT_FIELD_FRAME_ID = "content_ifr";
    private static final String BODY_TEXT_CLASSNAME = "wp-editor-area";
    private static final String HTML_MOD_ID = "content-html";
    private static final String PUBLISH_BUTTON_ID = "publish";
    private static final String BACK_TO_WEB_BUTTON_XPATH= "//li[@id='wp-admin-bar-site-name']/a";
    private static final String DELETE_PAGE_BUTTON_XPATH = "//a[@class='submitdelete deletion']";

    @Override
    protected void addPageIsNotOpenReasons(List<String> reasons) {
        if (!isElementPresentByXpath(TITLE_TEXT_FIELD_XPATH)) {
            reasons.add(format(XPATH_ELEMENT_IS_NOT_PRESENT, TITLE_TEXT_FIELD_XPATH));
        }

    }

    private void switchFrameToTextField() {
        DriverSettings.getDriver().switchTo().frame(getTextFieldFrameElement());
    }

    public void setPageTitle(String text) {
        setText(getTitleElement(), text);
    }

    public void setPageText(String text) {
        performClick(getHtmlModButtonElement());
        setText(getTextFieldBodyElement(), text);
    }

    public void clickOnPublishButton() {
        performClick(getPublishButtonElement());
    }

    public void clickOnBackToWebButton() {
        performClick(getReturnToPageButtonElement());
    }

    public void clickOnDeletePageButton() {
        performClick(getDeletePageButtonElement());
    }

    private WebElement getTitleElement() {
        return findElementByXpath(TITLE_TEXT_FIELD_XPATH);
    }

    private WebElement getTextFieldFrameElement() {
        return findElementById(TEXT_FIELD_FRAME_ID);
    }

    private WebElement getTextFieldBodyElement() {
        return findElementByClassName(BODY_TEXT_CLASSNAME);
    }

    private WebElement getHtmlModButtonElement() {
        return findElementById(HTML_MOD_ID);
    }

    private WebElement getPublishButtonElement() {
        return findElementById(PUBLISH_BUTTON_ID);
    }

    private WebElement getReturnToPageButtonElement() {
        return findElementByXpath(BACK_TO_WEB_BUTTON_XPATH);
    }

    private WebElement getDeletePageButtonElement() {return  findElementByXpath(DELETE_PAGE_BUTTON_XPATH);}
}
