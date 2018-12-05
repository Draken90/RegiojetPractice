package webtest.demo.demosmoke.step;

import org.testng.Assert;
import webtest.base.step.AbstractDemoTestStep;
import webtest.demo.demosmoke.AdministrationPage;
import webtest.demo.demosmoke.ArticlePage;
import webtest.demo.demosmoke.HomePage;

import static java.lang.String.format;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;
import static org.testng.AssertJUnit.assertEquals;
import static webtest.base.Assert.assertPageIsOpen;

public class HomeTestStep extends AbstractDemoTestStep {


    public void clickToCommentsAndCheckText() {
        HomePage page = new HomePage();
        String text = page.getCommentText();
        page.clickToReadMoreButton();
        ArticlePage articlePage = new ArticlePage();
        assertPageIsOpen(articlePage);
        String articleText = articlePage.getTextFromArticle();
        assertEquals(format("Text's from homepage and from article page are diferent \n" +
                        "Homepage: [%s] \n" +
                        "Article:  [%s]", text, articleText),
                text, articleText);
    }

    public void clickOnAdministrationButton() {
        new HomePage().clickOnAdministrationButton();
        assertPageIsOpen(new AdministrationPage());
    }

    public void checkPage(String expectedTitle, String expectedText) {
        HomePage homePage = new HomePage();
        assertTrue(homePage.checkTitle(expectedTitle), format("Page with title %s was not found on homepage", expectedTitle));
        homePage.clickToPage(expectedTitle);
        Assert.assertEquals(homePage.getPageText(),expectedText, "Text (body) of new page is diferent from");
    }

    public void checkIfTheresNoSpeciciedPageTitle(String titlePage) {
        if(new HomePage().checkTitle(titlePage)){
            fail(format("Page with title %s is still in the menu after deleting", titlePage));
        }
    }
}
