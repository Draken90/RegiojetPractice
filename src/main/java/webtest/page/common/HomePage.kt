package webtest.page.common

import org.openqa.selenium.By
import webtest.base.ComponentType
import webtest.page.AbstractTechnicalPage

class HomePage : AbstractTechnicalPage() {
    override fun isOpen(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun validateLogoIsDisplayed() = elements().validateIsDisplayedExactlyOne(
        ComponentType.IMAGE,
        "Logo Škoda auto",
        By.id("nav_skoda_logo")
    )

    fun clickOnIdeas(): IdeasPage {
        elements().performClick(ComponentType.BUTTON, By.id("navlink_ideas"))
        return IdeasPage()
    }
}