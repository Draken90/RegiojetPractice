package cz.csob.oneib.common.util

import org.apache.commons.lang3.RandomStringUtils
import java.text.Collator
import java.util.*
import java.util.concurrent.TimeUnit

object TestUtils {
    /**
     * Sleep without exception
     */
    @JvmStatic
    fun sleep(millis: Long) {
        try {
            Thread.sleep(millis)
        } catch (ignored: InterruptedException) {
        }
    }

    /**
     * Sleep without exception
     */

    @JvmStatic
    fun sleep(value: Long, unit: TimeUnit?) {
        try {
            Thread.sleep(TimeUnit.MILLISECONDS.convert(value, unit))
        } catch (ignored: InterruptedException) {
        }
    }

    @JvmStatic
    fun waitForJavaScript(millis: Long) {
        sleep(millis)
    }

    @JvmStatic
    fun waitForJavaScript(value: Long, unit: TimeUnit?) {
        sleep(value, unit)
    }

    @JvmStatic
    fun getNumberFromAccountBalance(balance: String): Int {
        return balance.substring(0, balance.indexOf(',')).replace(" ", "").toInt()
    }

    /**
     * Používá se, protože defaulní sorter/komparátor neumí řadit české znaky
     * Příklad:
     *
     *     fun main() {
     *        val p = arrayListOf("Cuco", "AAA", "Čučo", "Čad", "R", "á", "a", "Á")
     *        p.sort()
     *        p.forEach {
     *          System.out.println(it)
     *        }
     *    }
     *
     * vrátí špatně:
     *
     *      AAA,Cuco,R,a,Á,á,Čad,Čučo
     *
     * @return Czech Collator
     */
    @JvmStatic
    val czechSorter: Collator
        get() {
            val coll = Collator.getInstance(Locale("cs"))
            coll.strength = Collator.PRIMARY
            return coll
        }

    /**
     * Vygeneruje random VS pro platby ve 1IB
     */
    fun randomVariableSymbol(): String = "55" + RandomStringUtils.randomNumeric(7, 7)

    /**
     * Vygeneruje random payer's reference number (tzv. "Reference plátce") pro platby ve 1IB
     */
    fun randomPayersReference(): String = "REF-" + randomVariableSymbol()

}


