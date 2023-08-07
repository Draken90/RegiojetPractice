package webtest.base.step

import webtest.page.app.CustomerInfoPage

class CustomerInfoPageTestStep: AbstractTestStep() {

    fun fillInFirstName(firstName: String)= CustomerInfoPage().run{writeFirstName(firstName)}
    fun fillInLastName(lastName: String)= CustomerInfoPage().run{writeLastName(lastName)}

    fun proceedAsAnonym() = CustomerInfoPage().run{clickOnContinueAsAnonymButton()}

}