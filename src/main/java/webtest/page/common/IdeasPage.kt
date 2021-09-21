package webtest.page.common

import org.openqa.selenium.By
import webtest.base.ComponentType
import webtest.base.PageStateChecker
import webtest.page.AbstractTechnicalPage

class IdeasPage : AbstractTechnicalPage() {
    override fun isOpen(): Boolean =
       checkAll(PageStateChecker.thatIsElementDisplayedById(this,ComponentType.BUTTON,"dsds"))


    fun verifyButtonReloadIsDisplayed() {
        elements().validateIsDisplayedExactlyOne(
            ComponentType.BUTTON,
            "Tlačítko reload",
            By.xpath("//button[text()='Reload']")
        )
    }
}