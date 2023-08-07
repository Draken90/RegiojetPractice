package webtest.page.app

import org.openqa.selenium.By
import webtest.base.ComponentType
import webtest.base.ElementDef
import webtest.page.common.AbstractTechnicalPage

class SelectTrainPage: AbstractTechnicalPage() {
    override fun isOpen(): Boolean {
        TODO("Not yet implemented")
    }

    private var selectTrain: ElementDef = ElementDef(ComponentType.BUTTON, "Vyber si vlak", By.xpath("(//a[@class='buttonbold'])[1]"))
    private var selectTrainTicket: ElementDef = ElementDef(ComponentType.LABEL, "Vyber si jízdenku", By.xpath("//label[@for='offer_1']"))
    private var selectNext: ElementDef = ElementDef(ComponentType.BUTTON, "Dále", By.id("availContinueButton"))

    fun clickOnTrain(){
        elements().performClick(selectTrain)
    }

    fun clickOnTrainTicket(){
        elements().performClick(selectTrainTicket)
    }

    fun clickOnNext(){
        elements().performClick(selectNext)
    }

}