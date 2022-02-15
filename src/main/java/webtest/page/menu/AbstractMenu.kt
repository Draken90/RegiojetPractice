package webtest.page.menu

import org.openqa.selenium.By
import webtest.base.ComponentType
import webtest.base.ElementDef
import webtest.page.common.AbstractTechnicalPage

abstract class AbstractMenu : AbstractTechnicalPage() {

    fun clickOnMenuItem(value: String) =
        elements().performClick(ElementDef(ComponentType.MAIN_MENU_ITEM, By.xpath("//*[text()='$value']")))

    protected fun validateMenuItemsAreDisplayed(vararg values: String) =
        elements().validateElementsAreDisplayed(
            *values.map { ElementDef(ComponentType.MAIN_MENU_ITEM, By.xpath("//*[text()='$it']")) }.toTypedArray()
        )

}