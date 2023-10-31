package webtest.base.step

import webtest.page.app.MainPage
import webtest.page.app.SelectSeatPage

class SelectSeatPageTestStep : AbstractTestStep() {

    fun selectSeats() = SelectSeatPage().selectSeats(MainPage().passengerNumber)
    fun clickNextFirst()= SelectSeatPage().clickOnFirstContinueButton()

}