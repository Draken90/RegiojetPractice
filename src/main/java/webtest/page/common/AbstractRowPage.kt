package webtest.page.common

import org.openqa.selenium.WebElement
import webtest.base.ElementDef

abstract class AbstractRowPage(val baseElement: WebElement) : AbstractTechnicalPage() {

    fun getChildValue(def: ElementDef) : String =
        elements().findSubElement(baseElement,def).text
}