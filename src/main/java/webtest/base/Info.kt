package webtest.base

import com.google.common.base.Joiner
import org.apache.commons.lang3.Validate
import org.apache.commons.lang3.exception.ExceptionUtils
import org.openqa.selenium.By
import webtest.page.AbstractTechnicalPage
import java.util.*

private const val DEFAULT_INDENT = "    "

private const val ELEMENT_TYPE_TEMPLATE = "Element type: [{}]"
private const val ELEMENT_CAPTION_TEMPLATE = "Element Caption: [{}]"
private const val ELEMENT_CAPTION_KEY_TEMPLATE = "Element Key: [{}]"
private const val ELEMENT_STYLE_TEMPLATE = "Element style name: [{}]"
private const val ELEMENT_SELECTOR_TEMPLATE = "Element selector: [{}]"
private const val ON_THE_SAME_LINE = "__ON_THE_SAME_LINE__"

/**
 * Builder class for creating complex messages about webtest pages and elements.<br></br>
 * Can be used to construct message for Exception (e.g. [org.openqa.selenium.NotFoundException]) or fail method of Assert class.
 */
class Info private constructor(private val page: AbstractTechnicalPage) {
    private val parts = ArrayList<String>()
    private var withCurrentPageAndErrorInfo = true

    /**
     * Returns single message containing all information that was put together by this builder.
     */
    fun build(): String {

        // Do not put Page class into method addCurrentPageStateAndErrorInfo because it is not the same!
        // class is from page instance but method work with values of current driver!
        parts.add("Page class: [${page.javaClass.simpleName}]")

        if (withCurrentPageAndErrorInfo) {
            addCurrentPageStateAndErrorInfo()
        }

        return parts
            .map {
                // do not trim it from left because indented lines can be added to message
                it.trimEnd()
            }
            .filter { it.isNotBlank() }
            .joinToString(NL)
            .replace((NL + ON_THE_SAME_LINE).toRegex(), "")
    }

    /**
     * Removes [Info.addPageAndErrorInfo] content from resulting message.
     */
    fun withoutCurrentPageAndErrorInfo(): Info {
        withCurrentPageAndErrorInfo = false
        return this
    }

    private fun format(format: String?, vararg args: Any): String {
        return if (format == null) ""
        else String.format(format.replace("{}", "%s"), *args)
    }

    /**
     * Add parametrized message using {} or %s, %d ... to mark places where parameters should be inserted.
     *
     *
     * e.g.
     *
     * message("Hi {}", "there") will return Hi there.
     */
    fun message(message: String, vararg params: Any): Info {
        if (message.isNotBlank()) {
            parts.add(format(message, *params))
        }
        return this
    }

    fun messageSameLine(message: String, vararg params: Any): Info =
        message(ON_THE_SAME_LINE + message, *params)

    /**
     * Add element class, element key and message which this key refers to.
     *
     *
     * e.g.
     *
     * Element type: [ButtonElement]
     *
     * Element Caption: [Žádost o úhradu z externího účtu]
     *
     * Element Key: [LABEL.rft.initial.heading.create]
     */
    fun element(
        elementClass: Class<Any>,
        // TODO: z nejakeho duvodu projekt OneIB porad nema klice od vyvoje!!! Trask porad nepozadal vyvoj o klice I18NKey!
        elementKey:/*I18NKey*/ Any
    ): Info {
        Validate.notNull(elementClass, "elementClass")
        Validate.notNull(elementKey, "elementKey")
        element(elementClass)
        element(elementKey)
        return this
    }

    /**
     * Add element class
     *
     *
     * e.g.
     *
     * Element type: [ButtonElement]
     */
    fun element(elementClass: Class<Any>): Info {
        Validate.notNull(elementClass)
        parts.add(format(ELEMENT_TYPE_TEMPLATE, elementClass.simpleName))
        return this
    }

    /**
     * Add element key and message which this key refers to.
     *
     *
     * e.g.
     *
     * Element Caption: [Žádost o úhradu z externího účtu]
     *
     * Element Key: [LABEL.rft.initial.heading.create]
     */
    fun element(
        // TODO: z nejakeho duvodu projekt OneIB porad nema klice od vyvoje!!! Trask porad nepozadal vyvoj o klice I18NKey!
        elementKey:/*I18NKey*/ Any
    ): Info {
        Validate.notNull(elementKey)
        parts.add(
            Joiner.on(System.lineSeparator()).join(
                format(
                    ELEMENT_CAPTION_TEMPLATE,
                    // TODO: z nejakeho duvodu projekt OneIB porad nema klice od vyvoje!!! Trask porad nepozadal vyvoj o klice I18NKey!
                    // page.getMessage(elementKey)).getMessage(),
                    "TODO Trask ma zazadat o elementKey I18NKey - $elementKey"
                ),
                format(
                    ELEMENT_CAPTION_KEY_TEMPLATE,
                    // TODO: z nejakeho duvodu projekt OneIB porad nema klice od vyvoje!!! Trask porad nepozadal vyvoj o klice I18NKey!
                    // elementKey.getKey()).getMessage())
                    "TODO Trask ma zazadat o elementKey I18NKey - $elementKey"
                )
            )
        )
        return this
    }

    /**
     * Add element style.
     *
     *
     * e.g.
     *
     * Element style name: [ceb-form-step2]
     */
    fun element(elementStyleName: String): Info {
        parts.add(format(ELEMENT_STYLE_TEMPLATE, elementStyleName))
        return this
    }

    /**
     * Add element selector.
     *
     *
     * e.g.
     *
     * Element selector: By.xpath = [div[attr='element-attr-value']]
     */
    fun element(elementSelector: By): Info {
        parts.add(format(ELEMENT_SELECTOR_TEMPLATE, elementSelector))
        return this
    }

    /**
     * Add Page information and error message displayed on page if any is present.<br></br>
     * Page information contains page url, page class name, page name, page language and displayed errors
     *
     *
     * e.g.
     *
     * Page url: [http://w2ab00kw.tda001.ad.acc.sys:1099/platby/zadost-o-prevod-mt101]
     *
     * Page class: [RftInitialPage]
     *
     * Page name: [Request For Transfer - Initial Page]
     *
     * Page language: [cs]
     *
     * Page errors: No error message detected on page.
     */
    private fun addCurrentPageStateAndErrorInfo() {
        parts.add("Current page url: [${page.getCurrentUrl()}]")
        parts.add("Current page title: [${page.title}]")
    }


    fun exception(message: String, exception: Exception): Info {
        parts.add(message)
        parts.add(ExceptionUtils.getStackTrace(exception))
        return this
    }

    /**
     * Add each item in array delimited with delimiter.
     */
    @JvmOverloads
    fun collection(
        items: Array<Any?>,
        delimiter: String = System.lineSeparator(),
        indent: String = DEFAULT_INDENT
    ): Info =
        collection(items.asList(), delimiter = delimiter, indent = indent)

    /**
     * Add each item in list delimited with delimiter.
     */
    @JvmOverloads
    fun collection(
        items: List<Any?>,
        delimiter: String = System.lineSeparator(),
        indent: String = DEFAULT_INDENT
    ): Info {
        if (items.isEmpty()) {
            parts.add("[empty collection]")
        } else {
            val joinedText = StringBuilder()
            for (idx in items.indices) {
                if (delimiter == System.lineSeparator())
                    joinedText.append(indent)
                joinedText.append(if (items[idx] == null) "<null>" else items[idx].toString())
                if (idx < items.size - 1) {
                    joinedText.append(delimiter)
                }
            }
            parts.add(joinedText.toString())
        }
        return this
    }

    companion object {
        @JvmStatic
        fun of(page: AbstractTechnicalPage): Info = Info(page)
    }

}

fun buildFailedReasons(failedReasons: List<Info>, withoutPageInfo: Boolean): String =
    failedReasons
        .map { if (withoutPageInfo) it.withoutCurrentPageAndErrorInfo().build() else it.build() }
        .distinct()
        .joinToString(NL)
