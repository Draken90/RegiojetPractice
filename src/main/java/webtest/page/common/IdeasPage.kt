package webtest.page.common

import org.openqa.selenium.By
import webtest.base.ComponentType
import webtest.page.AbstractTechnicalPage

class IdeasPage : AbstractTechnicalPage() {
    override fun isOpen(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun verifyButtonReloadIsDisplayed() {
        elements().validateIsDisplayedExactlyOne(
            ComponentType.BUTTON,
            "Tlačítko reload",
            By.xpath("//button[text()='Reload']")
        )
    }
}