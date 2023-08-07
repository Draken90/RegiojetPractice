package webtest.base.step

import webtest.page.app.AgeUpdatePage

class AgeUpdatePageTestStep: AbstractTestStep() {

    fun selectAgeInterval(interval: String)
        {
            if (interval == "Adult") {
                AgeUpdatePage().run {
                    selectAgeAdult()
                }
            }
        }
    fun writeExactAge(age: String) = AgeUpdatePage().run{writeExactAge(age)}

    fun updateSelection() = AgeUpdatePage().run{updateSelection()}
}