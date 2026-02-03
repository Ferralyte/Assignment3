package edu.aitu.oop3.db.database;

import java.sql.Connection;
import java.sql.SQLException;

public class PostgresDB implements IDB {
    @Override
    public Connection getConnection() {

        Connection conn = DatabaseConnection.getInstance().getConnection();

        if (conn == null) {
            System.err.println("Error: Connection is null. Check console for previous errors.");
        }
        return conn;
    }
}