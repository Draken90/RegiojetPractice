package webtest.demo.demosmoke;

import org.openqa.selenium.WebElement;
import webtest.base.AbstractDemoPage;

import java.util.List;

import static java.lang.String.format;

public class HomePage extends AbstractDemoPage {

    private static final String HOMEPAGE_DIV_ID = "homepage-pane-1";
    private static final String ACTIVE_HOMEPAGE_XPATH = "//li[@class='current_page_item']/a[text()='Start']";
    private static final String ARTICLE_TEXT_XPATH = "//div[@class='post-excerpt']/p";
    private static final String READ_MORE_BUTTON_XPATH = "//p[@class='post-comments']/a";
    private static final String ADMINISTRATION_BUTTON_CLASSNAME = "ab-icon";
    private static final String EXPECTED_NEW_PAGE_ID = "//ul[@class='nav navbar-nav flip']//a[text()='%s']";
    private static final String EXPECTED_TEXT_XPATH ="//div[@class='entry-content clearfix']/p";

    @Override
    protected void addPageIsNotOpenReasons(List<String> reasons) {
        if (!isElementPresentById(HOMEPAGE_DIV_ID)) {
            reasons.add(format(ID_ELEMENT_IS_NOT_PRESENT, HOMEPAGE_DIV_ID));
        }

        if (!isElementPresentByXpath(ACTIVE_HOMEPAGE_XPATH)) {
            reasons.add(format(XPATH_ELEMENT_IS_NOT_PRESENT, ACTIVE_HOMEPAGE_XPATH));
        }
    }

    public String getCommentText() {
        return getArticleTextElement().getText();
    }

    public void clickToReadMoreButton() {
        performClick(getReadMoreButtonElement());
    }

    public void clickOnAdministrationButton() {
        performClick(getAdministrationButtonElement());
    }


    public boolean checkTitle(String expectedTitle) {
        return isElementPresentByXpath(format(EXPECTED_NEW_PAGE_ID, expectedTitle));
    }

    public void clickToPage(String expectedTitle) {
        performClick(getExpectedNewPageElement(expectedTitle));
    }

    public String getPageText() {
        return getExpectedTextElement().getText();
    }

    private WebElement getArticleTextElement() {
        return findElementByXpath(ARTICLE_TEXT_XPATH);
    }

    private WebElement getReadMoreButtonElement() {
        return findElementByXpath(READ_MORE_BUTTON_XPATH);
    }

    private WebElement getAdministrationButtonElement() {
        return findElementByClassName(ADMINISTRATION_BUTTON_CLASSNAME);
    }

    private WebElement getExpectedTextElement() {
        return findElementByXpath(EXPECTED_TEXT_XPATH);
    }

    private WebElement getExpectedNewPageElement(String exceptedTitle) {
        return findElementByXpath(format(EXPECTED_NEW_PAGE_ID, exceptedTitle));
    }
}
