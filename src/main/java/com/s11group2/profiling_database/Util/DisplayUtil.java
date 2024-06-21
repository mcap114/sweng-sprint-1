package com.s11group2.profiling_database.Util;

import java.sql.*;

/**
 * Utility class for displaying table contents to the terminal.
 */
public class DisplayUtil {

    /**
     * Displays the contents of a specified table to the terminal.
     *
     * @param conn      the connection to the database
     * @param tableName the name of the table to display
     */
    public static void displayTableContents(Connection conn, String tableName) {
        String query = "SELECT * FROM " + tableName;
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Print column names
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(metaData.getColumnName(i) + "\t");
            }
            System.out.println();

            // Print rows
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rs.getString(i) + "\t");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the contents of a specified table to the terminal.
     *
     * @param rs      the set of records to be displayed
     */
    public static void displayTableContents(ResultSet rs) throws SQLException {
        while (rs.next()) {
            System.out.println("Building Number: " + rs.getInt("buildingNum"));
            System.out.println("Unit Number: " + rs.getInt("unitNum"));
            System.out.println("Monthly Expenditure: " + rs.getDouble("monthlyExpenditure"));
            System.out.println("Monthly Amortization: " + rs.getDouble("monthlyAmortization"));
            System.out.println("Year of Residence: " + rs.getInt("yearOfResidence"));
            System.out.println("---------------------------------------");
        }
    }


}
