package webtest.base.step
import webtest.page.app.MainPage
import webtest.page.app.PersonalInfoPage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainPageTestStep: AbstractTestStep() {

    fun eatCookies() = MainPage().run{clickOnEatCookies()}

    fun setDepartureAndArrival(departure:String,arrival:String)=MainPage().setDepartureAndArrivalStations(departure,arrival)

    fun clickOnAddPassenger(adult:Int,children618:Int,isic:Int,senior:Int) {
        val mainPage = MainPage()

        mainPage.clickOnAddPassenger(adult,children618,isic,senior)
    }

    fun setDateOfDepartureAndReturn(){
        val mainPage = MainPage()
        mainPage.DateOfDepartureAndReturn()
    }

    fun search()=MainPage().clickOnSearchButton()
}