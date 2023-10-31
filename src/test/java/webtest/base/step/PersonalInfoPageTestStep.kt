package webtest.base.step

import webtest.page.app.PersonalInfoPage

class PersonalInfoPageTestStep:AbstractTestStep() {

    fun fillInConnections(){

        PersonalInfoPage().writePhoneNumber()
        PersonalInfoPage().writeEmail()


    }

    fun agreeWithTerms(){
        PersonalInfoPage().run{
            this.checkCheckbox()
        }
    }

    fun fillInNames(name:String,surname:String){

        PersonalInfoPage().run{
            this.writeNameAndSurname(name,surname)
        }
    }

    fun clickOnFinish()=PersonalInfoPage().clickOnFinishButton()
}