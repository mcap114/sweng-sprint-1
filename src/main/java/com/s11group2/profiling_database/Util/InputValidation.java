package com.s11group2.profiling_database.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Utility class for input validation and reading input.
 */
public class InputValidation {

    /**
     * Validates that the input is a valid integer.
     *
     * @param input the input string to validate
     * @return the parsed integer value
     * @throws ValidationException if the input is not a valid integer
     */
    public static int validateAndReadInt(String input) throws ValidationException {
        validateNotEmpty(input);
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid integer value: " + input);
        }
    }

    /**
     * Validates that the input is a valid double.
     *
     * @param input the input string to validate
     * @return the parsed double value
     * @throws ValidationException if the input is not a valid double
     */
    public static double validateAndReadDouble(String input) throws ValidationException {
        validateNotEmpty(input);
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid double value: " + input);
        }
    }

    /**
     * Validates that the input is not empty.
     *
     * @param input the input string to validate
     * @throws ValidationException if the input is empty
     */
    public static void validateNotEmpty(String input) throws ValidationException {
        if (input == null || input.trim().isEmpty()) {
            throw new ValidationException("Input cannot be empty.");
        }
    }

    /**
     * Validates that the input is a valid date.
     *
     * @param input the input string to validate
     * @return the parsed LocalDate object
     * @throws ValidationException if the input is not a valid date
     */
    public static LocalDate validateAndReadDate(String input) throws ValidationException {
        validateNotEmpty(input);
        try {
            return LocalDate.parse(input);
        } catch (DateTimeParseException e) {
            throw new ValidationException("Invalid date format: " + input);
        }
    }

    /**
     * Validates that the input is a valid integer.
     *
     * @param scanner the Scanner object to read input from
     * @return the parsed integer value
     * @throws ValidationException if the input is not a valid integer or is empty
     */
    public static int readIntFromScanner(Scanner scanner) throws ValidationException {
        String input = scanner.nextLine().trim();
        validateNotEmpty(input);
        return validateAndReadInt(input);
    }

    /**
     * Validates that the input is a valid double.
     *
     * @param scanner the Scanner object to read input from
     * @return the parsed double value
     * @throws ValidationException if the input is not a valid double or is empty
     */
    public static double readDoubleFromScanner(Scanner scanner) throws ValidationException {
        String input = scanner.nextLine().trim();
        validateNotEmpty(input);
        return validateAndReadDouble(input);
    }

    /**
     * Validates that the input is a valid date.
     *
     * @param scanner the Scanner object to read input from
     * @return the parsed LocalDate object
     * @throws ValidationException if the input is not a valid date or is empty
     */
    public static LocalDate readDateFromScanner(Scanner scanner) throws ValidationException {
        String input = scanner.nextLine().trim();
        validateNotEmpty(input);
        return validateAndReadDate(input);
    }

    public static void validateStringInput(String input) throws ValidationException {
        if (input == null || input.trim().isEmpty()) {
            throw new ValidationException("Input cannot be empty.");
        }
    }

    public static void validateDateInput(String input) throws ValidationException {
        try {
            LocalDate.parse(input);
        } catch (DateTimeParseException e) {
            throw new ValidationException("Invalid date format: " + input);
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



    /**
     * Calculates the age based on the given birthday.
     *
     * @param birthday the birthday to calculate the age from
     * @return the calculated age
     */
    public static int calculateAge(LocalDate birthday) {
        return LocalDate.now().getYear() - birthday.getYear();
    }

    /**
     * Determines if a person is a senior citizen based on their age.
     *
     * @param age the age of the person
     * @return 1 if the person is a senior citizen (age 65 or older), 0 otherwise
     */
    public static int isSeniorCitizen(int age) {
        return (age >= 65) ? 1 : 0;
    }
}
