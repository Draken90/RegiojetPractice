package webtest.page.app

import org.openqa.selenium.By
import org.testng.Assert.fail
import webtest.base.ComponentType
import webtest.base.ElementDef
import webtest.base.Info.Companion.of
import webtest.page.common.AbstractTechnicalPage

class MainPage : AbstractTechnicalPage() {
    override fun isOpen(): Boolean {
        TODO("Not yet implemented")
    }




    private var fromStationInput: ElementDef = ElementDef(ComponentType.INPUT, "nádraží / zastávka / adresa", By.id("qf-from"))
    private var toStationInput: ElementDef = ElementDef(ComponentType.INPUT, "nádraží / zastávka / adresa", By.id("qf-to"))
    private var logo: ElementDef = ElementDef(ComponentType.PAGE_LOGO, "Deutsche Bahn", By.className("head__logo-image"))

    val ageOption = listOf('F', 'Y', 'E')
    val randomAge = ageOption.shuffled()[1]
    private var age: ElementDef = ElementDef(ComponentType.INPUT, "Věk", By.xpath("//select[@id='qf-traveller-type1']//option[@value='${randomAge}']"))
    val bahnCardOption = listOf('0','1','2','3','4',"14")
    val randomBahnCard = bahnCardOption.shuffled()[1]
    private var bahnCard: ElementDef = ElementDef(ComponentType.INPUT, "BahnCard", By.xpath("//select[@id='qf-traveller-reduction1']//option[@value='${randomBahnCard}']"))

    private var calendar: ElementDef = ElementDef(ComponentType.BUTTON, "Otevřete kalendář", By.xpath("(//button[@class='qf-datepicker__toggle'])[1]"))
    private var calendarDate: ElementDef = ElementDef(ComponentType.BUTTON, "Otevřete kalendář", By.xpath("//span[contains(@aria-label, 'sobota 23. září 2023')]"))

    private var plusHour: ElementDef = ElementDef(ComponentType.BUTTON,"Další celá hodina", By.xpath("(//button[@title='dál k celé hodině'])[1]"))
    private var minusHour: ElementDef = ElementDef(ComponentType.BUTTON,"Předchozí celá hodina", By.xpath("(//button[@title='zpět k celé hodině'])[1]"))

    private var buttonSearch: ElementDef = ElementDef(ComponentType.BUTTON, "Hledat", By.id("qf-search-city"))


    fun validateLoggedIn() {
        if (!elements().isDisplayed(logo)) {
            fail(
                of(this).message("Uživateli se nepodažilo přihlásit nebo se nezobrazilo logo na stránce").element(logo)
                    .build()
            )
        }
    }





    fun writeTextToFromStationInput(cityFrom: String){
        elements().setValue(fromStationInput,cityFrom)
    }

    fun writeTextToToStationInput(cityTo: String){
        elements().setValue(toStationInput,cityTo)
    }

    fun clickOnCalendar() {
        elements().performClick(calendar)
    }

    fun clickOnCalendarDate() {
        elements().performClick(calendarDate)
    }

    fun clickOnAge() {
        elements().performClick(age)
    }
    fun clickOnBahnCard() {
        elements().performClick(bahnCard)
    }

    fun clickOnNextHour()=elements().performClick(plusHour)
    fun clickOnPrevHour()=elements().performClick(minusHour)

    fun clickOnSearchButton()=elements().performClick(buttonSearch)

}