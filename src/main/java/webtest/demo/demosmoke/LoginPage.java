package webtest.demo.demosmoke;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;
import webtest.base.AbstractDemoPage;
import webtest.base.PropertiesData;

import java.util.List;

import static java.lang.String.format;

public class LoginPage extends AbstractDemoPage {

    private static final String NICKNAME_FIELD_ID = "user_login";
    private static final String PASSWORD_FIELD_ID = "user_pass";
    private static final String LOGIN_BUTTON_ID = "wp-submit";
    private static final String USER_PANEL_XPATH= "//div[contains(@id,'wp_sidebarlogin')]/h3";
    private static final String STAY_LOGGED_CHECKBOX_ID = "rememberme";
    private static final String LOGOUT_BUTTON_XPATH ="//li[@class='odhlaseni-link']/a";

    @Override
    protected void addPageIsNotOpenReasons(List<String> reasons) {
        if (!isElementPresentById(NICKNAME_FIELD_ID)) {
            reasons.add(format(ID_ELEMENT_IS_NOT_PRESENT, NICKNAME_FIELD_ID));
        }

        if (!isElementPresentById(PASSWORD_FIELD_ID)) {
            reasons.add(format(XPATH_ELEMENT_IS_NOT_PRESENT, PASSWORD_FIELD_ID));
        }

    }

    public void login() {
        setText(getNickNameFieldElement(), PropertiesData.getUsername());
        setText(getPasswordFieldElement(), PropertiesData.getPassword());
        performClick(getConfirmLoginButtonElement());
    }

    public void UncheckStayLogged() {
        performClick(getRememberMeCheckBoxElement());
    }

    public void logout() {
        performClick(getLogoutButtonElement());
    }

    private WebElement getNickNameFieldElement()  {
        return findElementById(NICKNAME_FIELD_ID);
    }

    private WebElement getPasswordFieldElement() {
        return findElementById(PASSWORD_FIELD_ID);
    }

    public boolean isUserLogged() {
      return  isElementPresentByXpath(USER_PANEL_XPATH);
    }

    private WebElement getConfirmLoginButtonElement() {
        return findElementById(LOGIN_BUTTON_ID);
    }

    private WebElement getRememberMeCheckBoxElement()  {return findElementById(STAY_LOGGED_CHECKBOX_ID);}

    private WebElement getLogoutButtonElement () {return findElementByXpath(LOGOUT_BUTTON_XPATH);}

}
