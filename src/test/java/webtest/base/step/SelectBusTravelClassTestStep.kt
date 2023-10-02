package webtest.base.step
import webtest.page.app.SelectBusTravelClassPage

class SelectBusTravelClassTestStep: AbstractTestStep() {

    fun groundFloorButton()= SelectBusTravelClassPage().clickOnStandardGroundFloorButton()
    fun acceptChangeToAdult()=SelectBusTravelClassPage().acceptChangeToAdult()
}