package com.s11group2.profiling_database.Controller;

import com.s11group2.profiling_database.Model.DatabaseManager;
import com.s11group2.profiling_database.Model.Household;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class ViewSearchController {
    private DatabaseManager databaseManager;

    public ViewSearchController() {
        try {
            this.databaseManager = new DatabaseManager();
        } catch (SQLException e) {
            Logger.getLogger(ViewSearchController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @GetMapping("/viewunits")
    public String showHouseholds(Model model) {
        try {
            List<Household> households = databaseManager.getAllHouseholds();
            System.out.println("Number of households: " + households.size()); // Debugging statement
            model.addAttribute("households", households);
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Failed to retrieve households from database.");
        }
        return "viewunits";
    }
}
