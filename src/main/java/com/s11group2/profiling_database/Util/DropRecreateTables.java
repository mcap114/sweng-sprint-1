package com.s11group2.profiling_database.Util;

import com.s11group2.profiling_database.Model.DatabaseManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DropRecreateTables {

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            DatabaseManager dbManager = new DatabaseManager();
            dbManager.dropTables(conn);
            dbManager.createTables();

            insertSampleData(conn);

            System.out.println("Tables dropped, recreated, and sample data inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Connection getConnection() {
        String url = "jdbc:sqlite:units.db";
        try {
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void insertSampleData(Connection conn) throws SQLException {
        String insertHouseholdSQL = "INSERT INTO Households (buildingNum, unitNum, monthlyExpenditure, monthlyAmortization, yearOfResidence) VALUES " +
                "(1, 101, 1500.00, 500.00, 2010), " +
                "(1, 102, 1600.00, 600.00, 2011), " +
                "(2, 201, 1700.00, 700.00, 2012), " +
                "(2, 202, 1800.00, 800.00, 2013), " +
                "(3, 301, 1900.00, 900.00, 2014)";

        String insertMemberSQL = "INSERT INTO Members (lastName, firstName, middleName, gender, birthday, healthStatus, pwdType, isSeniorCitizen, civilStatus, contactNumber, highestEducationalAttainment, occupation, monthlyIncome, isMainRespondent, buildingNum, unitNum, profileImagePath) VALUES " +
                "('Smith', 'John', 'A.', 'M', '1980-01-01', 'Healthy', 'Not PWD', 0, 'Married', '0912 345 6789', 'collegeGraduateOrHigher', 'Engineer', 3000.00, 1, 1, 101, '/path/to/image1'), " +
                "('Doe', 'Jane', 'B.', 'F', '1985-02-02', 'Healthy', 'Not PWD', 0, 'Married', '0923 456 7890', 'collegeGraduateOrHigher', 'Teacher', 3200.00, 1, 1, 102, '/path/to/image2'), " +
                "('Brown', 'Michael', 'C.', 'M', '1975-03-03', 'Healthy', 'Not PWD', 0, 'Single', '0934 567 8901', 'collegeGraduateOrHigher', 'Scientist', 4000.00, 1, 2, 201, '/path/to/image3'), " +
                "('Johnson', 'Emily', 'D.', 'F', '1990-04-04', 'Healthy', 'Not PWD', 0, 'Single', '0945 678 9012', 'collegeGraduateOrHigher', 'Nurse', 2800.00, 1, 2, 202, '/path/to/image4'), " +
                "('Williams', 'David', 'E.', 'M', '1988-05-05', 'Healthy', 'Not PWD', 0, 'Married', '0956 789 0123', 'collegeGraduateOrHigher', 'Developer', 3500.00, 1, 3, 301, '/path/to/image5')";

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(insertHouseholdSQL);
            stmt.executeUpdate(insertMemberSQL);
        }
    }
}
