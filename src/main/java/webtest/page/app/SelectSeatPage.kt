package webtest.page.app

import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import webtest.base.ComponentType
import webtest.base.ElementDef
import webtest.page.common.AbstractTechnicalPage

class SelectSeatPage : AbstractTechnicalPage() {
    override fun isOpen(): Boolean {
        TODO("Not yet implemented")
    }

    //val seatNumberingGrid: ElementDef = ElementDef(ComponentType.BUTTON, "", By.xpath("//path[contains(@class,'text-secondary-seatfree')]"))
    val seatNumberingGrid: ElementDef = ElementDef(ComponentType.BUTTON, "", By.xpath("(//*[@id='seats-numbering'])[1]"))
    val sNG = elements().findElement(seatNumberingGrid)
    val freeSeatsList = sNG.findElements(By.xpath("//*[contains(@class,'seatfree')]/parent::*"))
    fun selectSeats(numberOfSeats: Int) {

        println("V seznamu je " + freeSeatsList.size + " sedadel")
        val selectedSeats = freeSeatsList.subList(0, numberOfSeats)
        selectedSeats.forEach { element ->
            elements().performClick(element)
        }


    }
}