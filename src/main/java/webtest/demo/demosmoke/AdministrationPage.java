package webtest.demo.demosmoke;

import org.openqa.selenium.WebElement;
import webtest.base.AbstractDemoPage;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class AdministrationPage extends AbstractDemoPage {

    private static final String NEW_PAGE_XPATH = "menu-pages";
    private static final String CREATE_NEW_PAGE_BUTTON_CLASS = "page-title-action";
    private static final String TITLES_CLASSNAME = "row-title";
    private static final String SPEC_TITLE_XPATH = "//a[text()='%s']";

    @Override
    protected void addPageIsNotOpenReasons(List<String> reasons) {
        if (!isElementPresentById(NEW_PAGE_XPATH)) {
            reasons.add(format(CLASSNAME_ELEMENT_IS_NOT_PRESENT, NEW_PAGE_XPATH));
        }
    }

    public CreateNewPagePage newPage() {
        return new CreateNewPagePage();
    }

    public List<String> getTitlesList() {
        return getPagesTitleElement().stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public void clickOnSpecifiedPage(String pageTitle) {
        performClick(getSpecifiedPageButton(pageTitle));
    }

    public void clickOnCreateNewPageButton() {
    performClick(getCreateNewPageButton());
    }

    public void clickOnNewPageButton() {
        performClick(getNewPageButtonElement());
    }
    private WebElement getNewPageButtonElement() {return findElementById(NEW_PAGE_XPATH);}


    private WebElement getCreateNewPageButton() {
        return findElementByClassName(CREATE_NEW_PAGE_BUTTON_CLASS);
    }

    private List<WebElement> getPagesTitleElement() {
        return findElementsByClassname(TITLES_CLASSNAME);
    }

    private WebElement getSpecifiedPageButton(String pageTitle){
        return findElementByXpath(format(SPEC_TITLE_XPATH,pageTitle));
    }
}
