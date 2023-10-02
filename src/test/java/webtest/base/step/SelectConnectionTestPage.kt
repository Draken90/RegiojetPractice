package webtest.base.step

import webtest.page.app.SelectConnectionPage

class SelectConnectionTestPage: AbstractTestStep() {

    fun selectConnection()= SelectConnectionPage().clickOnConnection()
}