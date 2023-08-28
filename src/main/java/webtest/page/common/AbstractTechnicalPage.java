package webtest.page.common;

import cz.csob.oneib.common.util.TestUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webtest.base.*;
import webtest.exceptions.ServerIsDownException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;


public abstract class AbstractTechnicalPage extends DriverSettings {

    private static final Logger log = LoggerFactory.getLogger(AbstractTechnicalPage.class);
    private Elements elementsProvider;
    private final List<Info> validationFailures = new ArrayList<>();

    private String url;

    public String getCurrentUrl() {
        return DriverSettings.getDriver().getCurrentUrl();
    }

    public String getTitle() {
        return DriverSettings.getDriver().getTitle();
    }

    public abstract boolean isOpen();

    @Nonnull
    public final String buildFailedReasons(boolean withoutPageInfo) {
        return InfoKt.buildFailedReasons(validationFailures, withoutPageInfo);
    }


    public String getUrl() {
        return getDriver().getCurrentUrl();
    }

    @Nonnull
    public final List<Info> getValidationFailures() {
        return validationFailures;
    }

    protected boolean checkAll(@Nonnull final PageStateChecker... checkers) {
        validationFailures.clear();
        for (int idx = 0; idx < checkers.length; idx++) {
            PageStateChecker pageStateChecker = checkers[idx];
            log.debug("    calling pageStateChecker [{} of {}] on page [{}]",
                    idx + 1,
                    checkers.length,
                    pageStateChecker.getPage() == null ? "UNAVAILABLE" : pageStateChecker.getPage().getClass().getSimpleName());
            try {
                if (!pageStateChecker.check()) {
                    validationFailures.add(pageStateChecker.getInfo());
                }
            } catch (Exception e) {
                log.error("Unexpected exception during isOpen check. Added to 'validationFailures' list. [{}]", e);
                validationFailures.add(pageStateChecker.getInfo().exception("Unexpected exception during isOpen() checkAll(...).", e));
            }
        }
        return validationFailures.isEmpty();
    }

    public void scrollToTop() {
        JavascriptExecutor jse = (JavascriptExecutor) DriverSettings.getDriver();
        jse.executeScript("scroll(0, -250);");
    }

    @Nonnull
    public final synchronized Elements elements() {
        if (elementsProvider == null) {
            elementsProvider = new Elements(this);
        }
        return elementsProvider;
    }

    /**
     * waits until page is loaded completely or until timeout
     */
    public void waitUntilPageIsLoaded() {
        elements().waitUntilPageIsLoaded();
    }

    public void waitForPageLoad() {
        TestUtils.waitForJavaScript(500);
        getWait().until(ExpectedConditions.invisibilityOfElementLocated(By.className("_1x6-mV8g8LA2lZBrZnZBVX")));
    }

}
