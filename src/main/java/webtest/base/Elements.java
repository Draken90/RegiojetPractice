package webtest.base;

import com.google.errorprone.annotations.DoNotCall;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import webtest.exceptions.ElementNotFoundException;
import webtest.exceptions.TooManyElementsWasFoundException;
import webtest.page.AbstractTechnicalPage;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static webtest.base.DriverSettings.getDriver;
import static webtest.utils.WebElementUtils.getElementTagSilently;


public class Elements extends AbstractElementProvider {

    public Elements(AbstractTechnicalPage sourcePage) {
        super(sourcePage);
    }

    @Override
    public boolean isOpen() {
        // do not call method on this class. Is not a reason for that.
        return false;
    }

    public WebElement findSubElement(WebElement parent, By by) {
        if (parent == null) {
            throw new IllegalStateException(format("Can not search for sub element with selector [%s] because input param 'parent' is null.", by));
        }
        List<WebElement> candidates = parent.findElements(by);
        if (candidates.size() == 1) {
            return candidates.get(0);
        } else if (candidates.size() > 1) {
            throw new TooManyElementsWasFoundException(Info.of(getSourcePage())
                    .message("Podle zadaného selectoru byl očekáván pouze 1 sub-element na poskytnutém parentovi [{}], ", getElementTagSilently(parent))
                    .messageSameLine("ale nalezeno jich je více [{}].", candidates.size())
                    .message("Podmínka/Selector: [{}]", by)
                    .message("Tip pro vývojáře: ověřte správnost selectoru.")
                    .build());
        } else {
            throw new ElementNotFoundException(Info.of(getSourcePage())
                    .message("Sub-element nebyl nalezen na poskytnutém parentovi.")
                    .message("Podmínka/Selector: [{}]", by)
                    .build());
        }
    }

    public WebElement findElementById(ComponentType elementType, String id) {
        return internalFindById(elementType, id, false);
    }

    public WebElement findElementById(ComponentType elementType, String id, boolean silent) {
        return internalFindById(elementType, id, silent);
    }

    /**
     * waits until page is loaded completely or until timeout
     */
    @Override
    public void waitUntilPageIsLoaded() {
        super.waitUntilPageIsLoaded();
    }

    public WebElement findElement(ComponentType typeOfElement, By by, boolean silent) {
        return internalFindElement(typeOfElement, by, silent);
    }

    /**
     * Direct call of this method is not allowed.
     * Use {@link #findElement(ComponentType, By, boolean)}
     */
    @SuppressWarnings("squid:S1133")
    @DoNotCall
    @NotNull
    public WebElement findElement(@NotNull By by) {
        throw new IllegalStateException("Use different method on this class. Direct call of this method is not allowed.");
    }

    /**
     * Direct call of this method is not allowed.
     * Use {@link #findElements(ComponentType, By, boolean))
     */
    @SuppressWarnings("squid:S1133")
    @DoNotCall
    @NotNull
    public List<WebElement> findElements(@NotNull By by) {
        throw new IllegalStateException("Use different method on this class. Direct call of this method is not allowed.");
    }

    public List<WebElement> findElements(ComponentType typeOfElements, By by, boolean silentIfEmpty) {
        waitUntilPageIsLoaded();
        List<WebElement> candidates = getDriver().findElements(by);
        if (candidates.isEmpty() && !silentIfEmpty) {
            throw buildNoneElementWasFoundButOneOrMoreIsExpectedError(typeOfElements, "By", by);
        }
        return candidates;
    }

    /**
     * !!! DON'T USE THIS METHOD IF specific findXXX method exists !!!
     * <p>
     * Search for one or more elements of specific type by XPath.
     */
    public List<WebElement> findElementsByXPath(ComponentType typeOfElements, String xpath) {
        return findElementsByXPath(typeOfElements, xpath, false);
    }

    /**
     * !!! DON'T USE THIS METHOD  findXXX method exists !!!
     * <p>IF specific
     * Search for one or more elements of specific type by XPath.
     *
     * @param silentIfEmpty If none element was found and silentIfEmpty is true the method returns empty list but if silentIfEmpty is false the exception is thrown.
     */
    @SuppressWarnings("WeakerAccess")
    public List<WebElement> findElementsByXPath(ComponentType typeOfElements, String xpath, boolean silentIfEmpty) {
        List<WebElement> candidates = getDriver().findElements(By.xpath(xpath));
        if (candidates.isEmpty() && !silentIfEmpty) {
            throw buildNoneElementWasFoundButOneOrMoreIsExpectedError(typeOfElements, "XPath", xpath);
        }
        return candidates;
    }

    @SuppressWarnings("WeakerAccess")
    public List<WebElement> findElementsById(ComponentType typeOfElements, String id, boolean silentIfEmpty) {
        List<WebElement> candidates = getDriver().findElements(By.id(id));
        if (candidates.isEmpty() && !silentIfEmpty) {
            throw buildNoneElementWasFoundButOneOrMoreIsExpectedError(typeOfElements, "ID", id);
        }
        return candidates;
    }

    public List<WebElement> findElementsById(ComponentType typeOfElements, String id) {
        return findElementsById(typeOfElements, id, false);
    }

    @SuppressWarnings("WeakerAccess")
    public List<WebElement> findElementsByClassName(ComponentType typeOfElements, String cssClass, boolean silentIfEmpty) {
        List<WebElement> candidates = getDriver().findElements(By.className(cssClass));
        if (candidates.isEmpty() && !silentIfEmpty) {
            throw buildNoneElementWasFoundButOneOrMoreIsExpectedError(typeOfElements, "CSS class", cssClass);
        }
        return candidates;
    }

    public List<WebElement> findElementsByClassName(ComponentType typeOfElements, String cssClass) {
        return findElementsByClassName(typeOfElements, cssClass, false);
    }

    /**
     * !!! DON'T USE THIS METHOD IF specific findXXX method exists !!!
     * <p>
     * Search for an element by XPath.
     *
     * @param silent If element not exists and silent is true the method returns null. If silent is false the exception is thrown.
     */
    public WebElement findElementByXPath(ComponentType typeOfElement, String xpath, boolean silent) {
        return findElement(typeOfElement, By.xpath(xpath), silent);
    }

    public WebElement findElementByAttr(@Nonnull ComponentType typeOfElement, @Nonnull String attrName, @Nonnull String attrValue) {
        return findElementByAttr(typeOfElement, attrName, attrValue, false);
    }

    public WebElement findElementByAttr(@Nonnull ComponentType typeOfElement, @Nonnull String attrName, @Nonnull String attrValue, boolean silent) {
        return findElement(typeOfElement, By.xpath(format("//*[@%s='%s']", attrName, attrValue)), silent);
    }

    public WebElement findElementByCssSelector(ComponentType typeOfElement, String cssSelector, boolean silent) {
        return findElement(typeOfElement, By.cssSelector(cssSelector), silent);
    }

    public WebElement findElementByXPath(ComponentType typeOfElement, String xpath) {
        return findElementByXPath(typeOfElement, xpath, false);
    }

    public WebElement findElementByClassName(ComponentType typeOfElement, String cssClass, boolean silent) {
        return findElement(typeOfElement, By.className(cssClass), silent);
    }

    public WebElement findElementByClassName(ComponentType typeOfElement, String className) {
        return findElementByClassName(typeOfElement, className, false);
    }

    /**
     * Nalezne prvni element podle Id. Pokud zadny nebyl nalezen, vyhodi vyjimku.
     */
    public WebElement findFirstById(ComponentType typeOfElement, String id) {
        return super.internalFindFirstById(typeOfElement, id);
    }

    /**
     * Nalezne prvni element podle Class name. Pokud zadny nebyl nalezen, vyhodi vyjimku.
     */
    public WebElement findFirstByClassName(ComponentType typeOfElement, String id) {
        return super.internalFindFirstById(typeOfElement, id);
    }

    /**
     * Nalezne prvni element podle xpath. Pokud zadny nebyl nalezen, vyhodi vyjimku.
     */
    public WebElement findFirstByXpath(ComponentType typeOfElement, String xpath) {
        return super.internalFindFirstByXpath(typeOfElement, xpath);
    }

    public void setValue(String elementId, String value) {
        internalSetValue(elementId, value, true);
    }

    public void setValue(WebElement element, String value) {
        internalSetValue(element, value, true, true);
    }

    public void setValueWithoutTab(WebElement element, String value) {
        internalSetValue(element, value, false, true);
    }

    public void setValueWithoutValidation(WebElement element, String value) {
        internalSetValue(element, value, false, false);
    }

    /**
     * Klasicka getText() neumi vzit text z nekterych elementu
     */
    public String getValueFromValueAttr(WebElement element) {
        return element.getAttribute(VALUE_ATTR);
    }

    /**
     * Use this if u need to ensure only 1 element on page.
     *
     * @see #isDisplayedAtLeastOneByClassName(ComponentType, String)
     */
    public boolean isDisplayedExactlyOneByClassName(ComponentType typeOfElement, String className) {
        return super.internalFindByClassName(typeOfElement, className, true) != null;
    }

    /**
     * Use this when is safe to have more than one element on page.
     *
     * @see #isDisplayedExactlyOneByClassName(ComponentType, String)
     */
    public boolean isDisplayedAtLeastOneByClassName(ComponentType typeOfElement, String className) {
        return super.internalFindByClassName(typeOfElement, className, true) != null;
    }

    public boolean isDisplayedByXpath(String xpath) {
        return isDisplayed(By.xpath(xpath));
    }

    public boolean isDisplayedById(String id) {
        return isDisplayed(By.id(id));
    }

    /**
     * use isDisplayedAtLeastOneByClassName or isDisplayedExactlyOneByClassName
     * <p>
     * Pokud potrebujes por ID nebo neco jineho, dodelej podle predlohy
     */
    @Deprecated
    public boolean isDisplayed(By selector) {
        return getDriver()
                .findElements(selector).stream()
                .anyMatch(WebElement::isDisplayed);
    }

    @SuppressWarnings("squid:S1192" /* because of better code readability */)
    public void validateIsDisplayedOneOrMore(ComponentType typeOfElement, String userFriendlyName, By selector) {
        if (!isDisplayed(selector)) {
            throw new ElementNotFoundException(Info.of(getSourcePage())
                    .message("Jeden nebo více elementů je očekáváno, ale žádný nebyl nalezen.")
                    .message("Uživatelské jméno elementu: [{}]", userFriendlyName)
                    .message("Typ hledaného elementu: [{}]", typeOfElement)
                    .message("Element selector: [{}]", selector)
                    .build());
        }
    }

    public void validateIsDisplayedExactlyOne(ComponentType typeOfElement, String userFriendlyName, By selector) {
        validateIsDisplayedExactlyOne(typeOfElement, userFriendlyName, selector, "");
    }

    @Override
    public void validateIsDisplayedExactlyOne(ComponentType typeOfElement, String userFriendlyName, By selector, String additionMessage) {
        super.validateIsDisplayedExactlyOne(typeOfElement, userFriendlyName, selector, additionMessage);
    }

    /**
     * Use only for page heading
     */
    public boolean isHeadingDisplayed(@Nonnull String visibleText) {
        return isDisplayed(By.xpath(format("//h2[(@id='ctl00_H2Basic')]/span[contains(text(),'%s')]", visibleText)));
    }

    public void performClick(WebElement element) {
        internalPerformClick(element);
    }

    public void performClick(ComponentType typeOfElement, By selector) {
        internalPerformClick(findElement(typeOfElement, selector, false));
    }

    public void clickOnLinkByVisibleText(@Nonnull String linkVisibleText) {
        WebElement linkElement = findElement(ComponentType.LINK, By.xpath(format("//a[text()='%s']", linkVisibleText)), false);
        internalPerformClick(linkElement);
    }

    /**
     * Search for elements by at least one of multiple condition.
     */
    @Nonnull
    public List<WebElement> findByAnyOf(ComponentType typeOfElements, Boolean silent, By... byArray) {
        if (byArray.length < 2) {
            throw new IllegalArgumentException("Use only with multiple input arguments.");
        }
        List<WebElement> candidates = new ArrayList<>();
        for (By by : byArray) {
            candidates.addAll(getDriver().findElements(by));
        }
        if (candidates.isEmpty() && !silent) {
            String byValues = Arrays.stream(byArray).map(By::toString).collect(Collectors.joining(" OR "));
            throw new ElementNotFoundException(Info.of(getSourcePage())
                    .message("Žádný element nebyl nalezen, ani když byl hledán podle více selectorů [{}].", byValues.length())
                    .message("Typ hledaných elementů: [{}]", typeOfElements)
                    .message("Element selectors: [{}]", byValues)
                    .collection(byArray)
                    .build());
        }
        return candidates;
    }
}