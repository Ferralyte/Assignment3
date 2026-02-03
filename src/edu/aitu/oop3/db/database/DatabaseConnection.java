package edu.aitu.oop3.db.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    private String url, user, password;

    private DatabaseConnection() {
        loadConfigAndConnect();
    }

    private void loadConfigAndConnect() {
        Properties props = new Properties();
        InputStream is = null;
        try {

            is = getClass().getClassLoader().getResourceAsStream("resources/config.properties");

            if (is == null) {
                is = getClass().getClassLoader().getResourceAsStream("config.properties");
            }

            if (is == null) {
                throw new RuntimeException("CRITICAL: config.properties not found on classpath.");
            }

            props.load(is);

            this.url = props.getProperty("db.url");
            this.user = props.getProperty("db.user");
            this.password = props.getProperty("db.password");

            Class.forName("org.postgresql.Driver");

            this.connection = DriverManager.getConnection(url, user, password);
            System.out.println("Database connected successfully.");

        } catch (Exception e) {
            throw new RuntimeException("DB Init Failed: " + e.getMessage());
        } finally {
            if (is != null) try { is.close(); } catch (IOException ignored) {}
        }
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        try {

            if (connection == null || connection.isClosed()) {
                this.connection = DriverManager.getConnection(url, user, password);
            }
        } catch (SQLException e) {
            System.err.println("Reconnection failed: " + e.getMessage());
        }
        return connection;
    }
}