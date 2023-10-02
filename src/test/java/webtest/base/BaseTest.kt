package webtest.base

import org.testng.annotations.Test
import webtest.base.step.*
import webtest.page.app.SelectSeatPage

class BaseTest : AbstractTestNew() {


    @Test
    fun testRJ() {
        val mpts = MainPageTestStep()
        val sctp = SelectConnectionTestPage()
        val stcts = SelectBusTravelClassTestStep()
        val sspts = SelectSeatPageTestStep()
        mpts.eatCookies()
        Thread.sleep(5000)
        mpts.setDepartureAndArrival("Praha", "Řím")
        mpts.setDateOfDepartureAndReturn()
        mpts.clickOnAddPassenger()
        mpts.search()
        Thread.sleep(2000)
        sctp.selectConnection()
        Thread.sleep(2000)
        stcts.groundFloorButton()
        Thread.sleep(2000)
        stcts.acceptChangeToAdult()
        Thread.sleep(2000)
        sspts.selectSeats(5)


    }


}