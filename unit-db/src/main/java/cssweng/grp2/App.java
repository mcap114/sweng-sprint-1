package cssweng.grp2;

import java.sql.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Connection conn = null;
        String url = "jdbc:sqlite:units.db";

        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Database Connected!");
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet rs = meta.getTables(null, null, "Units", null); //Change to main table name as needed
            if (rs.next()) {
                System.out.println("Table already exists!");
            } else {
                System.out.println("Database not found, creating database...");
                try {
                    createTable(conn, "Units");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
                System.out.println("Database Created!");
            }

            System.out.println();
            System.out.println("Inserting Data");
            try {

                insertRecord(conn, "Units", 2, 3, "Juan Cruz", 22, 1, "Carpenter"); //insert records here

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }

            System.out.println();
            System.out.println("Displaying DB");
            displayDatabase(conn, "Units");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            try {
                conn.close();
                System.out.println("Connection closed!");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }

    private static void displayDatabase(Connection conn, String tableName) throws SQLException {
        String selectSQL = "SELECT * from " + tableName;
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(selectSQL);

        System.out.println("----- " + tableName + " -----");
        while(rs.next()) {
            System.out.print("Record: " + rs.getInt("buildingNum") + ", ");
            System.out.print(rs.getInt("unitNum") + ", ");
            System.out.print(rs.getString("name") + ", ");
            System.out.print(rs.getInt("age") + ", ");
            System.out.print(rs.getInt("birthday") + ", ");
            System.out.println(rs.getString("occupation"));
        }
        System.out.println("--------------------------");
    }

    private static void insertRecord(Connection conn, String tableName, int buildingNum, int unitNum, String name, int age, int birthday, String occupation) throws SQLException {
        String insertSQL = "INSERT INTO " + tableName + "(buildingNum, unitNum, name, age, birthday, occupation) VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(insertSQL);
        pstmt.setInt(1, buildingNum);
        pstmt.setInt(2, unitNum);
        pstmt.setString(3, name);
        pstmt.setInt(4, age);
        pstmt.setInt(5, birthday); //might not be the best way to store this
        pstmt.setString(6, occupation);
        pstmt.executeUpdate();
    }

    private static void  createTable(Connection conn, String tableName) throws SQLException {
        String createTablesql = "" + 
                "CREATE TABLE " + tableName + " " +
                "( " +
                "buildingNum integer, " +
                "unitNum integer, " +
                "name varchar(255), " +
                "age integer, " +
                "birthday integer, " +
                "occupation varchar(255) " +
                "); " +
                "";
        Statement stmt = conn.createStatement();
        stmt.execute(createTablesql);
    }
}
