package webtest.page.app

import webtest.page.common.AbstractTechnicalPage
import webtest.base.ComponentType
import webtest.base.ElementDef
import org.openqa.selenium.By

class SelectConnectionPage:AbstractTechnicalPage() {
    override fun isOpen(): Boolean {
        TODO("Not yet implemented")
    }

    val connection: ElementDef = ElementDef(ComponentType.BUTTON,"Spoj",By.xpath("(//button[contains(@data-id,'connection-card-price')])[1]"))

    fun clickOnConnection() = elements().performClick(connection)
}