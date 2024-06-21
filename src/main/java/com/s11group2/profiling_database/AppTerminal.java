package com.s11group2.profiling_database;


import com.s11group2.profiling_database.Controller.AppController;
import com.s11group2.profiling_database.Util.DisplayUtil;
import com.s11group2.profiling_database.Util.TerminalMenus;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * The main terminal application for interacting with the profiling database.
 */
public class AppTerminal {
    public static void main(String[] args) {
        try {
            TerminalMenus terminalMenus = new TerminalMenus();
            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            while (running) {
                System.out.println("Main Menu:");
                System.out.println("1. Create Tables");
                System.out.println("2. View Profile");
                System.out.println("3. Add Household");
                System.out.println("4. Edit Profile");
                System.out.println("5. Delete Profile");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        terminalMenus.createTablesMenu();
                        break;
                    case 2:
                        terminalMenus.viewProfileMenu();
                        break;
                    case 3:
                        terminalMenus.addHouseholdMenu();
                        break;
                    case 4:
                        terminalMenus.editProfileMenu();
                        break;
                    case 5:
                        terminalMenus.deleteProfileMenu();
                        break;
                    case 6:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }

            terminalMenus.closeApp();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
