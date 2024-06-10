package com.s11group2.profiling_database.Util;

import java.time.LocalDate;
import java.time.Period;

/**
 * utility class for input validation and checking
 */
public class InputValidation {

     // checks if a person is a senior citizen based on their age
    public static int isSeniorCitizen(int age) {
        return (age >= 65) ? 1 : 0;
    }

     // calculates age based on the birth date
    public static int calculateAge(LocalDate birthday) {
        return Period.between(birthday, LocalDate.now()).getYears();
    }

    // validates if a given string can be parsed into a valid double
    public static boolean isValidDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

     // validates if a given string can be parsed into a valid double
    public static boolean isValidInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

 
}