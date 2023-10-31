package webtest.base.step

import webtest.page.app.SelectAddonsPage

class SelectAddonsPageTestStep: AbstractTestStep() {

    fun clickOnContinue()=SelectAddonsPage().clickOnContinueButton()
    fun clickOnFirstContinue()=SelectAddonsPage().clickOnFirstContinueButton()
}