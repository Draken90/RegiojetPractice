package webtest.page.app

import org.openqa.selenium.By
import webtest.base.ComponentType
import webtest.base.ElementDef
import webtest.page.common.AbstractTechnicalPage

class SelectAddonsPage:AbstractTechnicalPage() {
    override fun isOpen(): Boolean {
        TODO("Not yet implemented")
    }

    val continueButton:ElementDef = ElementDef(ComponentType.BUTTON,"Pokračovat",By.xpath("(//div[@class='w-full flex-grow-0 flex-shrink-0 lg:w-2/3']//button[text()='Pokračovat'])[1]"))

    fun clickOnFirstContinueButton()=elements().performClick(continueButton)

    fun clickOnContinueButton(){
        val fullWindowSection: ElementDef = ElementDef(ComponentType.ANY,"", By.xpath("//div[@class='w-full flex-grow-0 flex-shrink-0 lg:w-2/3']"))
        val fWS= elements().findElement(fullWindowSection)
        val continueButtonList=fWS.findElements(By.xpath("//div[2]/nav/button"))
        val cB=continueButtonList[0]

        elements().performClick(cB)
    }
}