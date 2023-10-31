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
        val pipts = PersonalInfoPageTestStep()
        val sapts = SelectAddonsPageTestStep()
        mpts.eatCookies()
        Thread.sleep(5000)
        mpts.setDepartureAndArrival("Praha", "Amsterdam")
        mpts.setDateOfDepartureAndReturn()
        mpts.clickOnAddPassenger(1,0,0,0)
        mpts.search()
        Thread.sleep(2000)
        sctp.selectConnection()
        Thread.sleep(2000)
        stcts.groundFloorButton()
        Thread.sleep(2000)
        stcts.acceptChangeToAdult()
        Thread.sleep(2000)
        sspts.selectSeats()
        sspts.clickNextFirst()
        Thread.sleep(2000)
        sapts.clickOnContinue()
        Thread.sleep(2000)
        sctp.selectConnection()
        Thread.sleep(2000)
        stcts.groundFloorButton()
        Thread.sleep(2000)
        stcts.acceptChangeToAdult()
        Thread.sleep(2000)
        sspts.selectSeats()
        sspts.clickNextFirst()
        Thread.sleep(2000)
        sapts.clickOnFirstContinue()
        Thread.sleep(2000)
        pipts.fillInConnections()
        pipts.agreeWithTerms()
        pipts.fillInNames("Arnold","Rimmer")
        Thread.sleep(2000)
        pipts.clickOnFinish()


    }
//ToDo Zastřešit test velkým TestStepem a propojit jím zadání s počítáním

}