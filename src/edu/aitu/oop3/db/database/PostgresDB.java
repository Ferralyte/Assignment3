package edu.aitu.oop3.db.database;

import edu.aitu.oop3.db.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;

public class PostgresDB implements IDB {
    @Override
    public Connection getConnection() {
        try {
            return DatabaseConnection.getConnection(); // ✅ uses Supabase credentials
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to database", e);
        }
    }
}