package webtest.page.app

import org.openqa.selenium.JavascriptExecutor



import org.openqa.selenium.By
import org.openqa.selenium.SearchContext
import org.openqa.selenium.WebElement
import webtest.base.ComponentType
import webtest.base.ElementDef
import webtest.page.common.AbstractTechnicalPage

class CookiePage: AbstractTechnicalPage() {

    override fun isOpen(): Boolean = elements().findElement(dialogWindow).isDisplayed

    val dialogWindow: ElementDef = ElementDef(ComponentType.ANY, "", By.id("consent-layer"))

    fun switchWindows(){
        val windowHandles = driver.windowHandles
        for (windowHandle in windowHandles) {
            driver.switchTo().window(windowHandle)
        }
    }

    fun eatCookies() {
        val jsExecutor = driver as JavascriptExecutor
        val element = driver.findElement(By.xpath("(//div)[1]")).shadowRoot


        val button = element.findElement(By.cssSelector("#consent-layer > div.consent-layer__btn-container > button.btn.btn--secondary.js-accept-all-cookies"))
        jsExecutor.executeScript("arguments[0].click()", button)

    }

}