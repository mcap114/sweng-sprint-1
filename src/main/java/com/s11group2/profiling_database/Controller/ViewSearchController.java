package com.s11group2.profiling_database.Controller;

import com.s11group2.profiling_database.Model.DatabaseManager;
import com.s11group2.profiling_database.Model.Household;
import com.s11group2.profiling_database.Model.Member;
import com.s11group2.profiling_database.Model.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.List;

@Controller
public class ViewSearchController {
    private final DatabaseManager databaseManager;

    @Autowired
    public ViewSearchController(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @GetMapping("/viewunits")
    public String showHouseholds(Model model) {
        try {
            List<Household> households = databaseManager.getAllHouseholds();

            for (Household household : households) {
                Member mainRespondent = databaseManager.getMainRespondent(household.getBuildingNum(), household.getUnitNum());
                int memberCount = household.getMembers().size() + 1;
                household.setMemberCount(memberCount);
                household.setMembers(mainRespondent != null ? List.of(mainRespondent) : List.of());
            }

            model.addAttribute("households", households);
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Failed to retrieve households from database.");
        }
        return "viewunits";
    }

    @GetMapping("/search")
    public String searchHouseholds(@RequestParam("searchTerm") String searchTerm, Model model) {
        try {
            List<Household> households = databaseManager.searchHouseholds(searchTerm);

            for (Household household : households) {
                Member mainRespondent = databaseManager.getMainRespondent(household.getBuildingNum(), household.getUnitNum());
                int memberCount = household.getMembers().size() + 1;
                household.setMemberCount(memberCount);
                household.setMembers(mainRespondent != null ? List.of(mainRespondent) : List.of());
            }

            model.addAttribute("households", households);
            return "viewunits";
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Failed to retrieve households.");
            return "error";
        }
    }
    



    




}
