package webtest.page.app

import org.openqa.selenium.By
import webtest.base.ComponentType
import webtest.base.ElementDef
import webtest.page.common.AbstractTechnicalPage

class ReservationPage : AbstractTechnicalPage() {
    override fun isOpen(): Boolean {
        TODO("Not yet implemented")
    }

    private var selectReservation: ElementDef = ElementDef(ComponentType.CHECK_BOX, "rezervace", By.xpath("//div[@class='radio w-720 ']"))
    private var selectReservationTrainSection: ElementDef = ElementDef(ComponentType.LABEL, "velkoprostorový oddíl", By.xpath("//input[@id='abteilart-standard-1']"))
    private var selectReservationTrainLocation: ElementDef = ElementDef(ComponentType.LABEL, "okno", By.xpath("//input[@id='platzlage-1-1']"))
    private var selectReservationNext: ElementDef = ElementDef(ComponentType.BUTTON, "Pokračovat", By.id("buchenwunsch-button-weiter-id"))

    fun clickOnReservation(){
       elements().performClick(selectReservation)
    }

    fun clickOnReservationSection(){
        elements().performClick(selectReservationTrainSection)
    }

    fun clickOnReservationLocation(){
        elements().performClick(selectReservationTrainLocation)
    }
    fun clickOnReservationNext(){
        elements().performClick(selectReservationNext)
    }
}
