package report.realtime.datahandler;

import org.testng.IClass;
import org.testng.ISuite;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class DataMap {

    volatile public static Map<ISuite, Integer> suiteMap = new HashMap<ISuite, Integer>();

    volatile public static Map<IClass, Integer> testClassMap = new HashMap<IClass, Integer>();

    volatile public static Set<DataSuite> suiteSet = new ConcurrentSkipListSet<>();
}
