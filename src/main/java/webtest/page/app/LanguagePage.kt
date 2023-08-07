package webtest.page.app

import org.openqa.selenium.By
import webtest.base.ComponentType
import webtest.base.ElementDef
import webtest.page.common.AbstractTechnicalPage

class LanguagePage: AbstractTechnicalPage() {

    override fun isOpen(): Boolean = elements().findElement(czechMutation).isDisplayed

    private val czechMutation: ElementDef = ElementDef(ComponentType.BUTTON, "Česky", By.xpath("//a[@href='/cs']"))

    fun selectCzechMutation()=elements().performClick(czechMutation)
}