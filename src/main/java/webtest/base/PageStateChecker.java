package webtest.base;

import org.apache.commons.lang3.Validate;
import org.openqa.selenium.By;
import webtest.page.AbstractTechnicalPage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/**
 * PageStateChecker is supposed to be used in conjunction with method {@link AbstractTechnicalPage#checkAll(PageStateChecker...)}
 */
public final class PageStateChecker {
    private BooleanSupplier checker;
    private Supplier<Info> builder;
    private AbstractTechnicalPage page;

    private PageStateChecker(@Nonnull final BooleanSupplier checker) {
        this.checker = Validate.notNull(checker);
    }

    /**
     * Creates a new instance of {@link PageStateChecker} and adds a {@link BooleanSupplier} - the actual condition for a check
     */
    @Nonnull
    public static PageStateChecker that(@Nonnull final BooleanSupplier checker) {
        return new PageStateChecker(Validate.notNull(checker));
    }

    @Nonnull
    public static PageStateChecker thatIsElementDisplayed(@Nonnull final AbstractTechnicalPage page, @Nonnull By by) {
        PageStateChecker checker = that(() -> page.elements().isDisplayed(by));
        checker.page = page;
        checker.orInfo(() -> Info.of(page)
                .message("Expected element is not displayed at page.")
                .message("Selector: [{}]", by));
        return checker;
    }

    @Nonnull
    public static PageStateChecker thatIsElementDisplayed(
            @Nonnull final AbstractTechnicalPage page,
            @Nonnull final ComponentType typeOfElement,
            @Nonnull final By by) {
        PageStateChecker checker = that(() -> page.elements().isDisplayed(by));
        checker.page = page;
        checker.orInfo(() -> Info.of(page)
                .message("Expected element of type [{}] is not displayed at page.", typeOfElement.getUserFriendlyName())
                .message("Selector: [{}]", by));
        return checker;
    }

    @Nonnull
    public static PageStateChecker thatIsElementDisplayedById(
            @Nonnull final AbstractTechnicalPage page,
            @Nonnull final ComponentType typeOfElement,
            @Nonnull final String id) {
        PageStateChecker checker = that(() -> page.elements().isDisplayed(By.id(id)));
        checker.page = page;
        checker.orInfo(() -> Info.of(page)
                .message("Expected element of type [{}] is not displayed at page.", typeOfElement.getUserFriendlyName())
                .message("Element ID: [{}]", id));
        return checker;
    }

    @Nonnull
    public static PageStateChecker thatIsElementDisplayedByClass(
            @Nonnull final AbstractTechnicalPage page,
            @Nonnull final ComponentType typeOfElement,
            @Nonnull final String cssClass) {
        PageStateChecker checker = that(() -> page.elements().isDisplayed(By.className(cssClass)));
        checker.page = page;
        checker.orInfo(() -> Info.of(page)
                .message("Expected element of type [{}] is not displayed at page.", typeOfElement.getUserFriendlyName())
                .message("Element css class: [{}]", cssClass));
        return checker;
    }

    @Nonnull
    public static PageStateChecker thatIsHeadingDisplayedByVisibleText(@Nonnull final AbstractTechnicalPage page, @Nonnull final String visibleText) {
        PageStateChecker checker = that(() -> page.elements().isHeadingDisplayed(visibleText));
        checker.page = page;
        checker.orInfo(() -> Info.of(page)
                .message("Na stránce nebyl nalezen očekávaný nadpis.")
                .message("Text nadpisu: [{}]", visibleText));
        return checker;
    }

    /**
     * Adds an {@link Supplier<Info>} eventually providing reason why the check failed
     */
    @Nonnull
    public PageStateChecker orInfo(@Nonnull final Supplier<Info> builder) {
        this.builder = Validate.notNull(builder);
        return this;
    }

    public boolean check() {
        return checker.getAsBoolean();
    }

    @Nonnull
    public Info getInfo() {
        return Validate.notNull(builder.get());
    }

    @Nullable
    public AbstractTechnicalPage getPage() {
        return page;
    }

}
