package webtest.base

import org.apache.commons.lang3.StringUtils.isNotBlank
import org.openqa.selenium.WebElement
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.testng.Assert.fail

import webtest.exceptions.InvalidValueException
import webtest.exceptions.PageIsNotOpenException
import webtest.page.common.AbstractTechnicalPage


object Assert {
    private val log: Logger = LoggerFactory.getLogger(Assert.javaClass)

    @JvmStatic
    @JvmOverloads
    fun assertPageIsOpen(page: AbstractTechnicalPage, additionalMessage: String = "") {
        if (!page.isOpen) {
            val reasonsPrefix = "Reason(s):"
            val isServerError = page.currentUrl.contains("/error.html?errorCode", true)
            val errorMessage = Info.of(page)
                .message(if (isServerError) "Page is not open as expected - most probably server error." else "Page is not open as expected.")
                .also {
                    if (isNotBlank(additionalMessage)) {
                        it.message("Additional message: [%s]", additionalMessage)
                    }
                }
            if (page.validationFailures.isNotEmpty()) {
                errorMessage.message(
                    "$reasonsPrefix " + page.buildFailedReasons(true).indentLinesExceptFirst(
                        reasonsPrefix.length + 1
                    )
                )
            }
            if (page.validationFailures.isEmpty()) {
                log.error("Hey programmer! Page [${page.javaClass.simpleName}] has method 'isOpen' used not correctly because collection 'pageIsNotOpenReasons' is empty.")
            }
            val fullMessage = errorMessage.build()
            val pageNotOpenError = PageIsNotOpenException(fullMessage)
            throw pageNotOpenError
        }
    }

    @JvmStatic
    fun assertNotEmpty(list: List<Any>?, message: String) {
        if (list == null || list.isEmpty()) {
            throw InvalidValueException("Tested collection should be not empty. $message")
        }
    }

    @JvmStatic
    fun assertElementIsDisabled(page: AbstractTechnicalPage, def: ElementDef){
        if (page.elements().findElement(def).isEnabled){
            fail(Info.of(page)
                .message("Bylo očekáváno, že tlačítko bude disabled, ale není")
                .element(def)
                .build())
        }
    }
}