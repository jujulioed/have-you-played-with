package com.jujulioed;

import java.sql.*;

public class HistoryDB {
    private final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS registers (" +
            "id integer PRIMARY KEY," +
            "register text NOT NULL," +
            "CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP);";
    private final String INSERT_SQL = "Insert INTO registers (register) VALUES(?)";

    private Connection conn;

    public HistoryDB(String dbUrl) {
        try {
            conn = DriverManager.getConnection(dbUrl);
            Statement stmt = conn.createStatement();
            stmt.execute(CREATE_TABLE_SQL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int registerData(String data) {
        try {
            PreparedStatement pstmt = conn.prepareStatement(INSERT_SQL);
            pstmt.setString(1, data);
            pstmt.executeUpdate();
            System.out.println("Data inserted");
            return 1;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return  0;
        }
    }
}
