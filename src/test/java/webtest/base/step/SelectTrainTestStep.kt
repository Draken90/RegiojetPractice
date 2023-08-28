package webtest.base.step

import webtest.page.app.MainPage
import webtest.page.app.SelectTrainPage

class SelectTrainTestStep: AbstractTestStep(){

    fun selectTrain(){
        SelectTrainPage().run{
            clickOnTrain()
        }
    }

    fun selectTrainTicket(){
        SelectTrainPage().run{
            clickOnTrainTicket()
        }
    }

    fun selectNext(){
        SelectTrainPage().run{
            clickOnNext()
        }
    }

}