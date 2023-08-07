package webtest.base

import org.openqa.selenium.support.ui.Select
import org.testng.annotations.Test
import webtest.base.step.*

class BaseTest : AbstractTestNew() {

    var teststep1 = MainPageTestStep()
    var teststep2 = SelectTrainTestStep()
    var teststep3 = AgeUpdatePageTestStep()
    var teststep4 = CustomerInfoPageTestStep()
    var teststep5 = ReservationTestStep()

    @Test
    fun exampleTest() {
        Thread.sleep(5000)
        acceptCookies()
        selectCzechMutation()
        teststep1.selectFromStation("Praha hl. n.")
        teststep1.selectToStation("Berlin Hbf")
        teststep1.selectCalendar()
        teststep1.selectCalendarDate()
        teststep1.selectDepartureTime(8)
        teststep1.selectAge()
        teststep1.selectBahnCard()
        teststep1.searchForConnection()
        Thread.sleep(5000)
        acceptCookies()
        teststep2.selectTrain()
        teststep3.selectAgeInterval("Adult")
        teststep3.writeExactAge("30")
        teststep3.updateSelection()
        teststep2.selectTrain()
        teststep2.selectTrainTicket()
        teststep2.selectNext()
        teststep4.fillInFirstName("Arnold")
        teststep4.fillInLastName("Rimmer")
        teststep4.proceedAsAnonym()
        teststep5.selectTrainReservation()
        teststep5.selectTrainReservationSection()
        teststep5.selectTrainReservationLocation()
        teststep5.selectTrainReservationNext()
        Thread.sleep(10000)

    }
}