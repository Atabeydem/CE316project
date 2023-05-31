package com.example.ce316_project_test;
import java.sql.*;

public class ProjectManager {
    private Connection conn;

    public ProjectManager(String dbName) throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlite:" + dbName);
        createTables();
    }

    private void createTables() throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS configurations (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "description TEXT)");
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS results (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "config_id INTEGER, " +
                "result_data BLOB, " +
                "FOREIGN KEY(config_id) REFERENCES configurations(id))");
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS submitted_files (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "config_id INTEGER, " +
                "file_data BLOB, " +
                "FOREIGN KEY(config_id) REFERENCES configurations(id))");
        stmt.close();
    }

    public void addConfiguration(String name, String description) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO configurations (name, description) VALUES (?, ?)");
        stmt.setString(1, name);
        stmt.setString(2, description);
        stmt.executeUpdate();
        stmt.close();
    }

    public void editConfiguration(int configId, String newName, String newDescription) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE configurations SET name = ?, description = ? WHERE id = ?");
        stmt.setString(1, newName);
        stmt.setString(2, newDescription);
        stmt.setInt(3, configId);
        stmt.executeUpdate();
        stmt.close();
    }

    public void removeConfiguration(int configId) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM configurations WHERE id = ?");
        stmt.setInt(1, configId);
        stmt.executeUpdate();
        stmt.close();
    }

    public void addResult(int configId, byte[] resultData) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO results (config_id, result_data) VALUES (?, ?)");
        stmt.setInt(1, configId);
        stmt.setBytes(2, resultData);
        stmt.executeUpdate();
        stmt.close();
    }

    public void addSubmittedFile(int configId, byte[] fileData) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO submitted_files (config_id, file_data) VALUES (?, ?)");
        stmt.setInt(1, configId);
        stmt.setBytes(2, fileData);
        stmt.executeUpdate();
        stmt.close();
    }

    public ResultSet getConfigurations() throws SQLException {
        Statement stmt = conn.createStatement();
        return stmt.executeQuery("SELECT * FROM configurations");
    }

    public ResultSet getResults(int configId) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM results WHERE config_id = ?");
        stmt.setInt(1, configId);
        return stmt.executeQuery();
    }

    public ResultSet getSubmittedFiles(int configId) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM submitted_files WHERE config_id = ?");
        stmt.setInt(1, configId);
        return stmt.executeQuery();
    }

    public void close() throws SQLException {
        conn.close();
    }
}

