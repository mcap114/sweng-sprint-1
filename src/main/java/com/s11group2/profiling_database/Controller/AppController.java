package com.s11group2.profiling_database.Controller;

import com.s11group2.profiling_database.Model.DatabaseManager;
import java.sql.SQLException;
import java.time.LocalDate;



public class AppController {
    private DatabaseManager dbManager;

    public AppController() {
        try {
            dbManager = new DatabaseManager();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTables() {
        try {
            dbManager.createTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertHousehold(int buildingNum, int unitNum, double monthlyExpenditure, double monthlyAmortization, int yearOfResidence) {
        try {
            dbManager.insertHousehold(buildingNum, unitNum, monthlyExpenditure, monthlyAmortization, yearOfResidence);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertMember(String lastName, String firstName, String middleName, String gender, LocalDate birthday, String healthStatus, String pwdType, Integer isSeniorCitizen, String civilStatus, String contactNumber, String highestEducationalAttainment, String occupation, Double monthlyIncome, Integer isMainRespondent, Integer buildingNum, Integer unitNum) {
        try {
            dbManager.insertMember(lastName, firstName, middleName, gender, birthday, healthStatus, pwdType, isSeniorCitizen, civilStatus, contactNumber, highestEducationalAttainment, occupation, monthlyIncome, isMainRespondent, buildingNum, unitNum);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            dbManager.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
