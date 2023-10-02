package webtest.base.step
import webtest.page.app.MainPage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainPageTestStep: AbstractTestStep() {

    fun eatCookies() = MainPage().run{clickOnEatCookies()}

    fun setDepartureAndArrival(departure:String,arrival:String)=MainPage().setDepartureAndArrivalStations(departure,arrival)

    fun clickOnAddPassenger() {
        val mainPage = MainPage()
        mainPage.clickOnAddPassenger()
    }

    fun setDateOfDepartureAndReturn(){
        val mainPage = MainPage()
        mainPage.DateOfDepartureAndReturn()
    }

    fun search()=MainPage().clickOnSearchButton()
}