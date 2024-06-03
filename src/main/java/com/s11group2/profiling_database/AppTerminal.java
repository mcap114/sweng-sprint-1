package com.s11group2.profiling_database;

import com.s11group2.profiling_database.Model.DatabaseManager;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

/*
Terminal-based operation

run thru here for now
*/

public class AppTerminal {
    public static void main(String[] args) {
        try {
            DatabaseManager dbManager = new DatabaseManager();
            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            while (running) {
                System.out.println("Menu:");
                System.out.println("1. Create Tables");
                System.out.println("2. Insert Household Record");
                System.out.println("3. Insert Member Record");
                System.out.println("4. Edit Household Record");
                System.out.println("5. Delete Household Record");
                System.out.println("6. Edit Household Record");
                System.out.println("7. Delete Household Record");
                System.out.println("8. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        dbManager.createTables();
                        System.out.println("Tables created successfully.");
                        break;
                    case 2:
                        System.out.print("Enter building number: ");
                        int buildingNum = scanner.nextInt();
                        System.out.print("Enter unit number: ");
                        int unitNum = scanner.nextInt();
                        System.out.print("Enter monthly expenditure: ");
                        double monthlyExpenditure = scanner.nextDouble();
                        System.out.print("Enter monthly amortization: ");
                        double monthlyAmortization = scanner.nextDouble();
                        System.out.print("Enter year of residence: ");
                        int yearOfResidence = scanner.nextInt();
                        dbManager.insertHousehold(buildingNum, unitNum, monthlyExpenditure, monthlyAmortization, yearOfResidence);
                        System.out.println("Household record inserted successfully.");
                        break;
                    case 3:
                        System.out.print("Enter last name: ");
                        String lastName = scanner.nextLine();
                        System.out.print("Enter first name: ");
                        String firstName = scanner.nextLine();
                        System.out.print("Enter middle name: ");
                        String middleName = scanner.nextLine();
                        System.out.print("Enter gender: ");
                        String gender = scanner.nextLine();
                        System.out.print("Enter birthday (YYYY-MM-DD): ");
                        LocalDate birthday = LocalDate.parse(scanner.nextLine());
                        System.out.print("Enter health status: ");
                        String healthStatus = scanner.nextLine();
                        System.out.print("Enter PWD type: ");
                        String pwdType = scanner.nextLine();
                        System.out.print("Enter is senior citizen (1 for yes, 0 for no): ");
                        int isSeniorCitizen = scanner.nextInt();
                        scanner.nextLine(); 
                        System.out.print("Enter civil status: ");
                        String civilStatus = scanner.nextLine();
                        System.out.print("Enter contact number: ");
                        String contactNumber = scanner.nextLine();
                        System.out.print("Enter highest educational attainment: ");
                        String highestEducationalAttainment = scanner.nextLine();
                        System.out.print("Enter occupation: ");
                        String occupation = scanner.nextLine();
                        System.out.print("Enter monthly income: ");
                        double monthlyIncome = scanner.nextDouble();
                        System.out.print("Enter is main respondent (1 for yes, 0 for no): ");
                        int isMainRespondent = scanner.nextInt();
                        System.out.print("Enter building number: ");
                        buildingNum = scanner.nextInt();
                        System.out.print("Enter unit number: ");
                        unitNum = scanner.nextInt();
                        dbManager.insertMember(lastName, firstName, middleName, gender, birthday, healthStatus, pwdType, isSeniorCitizen, civilStatus, contactNumber, highestEducationalAttainment, occupation, monthlyIncome, isMainRespondent, buildingNum, unitNum);
                        System.out.println("Member record inserted successfully.");
                        break;
                    
                    //TODO: add edit/delete
                    
                    case 8:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }

            dbManager.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
