package com.s11group2.profiling_database.Model;

import java.sql.*;
import java.time.LocalDate;


public class DatabaseManager {
    private Connection conn;
    private String url = "jdbc:sqlite:units.db";

    public DatabaseManager() throws SQLException {
        this.conn = DriverManager.getConnection(url);
    }

    public Connection getConnection() {
        return this.conn;
    }

    public void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    public void createTables() throws SQLException {
        createHouseholdTable();
        createMemberTable();
        conn.createStatement().execute("PRAGMA foreign_keys = ON");
    }

    private void createHouseholdTable() throws SQLException {
        String createTableSQL = "CREATE TABLE Households (" +
                "buildingNum integer not null, " +
                "unitNum integer not null, " +
                "monthlyExpenditure real, " +
                "monthlyAmortization real, " +
                "yearOfResidence integer, " +
                "constraint pk_building_unit primary key (buildingNum, unitNum) " +
                ");";
        Statement stmt = conn.createStatement();
        stmt.execute(createTableSQL);
    }

    private void createMemberTable() throws SQLException {
        String createTableSQL = "CREATE TABLE Members (" +
                "lastName varchar(255), " +
                "firstName varchar(255), " +
                "middleName varchar(255), " +
                "gender varchar(255), " +
                "birthday date, " +
                "healthStatus varchar(255), " +
                "pwdType varchar(255), " +
                "isSeniorCitizen integer, " +
                "civilStatus varchar(255), " +
                "contactNumber integer, " +
                "highestEducationalAttainment varchar(255), " +
                "occupation varchar(255), " +
                "monthlyIncome real, " +
                "isMainRespondent integer, " +
                "buildingNum integer, " +
                "unitNum integer, " +
                "constraint buildingUnitNum_fk foreign key (buildingNum, unitNum) references Households(buildingNum, unitNum) " +
                ");";
        Statement stmt = conn.createStatement();
        stmt.execute(createTableSQL);
    }
    
    

    public void insertHousehold(int buildingNum, int unitNum, double monthlyExpenditure, double monthlyAmortization, int yearOfResidence) throws SQLException {
        String insertSQL = "INSERT INTO Households (buildingNum, unitNum, monthlyExpenditure, monthlyAmortization, yearOfResidence) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(insertSQL);
        pstmt.setInt(1, buildingNum);
        pstmt.setInt(2, unitNum);
        pstmt.setDouble(3, monthlyExpenditure);
        pstmt.setDouble(4, monthlyAmortization);
        pstmt.setInt(5, yearOfResidence);
        pstmt.executeUpdate();
    }

    public void insertMember(String lastName, String firstName, String middleName, String gender, LocalDate birthday, String healthStatus, String pwdType, Integer isSeniorCitizen, String civilStatus, String contactNumber, String highestEducationalAttainment, String occupation, Double monthlyIncome, Integer isMainRespondent, Integer buildingNum, Integer unitNum) throws SQLException {
        String insertSQL = "INSERT INTO Members (lastName, firstName, middleName, gender, birthday, healthStatus, pwdType, isSeniorCitizen, civilStatus, contactNumber, highestEducationalAttainment, occupation, monthlyIncome, isMainRespondent, buildingNum, unitNum) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(insertSQL);
        pstmt.setString(1, lastName);
        pstmt.setString(2, firstName);
        pstmt.setString(3, middleName);
        pstmt.setString(4, gender);
        pstmt.setObject(5, birthday);
        pstmt.setString(6, healthStatus);
        pstmt.setString(7, pwdType);
        pstmt.setInt(8, isSeniorCitizen);
        pstmt.setString(9, civilStatus);
        pstmt.setString(10, contactNumber);
        pstmt.setString(11, highestEducationalAttainment);
        pstmt.setString(12, occupation);
        pstmt.setDouble(13, monthlyIncome);
        pstmt.setInt(14, isMainRespondent);
        pstmt.setInt(15, buildingNum);
        pstmt.setInt(16, unitNum);
        pstmt.executeUpdate();
    }

    public void displayDatabase(Connection conn, String tableName, String orderCondition) throws SQLException { //have this return the resultset instead so its passable for rendering to the frontend
        String selectSQL = "SELECT * FROM " + tableName + " ORDER BY " + orderCondition;
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(selectSQL);

        //sample displaying
        System.out.println("\nRESULTS FOR " + tableName);
        while(rs.next()) {
            System.out.println("Entry: " + rs.getString("lastName") + ", " + rs.getString("firstName"));
        }

        //return rs
    }
    
    //TODO: add edit/delete


    
}
