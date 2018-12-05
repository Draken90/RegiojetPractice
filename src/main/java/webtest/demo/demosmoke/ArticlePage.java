package webtest.demo.demosmoke;

import org.openqa.selenium.WebElement;
import webtest.base.AbstractDemoPage;

import java.util.List;

import static java.lang.String.format;

public class ArticlePage extends AbstractDemoPage {

    private static final String ARTICLE_AUTHOR_CLASSNAME = "entry-author";
    private static final String ARTICLE_TEXT_XPATH = "//div[@class='entry-content clearfix']/p";

    @Override
    protected void addPageIsNotOpenReasons(List<String> reasons) {
        if (!isElementPresentByClassName(ARTICLE_AUTHOR_CLASSNAME)) {
            reasons.add(format(CLASSNAME_ELEMENT_IS_NOT_PRESENT, ARTICLE_AUTHOR_CLASSNAME));
        }
    }

    public String getTextFromArticle() {
        return getArticleTextElement().getText();
    }

    private WebElement getArticleTextElement() {
        return findElementByXpath(ARTICLE_TEXT_XPATH);
    }
}

