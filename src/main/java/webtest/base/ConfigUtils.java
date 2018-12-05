package webtest.base;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ConfigUtils {

    public static String getPCName() {
        String hostname = null;
        try {
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
        return hostname;
    }
}
