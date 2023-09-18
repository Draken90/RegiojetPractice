package webtest.base

import org.testng.annotations.Test
import webtest.base.step.*

class BaseTest : AbstractTestNew() {

    var teststep1 = MainPageTestStep()
    var teststep2 = SelectTrainTestStep()
    var teststep3 = AgeUpdatePageTestStep()
    var teststep4 = CustomerInfoPageTestStep()
    var teststep5 = ReservationTestStep()
    var teststep6 = PaymentTestStep()

    @Test
    fun testPrahaBerlin() {
        Thread.sleep(5000)
        acceptCookies()
        selectCzechMutation()
        teststep1.fillInConnection("Praha hl.n.","Berlin hbf",8)
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
        teststep5.selectTrainReservationNext()
        teststep5.selectTicketInsurance()
        teststep5.selectTicketInsuranceNext()
        Thread.sleep(5000)
        teststep6.fillInFirstNamePayment("Arnold")
        teststep6.fillInLastNamePayment("Rimmer")
        teststep6.fillInEmailPayment("arnold.rimmer@jmc.uk")
        teststep6.fillInStreetPayment("Ships cabin")
        teststep6.fillInPSCPayment("11111")
        teststep6.fillInPlacePayment("RedDwarf")
        teststep6.selectPayment()
        teststep6.selectCountry()
    }


}