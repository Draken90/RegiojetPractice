package webtest.page.app

import org.openqa.selenium.By
import webtest.base.ComponentType
import webtest.base.ElementDef
import webtest.page.common.AbstractTechnicalPage

class PersonalInfoPage:AbstractTechnicalPage() {
    override fun isOpen(): Boolean {
        TODO("Not yet implemented")
    }
    var pN = 0

    private val emailInput: ElementDef = ElementDef(ComponentType.INPUT, "Email", By.xpath("//input[@name='email']"))
    private val phoneNumberInput: ElementDef = ElementDef(ComponentType.INPUT, "Email", By.xpath("//input[@name='phone']"))
    private val checkboxAgreeWithTerms: ElementDef = ElementDef(ComponentType.CHECK_BOX, "Checkbox", By.xpath("//input[@name='agreeWithTerms']"))
    val finishButton:ElementDef = ElementDef(ComponentType.BUTTON,"Pokračovat",By.xpath("(/button[text()='Přidat do košíku'])[1]"))



    fun writeEmail()=elements().setValue(emailInput,"test@mail.cz")
    fun writePhoneNumber(){
        val pNI=elements().findElement(phoneNumberInput)
        pNI.sendKeys("800300301")}

    fun checkCheckbox() = elements().performClick(checkboxAgreeWithTerms)

    fun writeNameAndSurname(name:String,surname:String){


        var passNum = MainPage()

        while (passNum>pN){
            val nameInput: ElementDef = ElementDef(ComponentType.INPUT, "Jméno", By.xpath("//input[@name='passengers.${pN}.firstName']"))
            val surnameInput: ElementDef = ElementDef(ComponentType.INPUT, "Příjmení", By.xpath("//input[@name='passengers.${pN}.surname']"))
            elements().setValue(nameInput,name)
            elements().setValue(surnameInput,surname)
            pN++

        }

    }

    fun clickOnFinishButton()=elements().performClick(finishButton)



}