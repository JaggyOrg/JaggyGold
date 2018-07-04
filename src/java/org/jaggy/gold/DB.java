package org.jaggy.gold;

import java.sql.*;

/**
 * Database library class
 */
public class DB {
    /**
     * Reference pointer to parent
     */
    private final Main plugin;
    /**
     * Field to pass the table prefix along
     */
    private final String Prefix;
    /**
     * Container to hold the db connection
     */
    private Connection db;

    /**
     * Class constructor
     * @param main Passes the parent class to this class
     */
    public DB(Main main) {
        plugin = main;

        String mysqlHost = plugin.config.getMysqlHost();
        String mysqlUser = plugin.config.getMysqlUser();
        String DBName = plugin.config.getDBName();
        String mysqlPass = plugin.config.getMysqlPass();
        int mysqlPort = plugin.config.getMysqlPort();
        Prefix = plugin.config.getPrefix();
        boolean useSSL = plugin.config.useSSL();

        try {
            //tell java to load this driver
            Class.forName("com.mysql.jdbc.Driver");
            //connect to database
            db = DriverManager.getConnection("jdbc:mysql://"
                    + mysqlHost + ":" + mysqlPort + "/" + DBName + "?"
                    + "user=" + mysqlUser + "&useSSL=" + useSSL + "&password=" + mysqlPass);
        } catch (ClassNotFoundException | SQLException ex) {
            plugin.log.severe(ex.getMessage());
        }
        if (db != null) {
            plugin.isLoaded = true;
        }
    }

    /**
     * Method to run a db query
     * @param query query String
     * @return result
     */
    public ResultSet query(String query) {
        ResultSet result = null;
        try {
            Statement statement = db.createStatement();
            String[] type = query.split(" ", 2);
            if (type[0].equalsIgnoreCase("SELECT")) {
                result = statement.executeQuery(query);
            } else {
                statement.executeUpdate(query);
            }
        } catch (SQLException ex) {
            plugin.log.severe(ex.getMessage());
        }
        return result;
    }

    private void createDB() {
        query("CREATE TABLE IF NOT EXISTS " + Prefix + "Main (\n"
                + "UID INT(64) NOT NULL AUTO_INCREMENT,\n"
                + "Player VARCHAR(60),\n"
                + "Gold INT(64) DEFAULT 0,\n"
                + "PRIMARY KEY (UID)) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;"
        );
    }
}
