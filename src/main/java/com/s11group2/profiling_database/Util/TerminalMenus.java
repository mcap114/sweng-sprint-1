package com.s11group2.profiling_database.Util;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

import com.s11group2.profiling_database.Controller.AppController;
import com.s11group2.profiling_database.Controller.SearchController;

/**
 * Serves as pseudo-View, displays sub-menus in AppTerminal.
 */
public class TerminalMenus {

    private static Scanner scanner = new Scanner(System.in);
    private AppController appController;
    private SearchController searchController;

    public TerminalMenus() {
        appController = new AppController();
        searchController = new SearchController(appController.getDbManager());
    }

    public void createTablesMenu() throws SQLException {
        System.out.println("Create Tables Menu:");
        System.out.println("1. Create Tables");
        System.out.println("2. DEBUG: DELETE ALL TABLES");
        System.out.println("3. Back to Main Menu");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                appController.createTables();
                System.out.println("Tables created successfully.");
                break;
            case 2:
                appController.dropTables();
                System.out.println("Tables dropped successfully.");
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    public void viewProfileMenu() throws SQLException {
        System.out.println("View Profile Menu:");
        System.out.println("1. View Household Profiles");
        System.out.println("2. View Member Profiles");
        System.out.println("3. View Pet Profiles");
        System.out.println("4. Search");
        System.out.println("5. Back to Main Menu");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                DisplayUtil.displayTableContents(appController.fetchConnection(), "Households");
                break;
            case 2:
                DisplayUtil.displayTableContents(appController.fetchConnection(), "Members");
                break;
            case 3:
                DisplayUtil.displayTableContents(appController.fetchConnection(), "Pets");
                break;
            case 4:
                searchHouseholdsMenu();
                break;
            case 5:
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    public void addHouseholdMenu() throws SQLException {
        System.out.println("Add Household Menu:");
        System.out.println("1. Add New Household Record");
        System.out.println("2. Add Member to Household");
        System.out.println("3. Add Pet to Household");
        System.out.println("4. Back to Main Menu");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                try {
                    System.out.print("Enter building number: ");
                    int buildingNum = InputValidation.readIntFromScanner(scanner);

                    System.out.print("Enter unit number: ");
                    int unitNum = InputValidation.readIntFromScanner(scanner);

                    System.out.print("Enter monthly expenditure: ");
                    double monthlyExpenditure = InputValidation.readDoubleFromScanner(scanner);

                    System.out.print("Enter monthly amortization: ");
                    double monthlyAmortization = InputValidation.readDoubleFromScanner(scanner);

                    System.out.print("Enter year of residence: ");
                    int yearOfResidence = InputValidation.readIntFromScanner(scanner);

                    System.out.print("Enter last name: ");
                    String lastName = scanner.nextLine().trim();
                    InputValidation.validateStringInput(lastName);

                    System.out.print("Enter first name: ");
                    String firstName = scanner.nextLine().trim();
                    InputValidation.validateStringInput(firstName);

                    System.out.print("Enter middle name: ");
                    String middleName = scanner.nextLine().trim();
                    InputValidation.validateStringInput(middleName);

                    System.out.print("Enter gender: ");
                    String gender = scanner.nextLine().trim();
                    InputValidation.validateStringInput(gender);

                    System.out.print("Enter birthday (YYYY-MM-DD): ");
                    LocalDate birthday = InputValidation.readDateFromScanner(scanner);
                    InputValidation.validateDateInput(birthday.toString());

                    System.out.print("Enter health status: ");
                    String healthStatus = scanner.nextLine().trim();
                    InputValidation.validateStringInput(healthStatus);

                    System.out.print("Is PWD or Not: ");
                    String pwdType = scanner.nextLine().trim();
                    InputValidation.validateStringInput(pwdType);

                    int isSeniorCitizen = InputValidation.isSeniorCitizen(InputValidation.calculateAge(birthday));

                    System.out.print("Enter civil status: ");
                    String civilStatus = scanner.nextLine().trim();
                    InputValidation.validateStringInput(civilStatus);

                    System.out.print("Enter contact number: ");
                    String contactNumber = scanner.nextLine().trim();
                    InputValidation.validateStringInput(contactNumber);

                    System.out.print("Enter highest educational attainment: ");
                    String highestEducationalAttainment = scanner.nextLine().trim();
                    InputValidation.validateStringInput(highestEducationalAttainment);

                    System.out.print("Enter occupation: ");
                    String occupation = scanner.nextLine().trim();
                    InputValidation.validateStringInput(occupation);

                    System.out.print("Enter monthly income: ");
                    double monthlyIncome = InputValidation.readDoubleFromScanner(scanner);

                    int isMainRespondent = 1;

                    String profileImagePath = "/";

                    appController.insertHousehold(buildingNum, unitNum, monthlyExpenditure, monthlyAmortization, yearOfResidence);
                    appController.insertMember(lastName, firstName, middleName, gender, birthday, healthStatus, pwdType, isSeniorCitizen, civilStatus, contactNumber, highestEducationalAttainment, occupation, monthlyIncome, isMainRespondent, buildingNum, unitNum, profileImagePath);
                    System.out.println("Household record inserted successfully.");
                } catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
                }
                break;
            case 2:

                try {
                    System.out.print("Enter building number: ");
                    int buildingNum = InputValidation.readIntFromScanner(scanner);

                    System.out.print("Enter unit number: ");
                    int unitNum = InputValidation.readIntFromScanner(scanner);

                    System.out.print("Enter last name: ");
                    String lastName = scanner.nextLine().trim();
                    InputValidation.validateStringInput(lastName);

                    System.out.print("Enter first name: ");
                    String firstName = scanner.nextLine().trim();
                    InputValidation.validateStringInput(firstName);

                    System.out.print("Enter middle name: ");
                    String middleName = scanner.nextLine().trim();
                    InputValidation.validateStringInput(middleName);

                    System.out.print("Enter gender: ");
                    String gender = scanner.nextLine().trim();
                    InputValidation.validateStringInput(gender);

                    System.out.print("Enter birthday (YYYY-MM-DD): ");
                    LocalDate birthday = InputValidation.readDateFromScanner(scanner);
                    InputValidation.validateDateInput(birthday.toString());

                    System.out.print("Enter health status: ");
                    String healthStatus = scanner.nextLine().trim();
                    InputValidation.validateStringInput(healthStatus);

                    System.out.print("Is PWD or Not: ");
                    String pwdType = scanner.nextLine().trim();
                    InputValidation.validateStringInput(pwdType);

                    int isSeniorCitizen = InputValidation.isSeniorCitizen(InputValidation.calculateAge(birthday));

                    System.out.print("Enter civil status: ");
                    String civilStatus = scanner.nextLine().trim();
                    InputValidation.validateStringInput(civilStatus);

                    System.out.print("Enter contact number: ");
                    String contactNumber = scanner.nextLine().trim();
                    InputValidation.validateStringInput(contactNumber);

                    System.out.print("Enter highest educational attainment: ");
                    String highestEducationalAttainment = scanner.nextLine().trim();
                    InputValidation.validateStringInput(highestEducationalAttainment);

                    System.out.print("Enter occupation: ");
                    String occupation = scanner.nextLine().trim();
                    InputValidation.validateStringInput(occupation);

                    System.out.print("Enter monthly income: ");
                    double monthlyIncome = InputValidation.readDoubleFromScanner(scanner);

                    int isMainRespondent = 1;

                    String profileImagePath = "/";

                    appController.insertMember(lastName, firstName, middleName, gender, birthday, healthStatus, pwdType, isSeniorCitizen, civilStatus, contactNumber, highestEducationalAttainment, occupation, monthlyIncome, isMainRespondent, buildingNum, unitNum, profileImagePath);
                    System.out.println("Member record inserted successfully.");
                } catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
                }
                break;
            case 3:

            try {
                System.out.print("Enter building number: ");
                int buildingNum = InputValidation.readIntFromScanner(scanner);

                System.out.print("Enter unit number: ");
                int unitNum = InputValidation.readIntFromScanner(scanner);

                System.out.print("Enter pet name: ");
                String petName = scanner.nextLine().trim();
                InputValidation.validateStringInput(petName);

                System.out.print("Enter pet species: ");
                String petSpecies = scanner.nextLine().trim();
                InputValidation.validateStringInput(petSpecies);

                appController.insertPet(petName, petSpecies, buildingNum, unitNum);
                System.out.println("Pet record inserted successfully.");
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
            break;

            case 4:
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    public void editProfileMenu() throws SQLException {

        DisplayUtil.displayTableContents(appController.fetchConnection(), "Households");
        DisplayUtil.displayTableContents(appController.fetchConnection(), "Members");

        System.out.println("Edit Profile Menu:");
        System.out.println("1. Edit Household Record");
        System.out.println("2. Edit Member Record");
        System.out.println("3. Back to Main Menu");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                DisplayUtil.displayTableContents(appController.fetchConnection(), "Households");
                DisplayUtil.displayTableContents(appController.fetchConnection(), "Members");
                System.out.print("Enter building number: ");
                int buildingNum = scanner.nextInt();
                System.out.print("Enter unit number: ");
                int unitNum = scanner.nextInt();
                scanner.nextLine(); // consume newline
                System.out.print("Enter the field to edit (monthlyExpenditure, monthlyAmortization, yearOfResidence): ");
                String householdField = scanner.nextLine();
                System.out.print("Enter new value: ");
                Object householdNewValue = getFieldNewValue(householdField);
                appController.editHousehold(buildingNum, unitNum, householdField, householdNewValue);
                System.out.println("Household record updated successfully.");
                break;
            case 2:
                DisplayUtil.displayTableContents(appController.fetchConnection(), "Households");
                DisplayUtil.displayTableContents(appController.fetchConnection(), "Members");
                System.out.print("Enter last name: ");
                String lastName = scanner.nextLine();
                System.out.print("Enter first name: ");
                String firstName = scanner.nextLine();
                System.out.print("Enter middle name: ");
                String middleName = scanner.nextLine();
                System.out.print("Enter building number: ");
                buildingNum = scanner.nextInt();
                System.out.print("Enter unit number: ");
                unitNum = scanner.nextInt();
                scanner.nextLine(); // consume newline
                System.out.print("Enter the field to edit (gender, birthday, healthStatus, pwdType, isSeniorCitizen, civilStatus, contactNumber, highestEducationalAttainment, occupation, monthlyIncome, isMainRespondent): ");
                String memberField = scanner.nextLine();
                System.out.print("Enter new value: ");
                Object memberNewValue = getFieldNewValue(memberField);
                appController.editMember(lastName, firstName, middleName, buildingNum, unitNum, memberField, memberNewValue);
                System.out.println("Member record updated successfully.");
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    public void deleteProfileMenu() throws SQLException {

        DisplayUtil.displayTableContents(appController.fetchConnection(), "Households");
        DisplayUtil.displayTableContents(appController.fetchConnection(), "Members");

        System.out.println("Delete Profile Menu:");
        System.out.println("1. Delete Household Record");
        System.out.println("2. Delete Member Record");
        System.out.println("3. Back to Main Menu");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                DisplayUtil.displayTableContents(appController.fetchConnection(), "Households");
                DisplayUtil.displayTableContents(appController.fetchConnection(), "Members");

                System.out.print("Enter building number: ");
                int buildingNum = scanner.nextInt();
                System.out.print("Enter unit number: ");
                int unitNum = scanner.nextInt();
                appController.deleteHousehold(buildingNum, unitNum);
                System.out.println("Household record deleted successfully.");
                break;
            case 2:

                DisplayUtil.displayTableContents(appController.fetchConnection(), "Households");
                DisplayUtil.displayTableContents(appController.fetchConnection(), "Members");

                System.out.print("Enter last name: ");
                String lastName = scanner.nextLine();
                System.out.print("Enter first name: ");
                String firstName = scanner.nextLine();
                System.out.print("Enter middle name: ");
                String middleName = scanner.nextLine();
                System.out.print("Enter building number: ");
                buildingNum = scanner.nextInt();
                System.out.print("Enter unit number: ");
                unitNum = scanner.nextInt();
                appController.deleteMember(lastName, firstName, middleName, buildingNum, unitNum);
                System.out.println("Member record deleted successfully.");
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    public void searchHouseholdsMenu(){
        System.out.println("Choose Search Criteria:");
        System.out.println("1. Search by Building Number");
        System.out.println("2. Search by Unit Number");
        System.out.println("3. Back to Main Menu");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice){
            case 1:
                try {
                    System.out.println("Input Building Number: ");
                    int buildingNum = InputValidation.readIntFromScanner(scanner);
                    searchController.searchByBuilding(buildingNum);
                } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
                break;
            case 2:
                try {
                    System.out.println("Input Unit Number: ");
                    int unitNum = InputValidation.readIntFromScanner(scanner);
                    searchController.searchByUnit(unitNum);
                } catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
                }
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }


    private Object getFieldNewValue(String field) {
        switch (field) {
            case "monthlyExpenditure":
            case "monthlyAmortization":
            case "monthlyIncome":
                return scanner.nextDouble();
            case "yearOfResidence":
            case "isSeniorCitizen":
            case "isMainRespondent":
                return scanner.nextInt();
            case "birthday":
                return LocalDate.parse(scanner.next());
            default:
                return scanner.next();
        }
    }

    public void closeApp(){
        appController.closeConnection();
    }
}

