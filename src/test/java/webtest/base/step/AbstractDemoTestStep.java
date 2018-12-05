package webtest.base.step;

import webtest.demo.demosmoke.HomePage;
import webtest.demo.demosmoke.LoginPage;


import static org.testng.Assert.assertTrue;
import static webtest.base.Assert.assertPageIsOpen;

public class AbstractDemoTestStep {

    public void login() {
        LoginPage loginPage = new LoginPage();
        assertPageIsOpen(loginPage);
        loginPage.UncheckStayLogged();
        loginPage.login();
        assertTrue(loginPage.isUserLogged(), "User is not logged after filling crediteals");
        assertPageIsOpen(new HomePage());
    }

    public void logout() {
     LoginPage loginPage = new LoginPage();
     loginPage.logout();
    }
}
