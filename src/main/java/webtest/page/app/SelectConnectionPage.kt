package webtest.page.app

import webtest.page.common.AbstractTechnicalPage
import webtest.base.ComponentType
import webtest.base.ElementDef
import org.openqa.selenium.By

class SelectConnectionPage:AbstractTechnicalPage() {
    override fun isOpen(): Boolean {
        TODO("Not yet implemented")
    }

    val connection: ElementDef = ElementDef(ComponentType.BUTTON,"Spoj",By.xpath("//div[contains(@class, 'flex sm:flex-col-reverse items-center sm:items-end')]"))

    fun clickOnConnection() = elements().performClick(connection)
}