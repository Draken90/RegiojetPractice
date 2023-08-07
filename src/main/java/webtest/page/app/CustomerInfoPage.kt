package webtest.page.app

import org.openqa.selenium.By
import webtest.base.ComponentType
import webtest.base.ElementDef
import webtest.page.common.AbstractTechnicalPage

class CustomerInfoPage: AbstractTechnicalPage() {
    override fun isOpen(): Boolean {
        TODO("Not yet implemented")
    }
    private val firstName: ElementDef = ElementDef(ComponentType.INPUT,"Jméno", By.xpath("//input[@name='vorname']"))
    private val lastName: ElementDef = ElementDef(ComponentType.INPUT,"Příjmení", By.xpath("//input[@name='nachname']"))
    private val continueAsAnonymButton: ElementDef = ElementDef(ComponentType.BUTTON,"Pokračovat jako host","button.weiter.anonym")


    fun writeFirstName(name:String)=elements().setValue(firstName,name)
    fun writeLastName(name:String)=elements().setValue(lastName,name)

    fun clickOnContinueAsAnonymButton()=elements().performClick(continueAsAnonymButton)





}