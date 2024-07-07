package com.s11group2.profiling_database.Controller;

import com.s11group2.profiling_database.Model.DatabaseManager;
import com.s11group2.profiling_database.Model.Household;
import com.s11group2.profiling_database.Util.ValidationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * The AppController class manages the application's interaction with the database,
 * including creating tables, inserting records, and validating input.
 */
public class AppController {
    private static final String UPLOADED_FOLDER = "uploads/";

    private DatabaseManager dbManager;

    /**
     * Constructs an AppController and initializes the DatabaseManager.
     */
    public AppController() {
        try {
            dbManager = new DatabaseManager();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error initializing DatabaseManager: " + e.getMessage());
        }
    }

    public DatabaseManager getDbManager() {
        return this.dbManager;
    }

    /**
     * Creates the necessary tables in the database.
     */
    public void createTables() {
        try {
            dbManager.createTables();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error creating tables: " + e.getMessage());
        }
    }
    
    public void dropTables() {
        try {
            dbManager.dropTables(dbManager.getConnection());
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error dropping tables: " + e.getMessage());
        }
    }

    /**
     * Inserts a household record into the database after validating the input.
     *
     * @param buildingNum the building number
     * @param unitNum the unit number
     * @param monthlyExpenditure the monthly expenditure
     * @param monthlyAmortization the monthly amortization
     * @param yearOfResidence the year of residence
     */
    public void insertHousehold(int buildingNum, int unitNum, double monthlyExpenditure, double monthlyAmortization, int yearOfResidence) {
        try {
            // Input validation
            validateHouseholdInput(buildingNum, unitNum, monthlyExpenditure, monthlyAmortization, yearOfResidence);
            dbManager.insertHousehold(buildingNum, unitNum, monthlyExpenditure, monthlyAmortization, yearOfResidence);
        } catch (ValidationException e) {
            System.err.println("Validation Error: " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error inserting household: " + e.getMessage());
        }
    }

    /**
     * Inserts a member record into the database after validating the input.
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
     * @param profileImagePath the path to the profile image
     */
    public void insertMember(String lastName, String firstName, String middleName, String gender, LocalDate birthday, String healthStatus, String pwdType, Integer isSeniorCitizen, String civilStatus, String contactNumber, String highestEducationalAttainment, String occupation, Double monthlyIncome, Integer isMainRespondent, Integer buildingNum, Integer unitNum, String profileImagePath) {
        try {
            // Input validation
            validateMemberInput(lastName, firstName, gender, birthday, healthStatus, civilStatus, contactNumber, highestEducationalAttainment, occupation, monthlyIncome, isMainRespondent, buildingNum, unitNum);
            dbManager.insertMember(lastName, firstName, middleName, gender, birthday, healthStatus, pwdType, isSeniorCitizen, civilStatus, contactNumber, highestEducationalAttainment, occupation, monthlyIncome, isMainRespondent, buildingNum, unitNum, profileImagePath);
        } catch (ValidationException e) {
            System.err.println("Validation Error: " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error inserting member: " + e.getMessage());
        }
    }

    /**
     * Handles the image upload and returns the file path.
     *
     * @param file the uploaded file
     * @return the file path
     * @throws IOException if an error occurs while saving the file
     */
    private String handleImageUpload(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("File is empty");
        }

        byte[] bytes = file.getBytes();
        Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
        Files.write(path, bytes);
        return path.toString();
    }

    /**
     * Fetches the current database connection.
     *
     * @return the current Connection object
     */
    public Connection fetchConnection() {
        return dbManager.getConnection();
    }

    /**
     * Closes the current database connection.
     */
    public void closeConnection() {
        try {
            dbManager.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }

    public void editHousehold(int buildingNum, int unitNum, String field, Object newValue) throws SQLException {
        dbManager.editHousehold(buildingNum, unitNum, field, newValue);
    }

    public void editMember(String lastName, String firstName, String middleName, int buildingNum, int unitNum, String field, Object newValue) throws SQLException {
        dbManager.editMember(lastName, firstName, middleName, buildingNum, unitNum, field, newValue);
    }

    public void deleteHousehold(int buildingNum, int unitNum) throws SQLException {
        dbManager.deleteHousehold(buildingNum, unitNum);
    }

    public void deleteMember(String lastName, String firstName, String middleName, int buildingNum, int unitNum) throws SQLException {
        dbManager.deleteMember(lastName, firstName, middleName, buildingNum, unitNum);
    }

    /**
     * Validates the input for inserting a household record.
     *
     * @param buildingNum the building number
     * @param unitNum the unit number
     * @param monthlyExpenditure the monthly expenditure
     * @param monthlyAmortization the monthly amortization
     * @param yearOfResidence the year of residence
     * @throws ValidationException if any input is invalid
     */
    private void validateHouseholdInput(int buildingNum, int unitNum, double monthlyExpenditure, double monthlyAmortization, int yearOfResidence) throws ValidationException {
        if (buildingNum <= 0) throw new ValidationException("Building number must be positive.");
        if (unitNum <= 0) throw new ValidationException("Unit number must be positive.");
        if (monthlyExpenditure <= 0) throw new ValidationException("Monthly expenditure must be positive.");
        if (monthlyAmortization <= 0) throw new ValidationException("Monthly amortization must be positive.");
        if (yearOfResidence <= 0) throw new ValidationException("Year of residence must be positive.");
    }

    public List<Household> getAllHousehoulds() throws SQLException {
        return dbManager.getAllHouseholds();
    }

    /**
     * Validates the input for inserting a member record.
     *
     * @param lastName the last name
     * @param firstName the first name
     * @param gender the gender
     * @param birthday the birthday
     * @param healthStatus the health status
     * @param civilStatus the civil status
     * @param contactNumber the contact number
     * @param highestEducationalAttainment the highest educational attainment
     * @param occupation the occupation
     * @param monthlyIncome the monthly income
     * @param isMainRespondent indicates if the person is the main respondent
     * @param buildingNum the building number
     * @param unitNum the unit number
     * @throws ValidationException if any input is invalid
     */
    private void validateMemberInput(String lastName, String firstName, String gender, LocalDate birthday, String healthStatus, String civilStatus, String contactNumber, String highestEducationalAttainment, String occupation, Double monthlyIncome, Integer isMainRespondent, Integer buildingNum, Integer unitNum) throws ValidationException {
        if (lastName == null || lastName.isEmpty()) throw new ValidationException("Last name cannot be empty.");
        if (firstName == null || firstName.isEmpty()) throw new ValidationException("First name cannot be empty.");
        if (gender == null || gender.isEmpty()) throw new ValidationException("Gender cannot be empty.");
        if (birthday == null) throw new ValidationException("Birthday cannot be null.");
        if (healthStatus == null || healthStatus.isEmpty()) throw new ValidationException("Health status cannot be empty.");
        if (civilStatus == null || civilStatus.isEmpty()) throw new ValidationException("Civil status cannot be empty.");
        if (contactNumber == null || contactNumber.isEmpty()) throw new ValidationException("Contact number cannot be empty.");
        if (highestEducationalAttainment == null || highestEducationalAttainment.isEmpty()) throw new ValidationException("Highest educational attainment cannot be empty.");
        if (occupation == null || occupation.isEmpty()) throw new ValidationException("Occupation cannot be empty.");
        if (monthlyIncome == null || monthlyIncome <= 0) throw new ValidationException("Monthly income must be positive.");
        if (isMainRespondent == null) throw new ValidationException("Main respondent status cannot be null.");
        if (buildingNum == null || buildingNum <= 0) throw new ValidationException("Building number must be positive.");
        if (unitNum == null || unitNum <= 0) throw new ValidationException("Unit number must be positive.");
    }
}
