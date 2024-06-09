package com.s11group2.profiling_database;

import com.s11group2.profiling_database.Controller.AppController;
import com.s11group2.profiling_database.View.MainAppFrame;

import java.time.LocalDate;

public class App {
    public static void main(String[] args) {
        AppController controller = new AppController();

        // creates the database tables
        // controller.createTables();

        // inserts sample data
        // controller.insertHousehold(2, 3, 19.99, 19.99, 2020);
        // controller.insertMember("Cruz", "Juan Dela", "Yu", "Male", LocalDate.of(2004, 4, 2), "Healthy", "Not PWD", 0, "Single", "0917 111 1111", "College", "Student", 0.0, 1, 2, 3);

        // closes the connection when done
        controller.closeConnection();

        // launches GUI (WIP)
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainAppFrame().setVisible(true);
            }
        });
    }
}
