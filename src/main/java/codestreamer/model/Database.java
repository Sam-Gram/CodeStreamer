package codestreamer.model;

import codestreamer.CodeStreamerContextListener;

import java.sql.*;

/**
 * A simple database management class. On it's construction, it attempt to make a connection
 * to the database. This class gets loaded into the servlet container, so that all servlets
 * can have access to it.
 *
 * @see CodeStreamerContextListener
 */
public class Database {

    /**
     * Holds a connection to the database.
     */
    private Connection conn;

    /**
     * codestreamer.model.Database's URL.
     */
    private String url;

    /**
     * A username to connect to the database with.
     */
    private String username;

    /**
     * A password to connect to the database with.
     */
    private String password;

    /**
     * Attempts to make a connection to the database.
     */
    public Database() {
        // Are we running on Openshift?
        if (System.getenv("OPENSHIFT_MYSQL_DB_URL") == null) {
            url = "jdbc:mysql://localhost:3306/codestreamer";
            username = "root";
            password = "";
        } else {
            String url = System.getenv("OPENSHIFT_MYSQL_DB_URL");
            String username = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
            String password = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return A connection to the database.
     */
    public Connection getConnection() {
        return this.conn;
    }

    /**
     * Runs a SQL query against the database.
     * @param sql The query to execute.
     * @return A dataset containing the results of the executed query.
     * @throws SQLException
     */
    public ResultSet runSql(String sql) throws SQLException {
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(sql);
    }
}
