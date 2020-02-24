package webtest.exceptions

import org.openqa.selenium.NotFoundException
import java.net.URL

// WARNING !
//
// DO NOT USE keyword 'typealias' for exception because error message will not display name of your class !!!
//
// WARNING !

class InvalidConfigurationException @JvmOverloads constructor(message: String, cause: Throwable? = null) :
    AssertionError(message, cause)

class InvalidValueException @JvmOverloads constructor(message: String, cause: Throwable? = null) :
    AssertionError(message, cause)

class InvalidFormatException @JvmOverloads constructor(message: String, cause: Throwable? = null) :
    java.lang.IllegalStateException(message, cause)

class InvalidTestDataException @JvmOverloads constructor(message: String, cause: Throwable? = null) :
    AssertionError(message, cause)

class ServerIsDownException @JvmOverloads constructor(message: String, cause: Throwable? = null) :
    IllegalStateException(message, cause)

class LoginException @JvmOverloads constructor(message: String, cause: Throwable? = null) :
    AssertionError(message, cause)

/**
 * Use this class if you need rethrow design exception as runtime exception
 * and avoid Sonar inspection error "Define and throw a dedicated exception instead of using a generic one."
 */
class JustCoverException @JvmOverloads constructor(message: String, cause: Throwable? = null) :
    RuntimeException(message, cause) {
    constructor(cause: Throwable) : this("Unexpected error. See inner exception for details.", cause)
}

class SmsAuthorizationServiceException(message: String, url: URL, cause: Throwable?) :
    IllegalStateException("$message\nService URL: [$url]", cause) {
    constructor(message: String, url: URL) : this(message, url, null)
}

/**
 * Use only in case if tested portal displays error message on page.
 */
class UnexpectedErrorVisibleOnPageException @JvmOverloads constructor(message: String, cause: Throwable? = null) :
    AssertionError(message, cause)

class ElementNotFoundException @JvmOverloads constructor(message: String, cause: Throwable? = null) :
    NotFoundException(message, cause)

class TooManyElementsWasFoundException @JvmOverloads constructor(message: String, cause: Throwable? = null) :
    NotFoundException(message, cause)

class PageIsNotOpenException @JvmOverloads constructor(message: String, cause: Throwable? = null) :
    AssertionError(message, cause)

class ComboBoxItemNotFoundException @JvmOverloads constructor(message: String, cause: Throwable? = null) :
    AssertionError(message, cause)

class TimeoutException @JvmOverloads constructor(message: String, cause: Throwable? = null) :
    AssertionError(message, cause)

/**
 * Use only in cases when you are testing payment and payment was not found on page or database.
 */
class PaymentNotFoundException @JvmOverloads constructor(message: String, cause: Throwable? = null) :
    AssertionError(message, cause)
