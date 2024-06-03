package cssweng.grp2;

import java.sql.*;
import java.time.LocalDate;

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
            ResultSet rs = meta.getTables(null, null, "Households", null); //Change to main table name as needed
            if (rs.next()) {
                System.out.println("Table already exists!");
            } else {
                System.out.println("Database not found, creating database...");
                try {
                    createHouseholdTable(conn);
                    createMemberTable(conn);
                    conn.createStatement().execute("PRAGMA foreign_keys = ON");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
                System.out.println("Database Created!");
            }

            System.out.println();
            System.out.println("Inserting Data");
            try {

                LocalDate birthday = LocalDate.of(2004, 4, 2);

                insertHousehold(conn, 2, 3, 19.99, 19.99, 2020);
                insertMember(conn, "Cruz", "Juan Dela", "Yu", "Male", birthday, "Healthy", "Not PWD", 0, "Single", "0917 111 1111", "College", "Student", 0.0, 1, 2, 3);

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }

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


    private static void insertHousehold(Connection conn, int buildingNum, int unitNum, double monthlyExpenditure, double monthlyAmortization, int yearOfResidence) throws SQLException {
        String insertSQL = "INSERT INTO " + "Households" + "(buildingNum, unitNum, monthlyExpenditure, monthlyAmortization, yearOfResidence) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(insertSQL);
        pstmt.setInt(1, buildingNum);
        pstmt.setInt(2, unitNum);
        pstmt.setDouble(3, monthlyExpenditure);
        pstmt.setDouble(4, monthlyAmortization);
        pstmt.setInt(5, yearOfResidence);
        pstmt.executeUpdate();
    }

    private static void insertMember(Connection conn, String lastName, String firstName, String middleName, 
                                    String gender, LocalDate birthday, String healthStatus, String pwdType, 
                                    Integer isSeniorCitizen, String civilStatus, String contactNumber, String highestEducationalAttainment, 
                                    String occupation, Double monthlyIncome, Integer isMainRespondent, Integer buildingNum, Integer unitNum) throws SQLException {
        String insertSQL = "INSERT INTO " + "Members" + "(lastName, firstName, middleName, gender, birthday, healthStatus, pwdType, isSeniorCitizen, civilStatus, contactNumber, highestEducationalAttainment, occupation, monthlyIncome, isMainRespondent, buildingNum, unitNum) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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

    private static void  createHouseholdTable(Connection conn) throws SQLException {
        String createTablesql = "" + 
                "CREATE TABLE " + "Households" + " " +
                "( " +
                "buildingNum integer not null, " +
                "unitNum integer not null, " +
                "monthlyExpenditure real, " +
                "monthlyAmortization real, " +
                "yearOfResidence integer, " +
                "constraint pk_building_unit primary key (buildingNum, unitNum) " +
                "); " +
                "";
        Statement stmt = conn.createStatement();
        stmt.execute(createTablesql);
    }

    private static void  createMemberTable(Connection conn) throws SQLException {
        String createTablesql = "" + 
                "CREATE TABLE " + "Members" + " " +
                "( " +
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
                "); " +
                "";
        Statement stmt = conn.createStatement();
        stmt.execute(createTablesql);
    }
}
