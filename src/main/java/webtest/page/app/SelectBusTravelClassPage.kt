package webtest.page.app

import webtest.page.common.AbstractTechnicalPage
import webtest.base.ElementDef
import org.openqa.selenium.By
import webtest.base.ComponentType

class SelectBusTravelClassPage: AbstractTechnicalPage() {
    override fun isOpen(): Boolean {
        TODO("Not yet implemented")
    }

    // val standardGF: ElementDef = ElementDef(ComponentType.BUTTON,"9345 KČ",By.xpath("(//button[contains(@class, 'bg-primary-blue') and contains(text(), '9 345 Kč')])[1]"))
    val standardGF: ElementDef = ElementDef(ComponentType.BUTTON,"Spodní patro",By.xpath("//div[@data-id='fare-class-BUS_STANDARD']/div/div[2]/h4/button"))

    fun clickOnStandardGroundFloorButton()=elements().performClick(standardGF)

    fun acceptChangeToAdult(){
        val buttonAccept: ElementDef = ElementDef(ComponentType.BUTTON, "Rozumím",By.xpath("//button[text()='Rozumím']"))
        elements().performClick(buttonAccept)
    }
}