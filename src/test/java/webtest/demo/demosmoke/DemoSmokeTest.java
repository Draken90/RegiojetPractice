package webtest.demo.demosmoke;


import org.testng.annotations.Test;
import webtest.base.AbstractDemoTest;
import webtest.demo.demosmoke.data.NewPageTestData;
import webtest.demo.demosmoke.data.NewPageTestDataProvider;
import webtest.demo.demosmoke.step.AdministrationTestStep;
import webtest.demo.demosmoke.step.HomeTestStep;


public class DemoSmokeTest extends AbstractDemoTest {

    private static final String FAILING_PAGE_TITLE = "Nesprávný text (failing test)";
    private static final String EXPECTED_TEST_PAGE_TEXT_FAIL = "Zkontrolujte tento text.";

    @Test
    public void loginToDemoPage() {
        login();
        logout();
    }

    @Test(dependsOnMethods = "loginToDemoPage")
    public void openAndCheckArticleText() {
        login();
        new HomeTestStep().clickToCommentsAndCheckText();
        logout();
    }

    @Test(dependsOnMethods = "loginToDemoPage")
    public void checkTestPageTextFail() {
        login();
        new HomeTestStep().checkPage(FAILING_PAGE_TITLE, EXPECTED_TEST_PAGE_TEXT_FAIL);
        logout();

    }


    @Test(dataProviderClass = NewPageTestDataProvider.class, dataProvider = NewPageTestDataProvider.SMOKE_TEST_DATA, dependsOnMethods = "loginToDemoPage")
    public void createAndCheckNewPage(NewPageTestData data) {
        login();
        AdministrationTestStep step = new AdministrationTestStep();
        step.clickOnNewPage();
        step.fillPageTitle(data.getHeaderText());
        step.fillPageText(data.getBodyText());
        step.savePage();
        new HomeTestStep().checkPage(data.getHeaderText(), data.getBodyText());
        logout();
    }

    @Test(dataProviderClass = NewPageTestDataProvider.class, dataProvider = NewPageTestDataProvider.SMOKE_TEST_DATA, dependsOnMethods = {"createAndCheckNewPage", "loginToDemoPage"})
    public void deletePage(NewPageTestData data) {
        login();
        AdministrationTestStep step = new AdministrationTestStep();
        step.clickToPageSummary();
        step.checkAndDeletePage(data.getHeaderText());
        new HomeTestStep().checkIfTheresNoSpeciciedPageTitle(data.getHeaderText());
        logout();
    }

}