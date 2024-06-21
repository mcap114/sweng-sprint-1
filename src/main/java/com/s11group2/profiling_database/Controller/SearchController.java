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

    public void search(String searchTerm) {
        try (Connection conn = dbManager.getConnection()) {
            String query = buildDynamicQuery(conn, searchTerm);
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                for (int i = 1; i <= 10; i++) {
                    pstmt.setString(i, "%" + searchTerm + "%");
                }
                try (ResultSet rs = pstmt.executeQuery()) {
                    DisplayUtil.displayTableContents(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String buildDynamicQuery(Connection conn, String searchTerm) throws SQLException {
        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet rsColumns = metaData.getColumns(null, null, "Households", null);

        StringBuilder query = new StringBuilder("SELECT * FROM Households WHERE ");
        boolean first = true;
        while (rsColumns.next()) {
            String columnName = rsColumns.getString("COLUMN_NAME");
            if (!first) {
                query.append(" OR ");
            }
            query.append(columnName).append(" LIKE ?");
            first = false;
        }
        return query.toString();
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
