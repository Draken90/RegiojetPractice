package webtest.demo.demosmoke.step;

import webtest.base.step.AbstractDemoTestStep;
import webtest.demo.demosmoke.AdministrationPage;
import webtest.demo.demosmoke.HomePage;

import java.util.List;

import static java.lang.String.format;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;
import static webtest.base.Assert.assertPageIsOpen;

public class AdministrationTestStep extends AbstractDemoTestStep {

    public void clickOnNewPage() {
        new HomePage().clickOnAdministrationButton();
        AdministrationPage page = new AdministrationPage();
        page.clickOnNewPageButton();
        assertPageIsOpen(page);
        page.clickOnCreateNewPageButton();
        assertPageIsOpen(page.newPage());
    }

    public void clickToPageSummary() {
        new HomePage().clickOnAdministrationButton();
        AdministrationPage page = new AdministrationPage();
        page.clickOnNewPageButton();
        assertPageIsOpen(page);
    }

    public void checkAndDeletePage(String pageTitle) {
        boolean erased = false;
        List<String> list = new AdministrationPage().getTitlesList();
        if (list.isEmpty()) {
            fail("There're no pages in administration menu.");
        }

        for (String date : list) {
            if (date.equals(pageTitle)) {
            }
            deletePage(pageTitle);
            erased = true;
            break;
        }

        if (!erased) {
            fail(format("There's no page with name %s to delete", pageTitle));
        }
    }

    public void fillPageTitle(String text) {
        new AdministrationPage().newPage().setPageTitle(text);
    }

    public void fillPageText(String text) {
        new AdministrationPage().newPage().setPageText(text);
    }

    public void savePage() {
        AdministrationPage page = new AdministrationPage();
        page.newPage().clickOnPublishButton();
        page.newPage().clickOnBackToWebButton();
    }

    private void deletePage(String pageTitle) {
        AdministrationPage page = new AdministrationPage();
        page.clickOnSpecifiedPage(pageTitle);
        page.newPage().clickOnDeletePageButton();
        page.newPage().clickOnBackToWebButton();
    }
}
