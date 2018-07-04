package org.jaggy.gold.util;


import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Basic Logging handling class
 * @author Quirkylee
 */
public class Logging {
    /**
     * Get the java logger
     */
    private static final Logger log = Logger.getLogger("JaggyGold");

    /**
     * Sends a info level message to the console
     * @param str
     */
    public void info(String str) {
        Logging.log.log(Level.INFO, "[JaggyGold] {0}", str);
    }

    /**
     * Sends a severe level message to the console
     * @param str
     */
    public void severe(String str) {
        Logging.log.log(Level.SEVERE, "[JaggyGold] {0}", str);
    }

    /**
     * Sends a fine level message to the console
     * @param str
     */
    public void fine(String str) {
        Logging.log.log(Level.FINE, "[JaggyGold] {0}", str);
    }

    /**
     * Logs an error event to the console
     * @param Level
     * @param str
     */
    public void log(Level Level, String str) {
        Logging.log.log(Level, "[JaggyGold] {0}", str);
    }

    /**
     * Logs an error event to the console with a stacktrace
     * @param Level
     * @param str
     * @param ex
     */
    public void log(Level Level, String str, Throwable ex) {
        Logging.log.log(Level,"[JaggyGold] " +str, ex);
    }
}