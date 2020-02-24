package webtest.utils

import org.apache.commons.lang3.StringUtils.isBlank
import org.openqa.selenium.WebElement
import org.slf4j.LoggerFactory

private val log = LoggerFactory.getLogger(WebElementUtils::class.java)!!

object WebElementUtils {

    @JvmStatic
    fun getElementTagSilently(element: WebElement?): String =
        if (element == null) {
            "element is null"
        } else
            try {
                element.tagName
            } catch (ex: Exception) {
                "NOT AVAILABLE"
            }

    @JvmStatic
    fun getElementIdentificationSilently(element: WebElement): String {
        try {
            var identification: String? = element.text
            if (isBlank(identification)) {
                identification = element.getAttribute("name")
            }
            if (isBlank(identification)) {
                identification = element.getAttribute("id")
            }
            if (isBlank(identification)) {
                identification = element.getAttribute("href")
            }
            if (isBlank(identification)) {
                identification = element.tagName
            }
            if (identification == null) {
                identification = ""
            }
            return identification
        } catch (ex: Exception) {
            log.error("Could not detect element text. Exception is catched.", ex)
            return "ERROR - unable to detect!"
        }

    }

    @JvmStatic
    fun getElementVisibleTextSilently(element: WebElement?): String =
        if (element == null) {
            "element is null"
        } else
            try {
                element.text
            } catch (ex: Exception) {
                "NOT AVAILABLE"
            }

}
