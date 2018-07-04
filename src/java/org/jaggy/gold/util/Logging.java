package org.jaggy.gold.util;


import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Basic Logging handling class
 * @author Quirkylee
 */
public class Logging {
    private static final Logger log = Logger.getLogger("JaggyGold");

    public void info(String str) {
        Logging.log.log(Level.INFO, "[JaggyGold] {0}", str);
    }

    public void severe(String str) {
        Logging.log.log(Level.SEVERE, "[JaggyGold] {0}", str);
    }
    public void fine(String str) {
        Logging.log.log(Level.FINE, "[JaggyGold] {0}", str);
    }
    public void log(Level Level, String str) {
        Logging.log.log(Level, "[JaggyGold] {0}", str);
    }
    public void log(Level Level, String str, Throwable ex) {
        Logging.log.log(Level,"[JaggyGold] " +str, ex);
    }
}