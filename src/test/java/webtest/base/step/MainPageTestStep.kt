package webtest.base.step
import webtest.page.app.MainPage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainPageTestStep: AbstractTestStep() {

    fun selectFromStation(from: String){
        MainPage().run{
            writeTextToFromStationInput(from)
        }
    }

    fun selectToStation(toStation: String){
        MainPage().run{
            writeTextToToStationInput(toStation)
        }
    }

    fun selectCalendar(){
        MainPage().run{
            clickOnCalendar()
        }
    }

    fun selectCalendarDate(){
        MainPage().run{
            clickOnCalendarDate()
        }
    }

    fun selectAge(){
        MainPage().run{
            clickOnAge()
        }
    }
    fun selectBahnCard(){
        MainPage().run{clickOnBahnCard()
        }
    }

    fun selectDepartureTime(depTime: Int){
        val currentDateTime = LocalDateTime.now()
        val currentHour = currentDateTime.format(DateTimeFormatter.ofPattern("HH")).toInt()
        if (currentHour>depTime){
            val repeatTimes =currentHour-depTime+1
            repeat(repeatTimes){
                MainPage().run{
                    clickOnPrevHour()
                }
            }
        }
        else{
            val repeatTimes =depTime-currentHour
            repeat(repeatTimes){
                MainPage().run{
                    clickOnNextHour()
                }
            }
        }

    }


    fun searchForConnection()=MainPage().run{clickOnSearchButton()}

}