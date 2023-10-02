package webtest.base.step

import webtest.page.app.SelectSeatPage

class SelectSeatPageTestStep : AbstractTestStep() {

    fun selectSeats(numberOfSeats: Int) = SelectSeatPage().selectSeats(numberOfSeats)
}