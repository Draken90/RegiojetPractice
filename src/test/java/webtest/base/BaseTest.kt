package webtest.base

import org.testng.annotations.Test
import webtest.base.step.AbstractTestNew

class BaseTest : AbstractTestNew() {

    @Test
    fun exampleTest(){
        login().validateLoggedIn()
    }
}