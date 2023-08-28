package webtest.base.step

import webtest.page.app.ReservationPage

class ReservationTestStep: AbstractTestStep(){

    fun selectTrainReservation(){
        ReservationPage().run{
            clickOnReservation()
        }
    }
    fun selectTrainReservationSection(){
        ReservationPage().run{
            clickOnReservationSection()
        }
    }
    fun selectTrainReservationLocation(){
        ReservationPage().run{
            clickOnReservationLocation()
        }
    }
    fun selectTrainReservationNext(){
        ReservationPage().run{
            clickOnReservationNext()
        }
    }

}