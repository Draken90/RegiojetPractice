package webtest.base;

import org.openqa.selenium.JavascriptExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;


public abstract class AbstractTechnicalPage extends DriverSettings {

    private static final Logger log = LoggerFactory.getLogger(AbstractTechnicalPage.class);
    private Elements elementsProvider;
    private final List<Info> validationFailures = new ArrayList<>();

    protected String getCurrentUrl() {
        return DriverSettings.getDriver().getCurrentUrl();
    }

    public String getTitle() {
        return DriverSettings.getDriver().getTitle();
    }

    /**
     * @see #getValidationFailures()
     * t)
     * <p>
     * HINT:
     * tato metoda je zde jen kvuli kompatibilite s Javorem, pokud se bude tato knihovna s JAVOREM slucovat, aby to Javor neprebil.
     * prakticky ale uzitecna moc neni, jelikoz je pouhou obalkou nad statickou funkci.
     */
    @Nonnull
    public final String buildFailedReasons(boolean withoutPageInfo) {
        return InfoKt.buildFailedReasons(validationFailures, withoutPageInfo);
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

}
