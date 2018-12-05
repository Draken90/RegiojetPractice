package webtest.demo.demosmoke.data;

import org.testng.annotations.DataProvider;

public class NewPageTestDataProvider {

    public static final String SMOKE_TEST_DATA = "basicTestData";


    @DataProvider(name = SMOKE_TEST_DATA)
    static Object[][] basicTestDataProvider() {

        NewPageTestData data = createNewPageData();
        return new Object[][]{{data}};
    }

    private static NewPageTestData createNewPageData() {
        NewPageTestData data = new NewPageTestData();
        data.setHeaderText("Selenium");
        data.setBodyText("Selenium je sada nástrojů pro automatizaci testů. Testy je možno spouštět v mnoha webových prohlížečích a také na mnoha platformách. V tom spočívá jejich největší výhoda.");
        return data;
    }
}
