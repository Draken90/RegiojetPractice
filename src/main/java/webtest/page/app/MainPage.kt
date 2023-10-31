package webtest.page.app

import org.openqa.selenium.By
import org.testng.Assert.fail
import webtest.base.ComponentType
import webtest.base.ElementDef
import webtest.base.Info.Companion.of
import webtest.page.common.AbstractTechnicalPage
import java.awt.Robot
import java.awt.event.KeyEvent

class MainPage : AbstractTechnicalPage() {
    override fun isOpen(): Boolean {
        TODO("Not yet implemented")
    }

    val eatCookies: ElementDef = ElementDef(ComponentType.BUTTON,"Přijmout vše",By.xpath("//button[text()='Přijmout vše']"))
    val station: ElementDef = ElementDef(ComponentType.INPUT,"Odkud",By.className("react-select__input"))
    //val reactInputFrame: ElementDef = ElementDef(ComponentType.FRAME,"","//div[@role='search']")

    val dateOfDepartureButton: ElementDef = ElementDef(ComponentType.BUTTON,"Odjezd",By.xpath("(//span[text()='Odjezd'])[1]"))
    val departureDate: ElementDef = ElementDef(ComponentType.BUTTON,"Datum odjezdu",By.xpath("//td[@aria-label='Vybrat datum odjezdu: úterý, 17. října 2023']"))
    val dateOfReturnButton: ElementDef = ElementDef(ComponentType.BUTTON,"Návrat",By.xpath("(//span[text()='Návrat'])[1]"))
    val returnDate: ElementDef = ElementDef(ComponentType.BUTTON,"Datum návratu",By.xpath("//td[@aria-label='Vybrat datum příjezdu: neděle, 22. října 2023']"))

    val passengerClick: ElementDef = ElementDef(ComponentType.ANY,"",By.xpath("//span[text()='Cestující']"))
    val adult: ElementDef = ElementDef(ComponentType.BUTTON, "+", By.xpath("(//span[text() = '+'])[1]"))
    val minusAdult: ElementDef = ElementDef(ComponentType.BUTTON, "+", By.xpath("(//span[text() = '-'])[1]"))
    val childrenAndYouth: ElementDef = ElementDef(ComponentType.BUTTON, "+", By.xpath("(//span[text() = '+'])[2]"))
    val studentISIC: ElementDef = ElementDef(ComponentType.BUTTON, "+", By.xpath("(//span[text() = '+'])[3]"))
    val senior: ElementDef = ElementDef(ComponentType.BUTTON, "+", By.xpath("(//span[text() = '+'])[4]"))

    val searchButton: ElementDef = ElementDef(ComponentType.BUTTON,"Hledat",By.xpath("//button[@data-id='search-btn']"))
    public var passengerNumber:Int = 0


    fun clickOnEatCookies() = elements().performClick(eatCookies)

    fun setupPassengers(adults:Int,children618:Int,isic:Int,elderly:Int):List<Int>{

        val passList = listOf(adults,children618,isic,elderly)
        return passList

    }

    fun sumUpPassengers(inputList:List<Int>):Int{

        return inputList.sum()

    }

    fun setDepartureAndArrivalStations(departure: String, arrival: String){
        val stationList = elements().findElements(station)
        val departureInput = stationList[0]
        val arrivalInput = stationList[1]
        elements().setValue(departureInput,departure)
        elements().setValue(arrivalInput, arrival)
    }

    //fun clickOnPassenger()

    fun clickOnAddPassenger(adults:Int,children618:Int,isic:Int,elderly:Int) {
        elements().performClick(passengerClick)
        elements().performClick(minusAdult)
        repeat(adults){
        elements().performClick(adult)}
        repeat(children618) {
            elements().performClick(childrenAndYouth)
        }
        repeat(isic) {
            elements().performClick(studentISIC)
        }
        repeat(elderly) {
            elements().performClick(senior)
        }
        val robot = Robot()
        robot.keyPress(KeyEvent.VK_ESCAPE)
        robot.keyRelease(KeyEvent.VK_ESCAPE)


    }


    fun DateOfDepartureAndReturn(){
        elements().performClick(dateOfDepartureButton)
        elements().performClick(departureDate)
        elements().performClick(dateOfReturnButton)
        elements().performClick(returnDate)

    }

    fun clickOnSearchButton()=elements().performClick(searchButton)

}