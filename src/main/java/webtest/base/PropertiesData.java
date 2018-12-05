package webtest.base;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesData {

    private static Properties getPropertiesFile() {
        Properties prop = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream("data.properties");
        try {
            prop.load(stream);
        } catch (IOException e) {
            e.fillInStackTrace();
        }
        return prop;
    }
    public static String getUsername() {
        return getPropertiesFile().getProperty("data.login.nickname");
    }

    public static String getPassword() {
        return getPropertiesFile().getProperty("data.login.password");
    }

    public static String getPageLoadTimeout() {
        return getPropertiesFile().getProperty("load.timeout");
    }
}
