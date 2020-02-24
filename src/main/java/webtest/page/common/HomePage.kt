package webtest.page.common

import org.openqa.selenium.By
import webtest.base.ComponentType
import webtest.page.AbstractTechnicalPage

class HomePage : AbstractTechnicalPage() {
    override fun isOpen(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun validateSearchIsDisplayed() = elements().validateIsDisplayedExactlyOne(
        ComponentType.TEXT_INPUT,
        "Google vyhledávací pole",
        By.className("RNNXgb")
    );
}