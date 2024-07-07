package com.s11group2.profiling_database.Model;

import java.sql.*;
import java.time.LocalDate;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


/**
 * The DatabaseManager class provides methods to manage the SQLite database,
 * including creating tables, inserting records, and displaying data.
 */
public class DatabaseManager {
    private Connection conn;
    private String url = "jdbc:sqlite:units.db";

    /**
     * Constructs a DatabaseManager and establishes a connection to the SQLite database.
     *
     * @throws SQLException if a database access error occurs
     */
    public DatabaseManager() throws SQLException {
        this.conn = DriverManager.getConnection(url);
    }

    /**
     * Returns the current database connection.
     *
     * @return the current Connection object
     */
    public Connection getConnection() {
        return this.conn;
    }

    /**
     * Closes the current database connection.
     *
     * @throws SQLException if a database access error occurs
     */
    public void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    /**
     * Creates the necessary tables for the database.
     *
     * @throws SQLException if a database access error occurs
     */
    public void createTables() throws SQLException {
        createHouseholdTable();
        createMemberTable();
        createPetsTable();
        conn.createStatement().execute("PRAGMA foreign_keys = ON");
    }

    /**
     * Creates the Household table in the database.
     *
     * @throws SQLException if a database access error occurs
     */
    private void createHouseholdTable() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Households (" +
                "buildingNum integer not null, " +
                "unitNum integer not null, " +
                "monthlyExpenditure real not null, " +
                "monthlyAmortization real not null, " +
                "yearOfResidence integer not null, " +
                "constraint pk_building_unit primary key (buildingNum, unitNum) " +
                ");";
        Statement stmt = conn.createStatement();
        stmt.execute(createTableSQL);
    }

    /**
     * Creates the Member table in the database.
     *
     * @throws SQLException if a database access error occurs
     */
    private void createMemberTable() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Members (" +
                "lastName varchar(255) not null, " +
                "firstName varchar(255) not null, " +
                "middleName varchar(255) not null, " +
                "gender varchar(255) not null, " +
                "birthday date not null, " +
                "healthStatus varchar(255) not null, " +
                "pwdType varchar(255) not null, " +
                "isSeniorCitizen integer not null, " +
                "civilStatus varchar(255) not null, " +
                "contactNumber varchar(255) not null, " +
                "contactNumber varchar(255) not null, " +
                "highestEducationalAttainment varchar(255) not null, " +
                "occupation varchar(255) not null, " +
                "monthlyIncome real not null, " +
                "isMainRespondent integer not null, " +
                "buildingNum integer not null, " +
                "unitNum integer not null, " +
                "profileImagePath varchar(255) not null, " +
                "constraint buildingUnitNumMembers_fk foreign key (buildingNum, unitNum) references Households(buildingNum, unitNum) " +
                ");";
        Statement stmt = conn.createStatement();
        stmt.execute(createTableSQL);
    }

    /**
     * Creates the Pets table in the database.
     *
     * @throws SQLException if a database access error occurs
     */
    private void createPetsTable() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Pets (" +
                "petName varchar(255) not null, " +
                "petBreed varchar(255) not null, " +
                "buildingNum integer not null, " +
                "unitNum integer not null, " +
                "constraint buildingUnitNumPets_fk foreign key (buildingNum, unitNum) references Households(buildingNum, unitNum) " +
                ");";
        Statement stmt = conn.createStatement();
        stmt.execute(createTableSQL);
    }

    /**
     * Inserts a record into the Household table if it doesn't already exist.
     *
     * @param buildingNum the building number
     * @param unitNum the unit number
     * @param monthlyExpenditure the monthly expenditure
     * @param monthlyAmortization the monthly amortization
     * @param yearOfResidence the year of residence
     * @throws SQLException if a database access error occurs
     * @throws IllegalArgumentException if the household already exists
     */
    public void insertHousehold(int buildingNum, int unitNum, double monthlyExpenditure, double monthlyAmortization, int yearOfResidence) throws SQLException {
        if (householdExists(buildingNum, unitNum)) {
            throw new IllegalArgumentException("Household with building number " + buildingNum + " and unit number " + unitNum + " already exists.");
        }

        String insertSQL = "INSERT INTO Households (buildingNum, unitNum, monthlyExpenditure, monthlyAmortization, yearOfResidence) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setInt(1, buildingNum);
            pstmt.setInt(2, unitNum);
            pstmt.setDouble(3, monthlyExpenditure);
            pstmt.setDouble(4, monthlyAmortization);
            pstmt.setInt(5, yearOfResidence);
            pstmt.executeUpdate();
        }
    }

    /**
     * Inserts a record into the Member table.
     *
     * @param lastName the last name
     * @param firstName the first name
     * @param middleName the middle name
     * @param gender the gender
     * @param birthday the birthday
     * @param healthStatus the health status
     * @param pwdType the type of PWD
     * @param isSeniorCitizen indicates if the person is a senior citizen
     * @param civilStatus the civil status
     * @param contactNumber the contact number
     * @param highestEducationalAttainment the highest educational attainment
     * @param occupation the occupation
     * @param monthlyIncome the monthly income
     * @param isMainRespondent indicates if the person is the main respondent
     * @param buildingNum the building number
     * @param unitNum the unit number
     * @throws SQLException if a database access error occurs
     */
    public void insertMember(String lastName, String firstName, String middleName, String gender, LocalDate birthday, String healthStatus, String pwdType, Integer isSeniorCitizen, String civilStatus, String contactNumber, String highestEducationalAttainment, String occupation, Double monthlyIncome, Integer isMainRespondent, Integer buildingNum, Integer unitNum, File profileImage) throws SQLException, IOException {
        String insertSQL = "INSERT INTO Members (lastName, firstName, middleName, gender, birthday, healthStatus, pwdType, isSeniorCitizen, civilStatus, contactNumber, highestEducationalAttainment, occupation, monthlyIncome, isMainRespondent, buildingNum, unitNum, profileImagePath) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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

        String inputPath = profileImage.getCanonicalPath();

        try {
            File temp = File.createTempFile("img", ".jpg", new File("../../../../main/resources/"));
            String profileImagePath = temp.getCanonicalPath();

            convertImage(inputPath, profileImagePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        pstmt.setString(17, profileImagePath);
        pstmt.executeUpdate();
    }

    public void convertImage(String inputPath, String outputPath) throws IOException {
        BufferedImage image = ImageIO.read(new File(inputPath));
		BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.TYPE_INT_RGB);
		newImage.createGraphics().drawImage(image, 0, 0, Color.white, null);
		ImageIO.write(newImage, "jpg", new File(outputPath));
    }

    /**
     * Inserts a record into the Pets table.
     *
     * @param petName the pet's name
     * @param petBreed the pet's breed
     * @param buildingNum the building number
     * @param unitNum the unit number
     * @throws SQLException if a database access error occurs
     */
    public void insertPet(String petName, String petBreed, String middleName, Integer buildingNum, Integer unitNum) throws SQLException {
        String insertSQL = "INSERT INTO Pets (petName, petBreed, buildingNum, unitNum) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(insertSQL);
        pstmt.setString(1, petName);
        pstmt.setString(2, petBreed);
        pstmt.setInt(3, buildingNum);
        pstmt.setInt(4, unitNum);
        pstmt.executeUpdate();
    }

    public void editHousehold(int buildingNum, int unitNum, String field, Object newValue) throws SQLException {
        String query = "UPDATE Households SET " + field + " = ? WHERE buildingNum = ? AND unitNum = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setObject(1, newValue);
            pstmt.setInt(2, buildingNum);
            pstmt.setInt(3, unitNum);
            pstmt.executeUpdate();
        }
    }

    public void editMember(String lastName, String firstName, String middleName, int buildingNum, int unitNum, String field, Object newValue) throws SQLException {
        String query = "UPDATE Members SET " + field + " = ? WHERE lastName = ? AND firstName = ? AND middleName = ? AND buildingNum = ? AND unitNum = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setObject(1, newValue);
            pstmt.setString(2, lastName);
            pstmt.setString(3, firstName);
            pstmt.setString(4, middleName);
            pstmt.setInt(5, buildingNum);
            pstmt.setInt(6, unitNum);
            pstmt.executeUpdate();
        }
    }

    /**
     * Deletes a household and its associated members from the database.
     *
     * @param buildingNum the building number of the household to delete
     * @param unitNum the unit number of the household to delete
     * @throws SQLException if a database access error occurs
     */
    public void deleteHousehold(int buildingNum, int unitNum) throws SQLException {
        String deleteMembersSQL = "DELETE FROM Members WHERE buildingNum = ? AND unitNum = ?";
        try (PreparedStatement pstmtMembers = conn.prepareStatement(deleteMembersSQL)) {
            pstmtMembers.setInt(1, buildingNum);
            pstmtMembers.setInt(2, unitNum);
            pstmtMembers.executeUpdate();
        }

        //deletes associated members as well
        String deleteHouseholdSQL = "DELETE FROM Households WHERE buildingNum = ? AND unitNum = ?";
        try (PreparedStatement pstmtHousehold = conn.prepareStatement(deleteHouseholdSQL)) {
            pstmtHousehold.setInt(1, buildingNum);
            pstmtHousehold.setInt(2, unitNum);
            pstmtHousehold.executeUpdate();
        }
    }

    public void deleteMember(String lastName, String firstName, String middleName, int buildingNum, int unitNum) throws SQLException {
        String query = "DELETE FROM Members WHERE lastName = ? AND firstName = ? AND middleName = ? AND buildingNum = ? AND unitNum = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, lastName);
            pstmt.setString(2, firstName);
            pstmt.setString(3, middleName);
            pstmt.setInt(4, buildingNum);
            pstmt.setInt(5, unitNum);
            pstmt.executeUpdate();
        }
    }

    public void dropTables(Connection conn) throws SQLException {
    String dropSQL1 = "DROP TABLE IF EXISTS Members";
    String dropSQL2 = "DROP TABLE IF EXISTS Households";
    try (Statement stmt = conn.createStatement()) {
        stmt.executeUpdate(dropSQL1);
        stmt.executeUpdate(dropSQL2);
    }
}

    /**
     * Checks if a household with the given building number and unit number already exists.
     *
     * @param buildingNum the building number to check
     * @param unitNum the unit number to check
     * @return true if the household already exists, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean householdExists(int buildingNum, int unitNum) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM Households WHERE buildingNum = ? AND unitNum = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, buildingNum);
            pstmt.setInt(2, unitNum);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return count > 0;
            }
        }
        return false;
    }

    /**
     * Retrieves all households from the database.
     *
     * @return List of Household objects
     * @throws SQLException if a database access error occurs
     */
    public List<Household> getAllHouseholds() throws SQLException {
        List<Household> households = new ArrayList<>();
        String query = "SELECT * FROM Households";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            Household household = new Household();
            household.setBuildingNum(rs.getInt("buildingNum"));
            household.setUnitNum(rs.getInt("unitNum"));
            household.setMonthlyExpenditure(rs.getDouble("monthlyExpenditure"));
            household.setMonthlyAmortization(rs.getDouble("monthlyAmortization"));
            household.setYearOfResidence(rs.getInt("yearOfResidence"));

            household.setMembers(getMembersByHousehold(household.getBuildingNum(), household.getUnitNum()));

            households.add(household);
        }
        return households;
    }

    public List<Member> getMembersByHousehold(int buildingNum, int unitNum) throws SQLException {
        List<Member> members = new ArrayList<>();
        String query = "SELECT * FROM Members WHERE buildingNum = ? AND unitNum = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, buildingNum);
        pstmt.setInt(2, unitNum);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Member member = new Member();
            member.setLastName(rs.getString("lastName"));
            member.setFirstName(rs.getString("firstName"));
            member.setMiddleName(rs.getString("middleName"));
            member.setGender(rs.getString("gender"));
            member.setBirthday(LocalDate.parse(rs.getString("birthday")));
            member.setHealthStatus(rs.getString("healthStatus"));
            member.setPwdType(rs.getString("pwdType"));
            member.setIsSeniorCitizen(rs.getInt("isSeniorCitizen"));
            member.setCivilStatus(rs.getString("civilStatus"));
            member.setContactNumber(rs.getString("contactNumber"));
            member.setHighestEducationalAttainment(rs.getString("highestEducationalAttainment"));
            member.setOccupation(rs.getString("occupation"));
            member.setMonthlyIncome(rs.getDouble("monthlyIncome"));
            member.setIsMainRespondent(rs.getInt("isMainRespondent"));
            member.setBuildingNum(rs.getInt("buildingNum"));
            member.setUnitNum(rs.getInt("unitNum"));
            member.setProfileImagePath(rs.getString("profileImagePath"));

            members.add(member);
        }
        return members;
    }

    /**
     * Retrieves all members from the Members table.
     *
     * @return a list of Member objects representing all members in the database
     * @throws SQLException if a database access error occurs
     */
    public List<Member> getAllMembers() throws SQLException {
        List<Member> members = new ArrayList<>();
        String query = "SELECT * FROM Members WHERE isMainRespondent = 0";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Member member = new Member();
                member.setLastName(rs.getString("lastName"));
                member.setFirstName(rs.getString("firstName"));
                member.setMiddleName(rs.getString("middleName"));
                member.setGender(rs.getString("gender"));
                member.setBirthday(rs.getObject("birthday", LocalDate.class));
                member.setHealthStatus(rs.getString("healthStatus"));
                member.setPwdType(rs.getString("pwdType"));
                member.setIsSeniorCitizen(rs.getInt("isSeniorCitizen"));
                member.setCivilStatus(rs.getString("civilStatus"));
                member.setContactNumber(rs.getString("contactNumber"));
                member.setHighestEducationalAttainment(rs.getString("highestEducationalAttainment"));
                member.setOccupation(rs.getString("occupation"));
                member.setMonthlyIncome(rs.getDouble("monthlyIncome"));
                member.setIsMainRespondent(rs.getInt("isMainRespondent"));
                member.setBuildingNum(rs.getInt("buildingNum"));
                member.setUnitNum(rs.getInt("unitNum"));
                member.setProfileImagePath(rs.getString("profileImagePath"));

                members.add(member);
            }
        }
        return members;
    }

    public Member getMainRespondent(int buildingNum, int unitNum) throws SQLException {
        String query = "SELECT * FROM Members WHERE buildingNum = ? AND unitNum = ? AND isMainRespondent = 1";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, buildingNum);
        pstmt.setInt(2, unitNum);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            Member member = new Member();
            member.setLastName(rs.getString("lastName"));
            member.setFirstName(rs.getString("firstName"));
            member.setMiddleName(rs.getString("middleName"));
            member.setGender(rs.getString("gender"));
            member.setBirthday(LocalDate.parse(rs.getString("birthday")));
            member.setHealthStatus(rs.getString("healthStatus"));
            member.setPwdType(rs.getString("pwdType"));
            member.setIsSeniorCitizen(rs.getInt("isSeniorCitizen"));
            member.setCivilStatus(rs.getString("civilStatus"));
            member.setContactNumber(rs.getString("contactNumber"));
            member.setHighestEducationalAttainment(rs.getString("highestEducationalAttainment"));
            member.setOccupation(rs.getString("occupation"));
            member.setMonthlyIncome(rs.getDouble("monthlyIncome"));
            member.setIsMainRespondent(rs.getInt("isMainRespondent"));
            member.setBuildingNum(rs.getInt("buildingNum"));
            member.setUnitNum(rs.getInt("unitNum"));
            member.setProfileImagePath(rs.getString("profileImagePath"));

            return member;
        }

        return null;
    }

    
    /**
     * Displays the contents of a specified table ordered by a given condition.
     *
     * @param conn the database connection
     * @param tableName the name of the table to display
     * @param orderCondition the condition to order the results by
     * @throws SQLException if a database access error occurs
     */
    public void displayDatabase(Connection conn, String tableName, String orderCondition) throws SQLException { //have this return the resultset instead so its passable for rendering to the frontend
        String selectSQL = "SELECT * FROM " + tableName + " ORDER BY " + orderCondition;
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(selectSQL);

        //sample displaying
        System.out.println("\nRESULTS FOR " + tableName);
        while(rs.next()) {
            System.out.println("Entry: " + rs.getString("lastName") + ", " + rs.getString("firstName"));
        }

    }

    public Household getHousehold(int buildingNum, int unitNum) throws SQLException {
        Household household = null;
        String query = "SELECT * FROM Households WHERE buildingNum = ? AND unitNum = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, buildingNum);
        pstmt.setInt(2, unitNum);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            household = new Household();
            household.setBuildingNum(rs.getInt("buildingNum"));
            household.setUnitNum(rs.getInt("unitNum"));
            household.setMonthlyExpenditure(rs.getDouble("monthlyExpenditure"));
            household.setMonthlyAmortization(rs.getDouble("monthlyAmortization"));
            household.setYearOfResidence(rs.getInt("yearOfResidence"));
        }

        rs.close();
        pstmt.close();

        return household;
    }
}
