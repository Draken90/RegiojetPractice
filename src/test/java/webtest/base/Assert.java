package webtest.base;


import static java.lang.String.format;
import static org.testng.Assert.fail;

public class Assert {

    public static void assertPageIsOpen(AbstractDemoPage searchPage) {
        assertPageIsOpen(searchPage, " ");
    }

    public static void assertPageIsOpen(AbstractDemoPage page, String customMessage) {
        if (!page.isOpen()) {
            String notOpenReasons = String.join(System.lineSeparator(), page.getPageIsNotOpenReasons());

            fail(format("Page is not open as expected."
                            + "%nCustom message: [%s]"
                            + "%nPage class: [%s]"
                            + "%nReason(s):%n %s",
                    customMessage,
                    page.getClass().getSimpleName(),
                    notOpenReasons));
        }
    }

}
