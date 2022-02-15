package webtest.base;


import cz.csob.oneib.common.util.TestUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webtest.exceptions.ElementNotFoundException;
import webtest.exceptions.TooManyElementsWasFoundException;
import webtest.page.common.AbstractTechnicalPage;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.jsoup.helper.Validate.fail;
import static webtest.base.DriverSettings.getDriver;
import static webtest.base.DriverSettings.getWait;
import static webtest.base.FormattedStringBuilder.formatExLn;
import static webtest.utils.WebElementUtils.*;


public abstract class AbstractElementProvider {
    private static final Logger log = LoggerFactory.getLogger(AbstractElementProvider.class);

    static final String VALUE_ATTR = "value";

    private final int implicitWaitSeconds = 30;

    /**
     * SourcePage is page used in error message for identification of place where error originated.
     */
    private AbstractTechnicalPage sourcePage;


    public boolean isOpen() {
        return true;
    }

    public AbstractElementProvider(AbstractTechnicalPage sourcePage) {
        this.sourcePage = sourcePage;
    }

    protected AbstractTechnicalPage getSourcePage() {
        return sourcePage;
    }

    private List<WebElement> findElements(By by) {
        try {
            getWait().until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (TimeoutException ignored) {
        }
        return getDriver().findElements(by);
    }

    protected WebElement evaluateSingleElementFindings(List<WebElement> elements,
                                                       ComponentType typeOfElement,
                                                       String typeOfCondition,
                                                       String conditionValue,
                                                       boolean silentIfEmpty) {
        if (elements.size() == 1) {
            return elements.get(0);
        } else if (elements.isEmpty()) {
            if (silentIfEmpty) {
                return null;
            } else {
                throw new ElementNotFoundException(Info.of(sourcePage)
                        .message("Požadovaný element [{}] nebyl nalezen na stránce.", formatElementType(typeOfElement))
                        .message("Typ podmínky/selectoru: [{}]", typeOfCondition)
                        .message("Podmínka/Selector: [{}]", conditionValue)
                        .build());
            }
        } else {
            throw new TooManyElementsWasFoundException(Info.of(sourcePage)
                    .message("Podle zadaného selectoru byl očekáván pouze 1 element [{}], ale nalezeno jich je více [{}].",
                            formatElementType(typeOfElement), elements.size())
                    .message("Typ podmínky/selectoru: [{}]", typeOfCondition)
                    .message("Podmínka/Selector: [{}]", conditionValue)
                    .message("Tip pro vývojáře: ověřte správnost selectoru.")
                    .build());
        }
    }

    protected List<WebElement> evaluateMultipleElementFindings(List<WebElement> elements,
                                                               ComponentType typeOfElement,
                                                               String typeOfCondition,
                                                               String conditionValue,
                                                               boolean silentIfEmpty) {
        if (elements.isEmpty() && !silentIfEmpty) {
            throw new ElementNotFoundException(Info.of(sourcePage)
                    .message("Požadované elementy [{}] nebyly nalezeny na stránce.", formatElementType(typeOfElement))
                    .message("Typ podmínky/selectoru: [{}]", typeOfCondition)
                    .message("Podmínka/Selector: [{}]", conditionValue)
                    .build());
        }
        return elements;
    }

    protected ElementNotFoundException buildNoneElementWasFoundButOneOrMoreIsExpectedError(ComponentType typeOfElements,
                                                                                           String condition,
                                                                                           Object conditionValue) {
        return new ElementNotFoundException(Info.of(sourcePage)
                .message("Jeden nebo více elementů je očekáváno, ale žádný nebyl nalezen.")
                .message("Typ hledaného elementu: [{}]", typeOfElements.getUserFriendlyName())
                .message("{}: [{}]", condition, conditionValue)
                .build());
    }

    protected String formatElementType(ComponentType typeOfElement) {
        return typeOfElement == null ? "NOT SPECIFIED" : typeOfElement.getUserFriendlyName();
    }

    protected WebElement getParentElement(WebElement childElement) {
        return childElement.findElement(By.xpath("./.."));
    }

    protected String getPageInfoSilently(AbstractTechnicalPage sourcePage) {
        FormattedStringBuilder msg = formatExLn("Stránka: [%s]", sourcePage.getClass().getSimpleName());
        // erorx
        return msg.toString();
    }

    protected String getElementInfoSilently(WebElement element) {
        return formatExLn("")
                .addLn("Viditelný text elementu [{}]", getElementVisibleTextSilently(element))
                .addLn("Tag (typ) elementu [{}]", getElementTagSilently(element)).toString();
    }

    @SuppressWarnings("squid:S1141")
    protected void internalPerformClick(WebElement element) {
        Throwable originalException = null;
        // by our experience this is necessary to make clickable element on VDE and HW PC
        TestUtils.sleep(300);
        try {
            try {
                element.click();
            } catch (Exception ex1) {
                originalException = ex1;
                log.debug("First attempt of element.click() FAILED. We will try second attempt. {}", ex1);
                // if first attempt failed we try second attempt with some adjustments
                log.debug("    (1 of 3) moving to element...");
                moveToElement(element);
                Duration duration = Duration.ofSeconds(60);
                log.debug("    (2 of 3) wait until elementToBeClickable... duration [{}]", duration);
                getWait()
                        .until(ExpectedConditions.elementToBeClickable(element));
                log.debug("    (3 of 3) element.click...");
                element.click();
            }
        } catch (TimeoutException ex) {
            throw new IllegalStateException(formatExLn("Click on element failed because of timeout.")
                    .addLn(getElementInfoSilently(element))
                    .addLn(getPageInfoSilently(getSourcePage())).toString(), ex);
        } catch (WebDriverException ex) {
            // we will try one more click()
            TestUtils.sleep(5000);
            try {
                getWait().until(ExpectedConditions.elementToBeClickable(element));
                element.click();
            } catch (Exception ignored) {
                throw new IllegalStateException(formatExLn("Click on element failed. See inner exception for more details.")
                        .addLn(getElementInfoSilently(element))
                        .addLn(getPageInfoSilently(sourcePage)).toString(), originalException);
            }
        }
    }

    protected void moveToElement(WebElement element) {
        DriverSettings.getActions().moveToElement(element).build().perform();
    }

    protected WebElement internalFindElement(ComponentType typeOfElement, By by, boolean silent) {
        waitUntilPageIsLoaded();
        List<WebElement> candidates = findElements(by);
        return evaluateSingleElementFindings(candidates, typeOfElement, "By", by.toString(), silent);
    }

    protected WebElement internalFindElement(ElementDef def, boolean silent) {
        waitUntilPageIsLoaded();
        List<WebElement> candidates = getDriver().findElements(def.getSelector());
        return evaluateSingleElementFindings(candidates, def.getComponentType(), "By", def.getSelector().toString(), silent);
    }

    protected List<WebElement> internalFindElements(ComponentType typeOfElement, String typeOfCondition, By by, boolean silentIfEmpty) {
        waitUntilPageIsLoaded();
        List<WebElement> candidates = getDriver().findElements(by);
        return evaluateMultipleElementFindings(candidates, typeOfElement, typeOfCondition, by.toString(), silentIfEmpty);
    }

    protected List<WebElement> internalFindElements(ElementDef def, boolean silentIfEmpty) {
        waitUntilPageIsLoaded();
        List<WebElement> candidates = findElements(def.getSelector());
        return evaluateMultipleElementFindings(candidates, def.getComponentType(), "Def", def.getSelector().toString(), silentIfEmpty);
    }

    protected WebElement internalFindById(ComponentType elementType, String id, boolean silent) {
        waitUntilPageIsLoaded();
        List<WebElement> candidates = getDriver().findElements(By.id(id));
        return evaluateSingleElementFindings(candidates, elementType, "Id", id, silent);
    }

    protected WebElement internalFindByClassName(ComponentType typeOfElement, String className) {
        return internalFindByClassName(typeOfElement, className, false);
    }

    protected WebElement internalFindByClassName(ComponentType typeOfElement, String cssClass, boolean silent) {
        return internalFindElement(typeOfElement, By.className(cssClass), silent);
    }

    /**
     * Nalezne prvni element podle id.
     * Pokud zadny nebyl nalezen, vyhodi vyjimku.
     */
    protected WebElement internalFindFirstById(ComponentType typeOfElement, String id, boolean silentIfEmpty) {
        return internalFindElements(typeOfElement, "Id", By.id(id), silentIfEmpty).get(0);
    }

    /**
     * Nalezne prvni element podle id.
     * Pokud zadny nebyl nalezen, vyhodi vyjimku.
     */
    protected WebElement internalFindFirstById(ComponentType typeOfElement, String id) {
        return internalFindFirstById(typeOfElement, id, false);
    }

    /**
     * Nalezne prvni element podle className.
     * Pokud zadny nebyl nalezen, vyhodi vyjimku.
     */
    protected WebElement internalFindFirstByClassName(ComponentType typeOfElement, String className) {
        return internalFindElements(typeOfElement, "CSS class", By.className(className), false).get(0);
    }

    /**
     * Nalezne prvni element podle xpath.
     * Pokud zadny nebyl nalezen, vyhodi vyjimku.
     */
    protected WebElement internalFindFirstByXpath(ComponentType typeOfElement, String xpath) {
        return internalFindElements(typeOfElement, "by xpath", By.xpath(xpath), false).get(0);
    }

    protected String getValueFromAttr(ComponentType typeOfElement, String id) {
        return internalFindFirstById(typeOfElement, id, false).getAttribute("value");
    }

    protected boolean isDisplayedExactlyOneById(ComponentType typeOfElement, String id) {
        return internalFindById(typeOfElement, id, true) != null;
    }

    protected boolean isDisplayedByClassName(ComponentType typeOfElement, String className) {
        return internalFindByClassName(typeOfElement, className, true) != null;
    }

    protected void internalSetValue(String elementId, String value, boolean checkIfIsSet) {
        WebElement candidate = internalFindById(ComponentType.TEXT_INPUT, elementId, false);
        internalSetValue(candidate, value, true, checkIfIsSet);
    }

    protected void internalSetValue(ElementDef def, String value, boolean checkIfIsSet) {
        WebElement candidate = internalFindElement(def, false);
        internalSetValue(candidate, value, true, checkIfIsSet);
    }

    @SuppressWarnings("squid:S135" /* cause: 'Loops should not contain more than a single "break" or "continue" statement" reason: we need more breaks here */)
    protected void internalSetValue(WebElement element, @Nonnull String value, boolean sendTab, boolean checkIfIsSet) {
        log.debug("      set value [{}] to element [{}]", value, getElementIdentificationSilently(element));
        moveToElement(element);
        int attempt = 0;
        while (attempt < 3) {
            if (!element.getAttribute(VALUE_ATTR).equals(value)) {
                element.clear();
                TestUtils.sleep(300);
                if (isNotEmpty(value)) {
                    element.sendKeys(value);
                    TestUtils.sleep(100);
                }
            } else {
                break;
            }
            if (!checkIfIsSet) {
                log.debug("        checking value after set is [DISABLED].");
                break;
            }
            attempt++;
        }
        logAfterValue(element, value);
        if (attempt == 3) {
            fail(Info.of(getSourcePage())
                    .message("Nepodařilo se zapsat hodnotu do elementu.")
                    .message("Vstupní hodnota: [{}].", value)
                    .message("Aktuální hodnota 'value' v elementu: [{}].", element.getAttribute(VALUE_ATTR))
                    .message("Aktuální hodnota 'text' v elementu: [{}].", element.getText())
                    .build());
        }
        if (sendTab) {
            element.sendKeys(Keys.TAB);
        }
    }

    private void logAfterValue(WebElement element, String value) {
        String actualValue;
        try {
            actualValue = element.getAttribute(VALUE_ATTR);
        } catch (Exception ex) {
            actualValue = format("ERROR: CAN NOT READ VALUE: %s - %s", ex.getClass().getSimpleName(), ex.getMessage());
        }
        if (StringUtils.equals(value, actualValue)) {
            log.debug("        Value in element after set is [{}]. It is equal with requested value.", actualValue);
        } else {
            log.error("        Value in element after set is [{}]. It is NOT equal with requested value [{}].", actualValue, value);
        }
    }

    @SuppressWarnings("squid:S1192" /* because of better code readability */)
    protected void validateIsDisplayedExactlyOne(ComponentType typeOfElement, String userFriendlyName, By selector, String additionMessage) {
        long candidateCount = findElements(selector)
                .stream()
                .filter(WebElement::isDisplayed)
                .count();
        if (candidateCount == 0) {
            Info errorMessage = Info.of(getSourcePage())
                    .message("Jeden nebo více elementů je očekáváno, ale žádný nebyl nalezen.")
                    .message("Uživatelské jméno elementu: [{}]", userFriendlyName)
                    .message("Typ hledaného elementu: [{}]", typeOfElement)
                    .message("Element selector: [{}]", selector);
            if (isNotBlank(additionMessage)) {
                errorMessage.message("Doplňující message: [{}]", additionMessage);
            }
            throw new ElementNotFoundException(errorMessage.build());
        } else if (candidateCount > 1) {
            Info errorMessage = Info.of(getSourcePage())
                    .message("Podle zadaného selectoru byl očekáván pouze 1 element [{}], ale nalezeno jich je více [{}].", userFriendlyName, candidateCount)
                    .message("Typ hledaného elementu: [{}]", typeOfElement)
                    .message("Element selector: [{}]", selector)
                    .message("Tip pro vývojáře: ověřte správnost selectoru.");
            if (isNotBlank(additionMessage)) {
                errorMessage.message("Doplňující message: [{}]", additionMessage);
            }
            throw new TooManyElementsWasFoundException(errorMessage.build());
        }
        // else: exactly 1 element was found. That's success.
    }

    protected void validateIsNotDisplayed(ElementDef def, String additionMessage) {
        long candidateCount = getDriver().findElements(def.getSelector())
                .stream()
                .filter(WebElement::isDisplayed)
                .count();
        if (candidateCount > 0) {
            Info errorMessage = Info.of(getSourcePage())
                    .message("Podle zadaného selectoru nebyl očekáván žádný element [{}], ale nalezeno jich je více [{}].", def.getUserFriendlyName(), candidateCount)
                    .message("Typ hledaného elementu: [{}]", def.getComponentType())
                    .message("Element selector: [{}]", def.getSelector())
                    .message("Tip pro vývojáře: ověřte správnost selectoru.");
            if (isNotBlank(additionMessage)) {
                errorMessage.message("Doplňující message: [{}]", additionMessage);
            }
            throw new TooManyElementsWasFoundException(errorMessage.build());
        }
        // else: exactly 1 element was found. That's success.
    }

    /**
     * waits until page is loaded completely or until timeout
     */
    protected void waitUntilPageIsLoaded() {
        ExpectedCondition<Boolean> isPageLoadedCondition = webDriver ->
                // this will tel if page is loaded
                "complete".equals(((JavascriptExecutor) webDriver).executeScript("return document.readyState"));
        WebDriverWait wait = new WebDriverWait(getDriver(), implicitWaitSeconds);
        // wait for page complete
        wait.until(isPageLoadedCondition);
    }

    @Nonnull
    public void validateElementsAreDisplayed(ComponentType componentType, String... ids) {
        List<String> fails = new ArrayList<>();
        for (String id : ids) {
            long candidateCount = getDriver()
                    .findElements(By.id(id)).stream()
                    .filter(WebElement::isDisplayed)
                    .count();

            if (candidateCount == 0) {
                fails.add(Info.of(getSourcePage())
                        .message("Jeden nebo více elementů typu je očekáváno, ale žádný nebyl nalezen.")
                        .message("Element id: [{}]", id).build());
            } else if (candidateCount > 1) {
                Info errorMessage = Info.of(getSourcePage())
                        .message("Podle zadaného selectoru byl očekáván pouze 1 element, ale nalezeno jich je více [{}].", candidateCount)
                        .message("Element id: [{}]", id)
                        .message("Tip pro vývojáře: ověřte správnost selectoru.");
                throw new TooManyElementsWasFoundException(errorMessage.build());
            }
        }
        if (!fails.isEmpty()) {
            throw new ElementNotVisibleException(
                    format("Elementy typu [%s] nebyly nalezeny \n", componentType.getUserFriendlyName()) + String.join(System.lineSeparator(), fails));
        }
    }


    public void validateElementsAreDisplayed(ElementDef... defs) {
        List<String> fails = new ArrayList<>();
        for (ElementDef def : defs) {
            long candidateCount =
                    internalFindElements(def, true).stream()
                            .filter(WebElement::isDisplayed)
                            .count();

            if (candidateCount == 0) {
                fails.add(Info.of(getSourcePage())
                        .message("Jeden nebo více elementů typu je očekáváno, ale žádný nebyl nalezen.")
                        .element(def).build());
            } else if (candidateCount > 1) {
                Info errorMessage = Info.of(getSourcePage())
                        .message("Podle zadaného selectoru byl očekáván pouze 1 element, ale nalezeno jich je více [{}].", candidateCount)
                        .element(def);
                throw new TooManyElementsWasFoundException(errorMessage.build());
            }
        }
        if (!fails.isEmpty()) {
            throw new ElementNotVisibleException(
                    format("Tyto elementy nebyly nalezeny: \n") + String.join(System.lineSeparator(), fails));
        }
    }

    @Nonnull
    public void validateElementsAreDisabled(ComponentType componentType, String... ids) {
        List<String> fails = new ArrayList<>();
        for (String id : ids) {
            if (internalFindById(componentType, id, false).isEnabled()) {
                fails.add("ID: " + id);
            }
        }
        if (!fails.isEmpty()) {
            throw new IllegalStateException(Info.of(getSourcePage())
                    .message("Bylo očekáváno, že všechny elementy jsou 'disabled', ale některé nejsou:'")
                    .message(String.join(System.lineSeparator(), fails)).build());
        }
    }
}
