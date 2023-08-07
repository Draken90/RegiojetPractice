package webtest.page.app

import org.openqa.selenium.By
import webtest.base.ComponentType
import webtest.base.ElementDef
import webtest.page.common.AbstractTechnicalPage

class AgeUpdatePage: AbstractTechnicalPage() {

    override fun isOpen(): Boolean {
        TODO("Not yet implemented")
    }

    private val ageOptionAdult: ElementDef = ElementDef(ComponentType.ANY,"", By.xpath("//select[@id='travellerType_1']//option[@value='E']"))
    private val ageExactInput: ElementDef = ElementDef(ComponentType.INPUT,"Věk","travellerAge_1")
    private val updateButton: ElementDef = ElementDef(ComponentType.BUTTON,"Aktualizovat","searchConnectionButton")

    fun selectAgeAdult() = elements().performClick(ageOptionAdult)
    fun writeExactAge(age: String)= elements().setValue(ageExactInput,age)

    fun updateSelection() = elements().performClick(updateButton)
}