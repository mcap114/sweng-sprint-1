package com.s11group2.profiling_database.Model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import org.apache.commons.io.FilenameUtils;

/**
 * The DatabaseManager class provides methods to manage the SQLite database,
 * including creating tables, inserting records, and displaying data.
 */
public class DatabaseManager {
    private static DatabaseManager instance;
    private Connection conn;

    private DatabaseManager() throws SQLException {
        String url = "jdbc:sqlite:units.db";
        this.conn = DriverManager.getConnection(url);
    }

    public static synchronized DatabaseManager getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public Connection getConn() {
        return conn;
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
        // Statement stmt = conn.createStatement();
        // stmt.execute("DROP TABLE Pets;"); // uncomment to clear tables on springboot run, recomment after cleared
        // stmt.execute("DROP TABLE Members;");
        // stmt.execute("DROP TABLE Households;");
        createHouseholdTable();
        createMemberTable();
        createPetTable();
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
                "monthlyExpenditure real, " +
                "monthlyAmortization real, " +
                "yearOfResidence integer, " +
                "fileString varchar(255), " +
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
                "lastName varchar(255), " +
                "firstName varchar(255), " +
                "middleName varchar(255), " +
                "gender varchar(255), " +
                "birthday date, " +
                "healthStatus varchar(255), " +
                "pwdType varchar(255), " +
                "isSeniorCitizen integer, " +
                "civilStatus varchar(255), " +
                "contactNumber varchar(255), " +
                "highestEducationalAttainment varchar(255), " +
                "occupation varchar(255), " +
                "monthlyIncome real, " +
                "isMainRespondent integer, " +
                "relationToMainRespondent varchar(255), " +
                "buildingNum integer, " +
                "unitNum integer, " +
                "profileImagePath varchar(255), " +
                "constraint buildingUnitNumMembers_fk foreign key (buildingNum, unitNum) references Households(buildingNum, unitNum) " +
                ");";
        Statement stmt = conn.createStatement();
        stmt.execute(createTableSQL);
    }

    private void createPetTable() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Pets (" +
                "petName varchar(255), " +
                "petSpecies varchar(255), " +
                "buildingNum integer, " +
                "unitNum integer, " +
                "petImagePath varchar(255), " +
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
    public void insertHousehold(int buildingNum, int unitNum, double monthlyExpenditure, double monthlyAmortization, int yearOfResidence, MultipartFile[] userFiles) throws SQLException, IOException {
        if (householdExists(buildingNum, unitNum)) {
            throw new IllegalArgumentException("Household with building number " + buildingNum + " and unit number " + unitNum + " already exists.");
        }

        StringBuilder fileString = new StringBuilder();

        if (userFiles.length > 0) {
            File dir = new File("./src/main/resources/static/user/files");
            if (!dir.exists()) {
                dir.mkdirs(); // Create the directory if it does not exist
            }
    
            for (MultipartFile userFile : userFiles) {
                String originalFilename = userFile.getOriginalFilename();
                if (originalFilename != null) {
                    String extension = "." + FilenameUtils.getExtension(originalFilename);
                    File temp = new File(dir, originalFilename + extension);
                    fileString.append("/user/files/").append(temp.getName()).append(" "); // Construct file path
    
                    try (OutputStream os = new FileOutputStream(temp)) {
                        os.write(userFile.getBytes());
                    }
                }
            }
        }

        String insertSQL = "INSERT INTO Households (buildingNum, unitNum, monthlyExpenditure, monthlyAmortization, yearOfResidence, fileString) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setInt(1, buildingNum);
            pstmt.setInt(2, unitNum);
            pstmt.setDouble(3, monthlyExpenditure);
            pstmt.setDouble(4, monthlyAmortization);
            pstmt.setInt(5, yearOfResidence);
            pstmt.setString(6, fileString.toString());
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
     * @param profileImagePath the profile image path
     * @throws SQLException if a database access error occurs
     */
    public void insertMember(String lastName, String firstName, String middleName, String gender, LocalDate birthday, String healthStatus, String pwdType, Integer isSeniorCitizen, String civilStatus, String contactNumber, String highestEducationalAttainment, String occupation, Double monthlyIncome, Integer isMainRespondent, String relationToMainRespondent, Integer buildingNum, Integer unitNum, MultipartFile profileImage) throws SQLException, IOException {
        String insertSQL = "INSERT INTO Members (lastName, firstName, middleName, gender, birthday, healthStatus, pwdType, isSeniorCitizen, civilStatus, contactNumber, highestEducationalAttainment, occupation, monthlyIncome, isMainRespondent, relationToMainRespondent, buildingNum, unitNum, profileImagePath) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
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
            pstmt.setString(15, relationToMainRespondent);
            pstmt.setInt(16, buildingNum);
            pstmt.setInt(17, unitNum);
    
            String tempPath = null;
            String extension = "." + FilenameUtils.getExtension(profileImage.getOriginalFilename());
    
            // Ensure the directory exists for user profiles
            File dir = new File("./src/main/resources/static/user/profiles");
            if (!dir.exists()) {
                dir.mkdirs(); // Create the directory if it does not exist
            }
    
            File temp = File.createTempFile("profile", extension, dir);
            tempPath = "/user/profiles/" + temp.getName();
    
            try (OutputStream os = new FileOutputStream(temp)) {
                os.write(profileImage.getBytes());
            }
    
            pstmt.setString(18, tempPath);
            pstmt.executeUpdate();
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace to help with debugging
            throw e; // Rethrow exception to handle it further up the call stack if needed
        }
    }
    
    public void insertPet(String petName, String petSpecies, Integer buildingNum, Integer unitNum, MultipartFile petImage) throws SQLException, IOException {
        String insertSQL = "INSERT INTO Pets (petName, petSpecies, buildingNum, unitNum, petImagePath) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, petName);
            pstmt.setString(2, petSpecies);
            pstmt.setInt(3, buildingNum);
            pstmt.setInt(4, unitNum);
    
            String tempPath = null;
            String extension = "." + FilenameUtils.getExtension(petImage.getOriginalFilename());
    
            // Ensure the directory exists for pet images
            File dir = new File("./src/main/resources/static/user/pets");
            if (!dir.exists()) {
                dir.mkdirs(); // Create the directory if it does not exist
            }
    
            File temp = File.createTempFile("pet", extension, dir);
            tempPath = "/user/pets/" + temp.getName();
    
            try (OutputStream os = new FileOutputStream(temp)) {
                os.write(petImage.getBytes());
            }
    
            pstmt.setString(5, tempPath);
            pstmt.executeUpdate();
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace to help with debugging
            throw e; // Rethrow exception to handle it further up the call stack if needed
        }
    }
    

    public void editHousehold(int buildingNum, int unitNum, String field, Object newValue) throws SQLException {
        String query = "UPDATE Households SET " + field + " = ? WHERE buildingNum = ? AND unitNum = ?";
         PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setObject(1, newValue);
            pstmt.setInt(2, buildingNum);
            pstmt.setInt(3, unitNum);
            pstmt.executeUpdate();

    }

    public void editMember(String lastName, String firstName, String middleName,
                           int buildingNum, int unitNum, String field, Object newValue) throws SQLException
    {
        String query = "UPDATE Members SET " + field + " = ? WHERE lastName = ? AND firstName = ? AND middleName = ? AND buildingNum = ? AND unitNum = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setObject(1, newValue);
            pstmt.setString(2, lastName);
            pstmt.setString(3, firstName);
            pstmt.setString(4, middleName);
            pstmt.setInt(5, buildingNum);
            pstmt.setInt(6, unitNum);
            pstmt.executeUpdate();

    }

    public void editPet(String petName, String petSpecies,
                        int buildingNum, int unitNum, String field, Object newValue) throws SQLException
    {
        String query = "UPDATE Pets SET " + field + " = ? WHERE petName = ? AND petSpecies = ? AND buildingNum = ? AND unitNum = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setObject(1, newValue);
            pstmt.setString(2, petName);
            pstmt.setString(3, petSpecies);
            pstmt.setInt(4, buildingNum);
            pstmt.setInt(5, unitNum);
            pstmt.executeUpdate();
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
        PreparedStatement pstmtMembers = conn.prepareStatement(deleteMembersSQL);
            pstmtMembers.setInt(1, buildingNum);
            pstmtMembers.setInt(2, unitNum);
            pstmtMembers.executeUpdate();


        //deletes associated members as well
        String deleteHouseholdSQL = "DELETE FROM Households WHERE buildingNum = ? AND unitNum = ?";
        PreparedStatement pstmtHousehold = conn.prepareStatement(deleteHouseholdSQL);
            pstmtHousehold.setInt(1, buildingNum);
            pstmtHousehold.setInt(2, unitNum);
            pstmtHousehold.executeUpdate();
    }

    public void deleteMember(String lastName, String firstName, String middleName, int buildingNum, int unitNum) throws SQLException {
        String query = "DELETE FROM Members WHERE lastName = ? AND firstName = ? AND middleName = ? AND buildingNum = ? AND unitNum = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, lastName);
            pstmt.setString(2, firstName);
            pstmt.setString(3, middleName);
            pstmt.setInt(4, buildingNum);
            pstmt.setInt(5, unitNum);
            pstmt.executeUpdate();
    }

    public void dropTables(Connection conn) throws SQLException {
        String dropSQL1 = "DROP TABLE IF EXISTS Members";
        String dropSQL2 = "DROP TABLE IF EXISTS Pets"; // Ensure to drop the Pets table as well
        String dropSQL3 = "DROP TABLE IF EXISTS Households";
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(dropSQL1);
        stmt.executeUpdate(dropSQL2); // Drop Pets table
        stmt.executeUpdate(dropSQL3);
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

    public List<Household> sortHouseholds(String sortCondition) throws SQLException {
        List<Household> households = new ArrayList<>();
        String query = "SELECT * FROM Households ORDER BY " + sortCondition;

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


    /**
     * Retrieves all households from the database.
     *
     * @return List of Household objects
     * @throws SQLException if a database access error occurs
     */
    public List<Household> searchHouseholds(String searchTerm) throws SQLException {
        List<Household> households = new ArrayList<>();


        String query = "SELECT DISTINCT h.* FROM Households h " +
                "LEFT JOIN Members m ON h.buildingNum = m.buildingNum AND h.unitNum = m.unitNum " +
                "WHERE h.buildingNum = ? OR h.unitNum = ? OR m.firstName LIKE ? OR m.lastName LIKE ?";

        PreparedStatement pstmt = conn.prepareStatement(query);

        int buildingOrUnitNum;
        try {
            buildingOrUnitNum = Integer.parseInt(searchTerm);
        } catch (NumberFormatException e) {
            buildingOrUnitNum = -1; // Invalid number, won't match any building or unit
        }

        pstmt.setInt(1, buildingOrUnitNum);
        pstmt.setInt(2, buildingOrUnitNum);
        pstmt.setString(3, "%" + searchTerm + "%");
        pstmt.setString(4, "%" + searchTerm + "%");

        try (ResultSet rs = pstmt.executeQuery()) {
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
        }

        return households;
    }


    public List<Member> getMembersByHousehold(int buildingNum, int unitNum) throws SQLException {
        List<Member> members = new ArrayList<>();
        String query = "SELECT * FROM Members WHERE buildingNum = ? AND unitNum = ? AND isMainRespondent = FALSE";

        PreparedStatement pstmt;
        ResultSet rs;

        pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, buildingNum);
        pstmt.setInt(2, unitNum);

        rs = pstmt.executeQuery();

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
            member.setRelationToMainRespondent(rs.getString("relationToMainRespondent"));
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

        Statement stmt = null;
        ResultSet rs = null;

        stmt = conn.createStatement();
        rs = stmt.executeQuery(query);

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
            member.setRelationToMainRespondent(rs.getString("relationToMainRespondent"));
            member.setBuildingNum(rs.getInt("buildingNum"));
            member.setUnitNum(rs.getInt("unitNum"));
            member.setProfileImagePath(rs.getString("profileImagePath"));

            members.add(member);
        }

        // Close resources (if not using try-with-resources)
        if (rs != null) {
            rs.close();
        }
        if (stmt != null) {
            stmt.close();
        }

        return members;
    }


    public Member getMainRespondent(int buildingNum, int unitNum) throws SQLException {
        String query = "SELECT * FROM Members WHERE buildingNum = ? AND unitNum = ? AND isMainRespondent = 1";

        PreparedStatement pstmt;
        ResultSet rs;
        Member member = null;

        pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, buildingNum);
        pstmt.setInt(2, unitNum);
        rs = pstmt.executeQuery();

        if (rs.next()) {
            member = new Member();
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
            member.setRelationToMainRespondent(rs.getString("relationToMainRespondent"));
            member.setBuildingNum(rs.getInt("buildingNum"));
            member.setUnitNum(rs.getInt("unitNum"));
            member.setProfileImagePath(rs.getString("profileImagePath"));
        }


        return member;
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
    public List<Pet> getPetsByHousehold(int buildingNum, int unitNum) throws SQLException {
        List<Pet> pets = new ArrayList<>();
        String query = "SELECT * FROM Pets WHERE buildingNum = ? AND unitNum = ?";
    
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, buildingNum);
        pstmt.setInt(2, unitNum);
    
        ResultSet rs = pstmt.executeQuery();
    
        while (rs.next()) {
            Pet pet = new Pet(
                rs.getString("petName"),
                rs.getString("petSpecies"),
                rs.getInt("buildingNum"),
                rs.getInt("unitNum"),
                rs.getString("petImagePath")
                
            );
    
            pets.add(pet); // Add pet to the list
        }
    
        return pets;
    }

    public boolean checkHouseholdExists(int buildingNum, int unitNum) throws SQLException {
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

}
