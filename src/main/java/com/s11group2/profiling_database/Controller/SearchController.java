package com.s11group2.profiling_database.Controller;

import com.s11group2.profiling_database.Model.DatabaseManager;
import com.s11group2.profiling_database.Util.DisplayUtil;

import java.sql.*;

/**
 * Controller class containing methods for search functionality.
 */
public class SearchController {

    private DatabaseManager dbManager;
    private DisplayUtil display;

    /**
     * Constructs a new SearchController with the specified DatabaseManager.
     *
     * @param dbManager the DatabaseManager instance used to retrieve connection
     */
    public SearchController(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    /**
     * Searches households by building number and displays results.
     *
     * @param buildingNum the building number to search for
     */
    public void searchByBuilding(int buildingNum) {
        try (Connection conn = dbManager.getConnection()) {
            String query = "SELECT * FROM Households WHERE buildingNum = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, buildingNum);

                try (ResultSet rs = pstmt.executeQuery()) {
                    display.displayTableContents(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Searches households by unit number and displays results.
     *
     * @param unitNum the unit number to search for
     */
    public void searchByUnit(int unitNum) {
        try (Connection conn = dbManager.getConnection()) {
            String query = "SELECT * FROM Households WHERE unitNum = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, unitNum);

                try (ResultSet rs = pstmt.executeQuery()) {
                    display.displayTableContents(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
