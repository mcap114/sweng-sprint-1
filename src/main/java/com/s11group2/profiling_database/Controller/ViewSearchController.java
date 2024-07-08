package com.s11group2.profiling_database.Controller;

import com.s11group2.profiling_database.Model.DatabaseManager;
import com.s11group2.profiling_database.Model.Household;
import com.s11group2.profiling_database.Model.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

            for (Household household : households) {
                Member mainRespondent = databaseManager.getMainRespondent(household.getBuildingNum(), household.getUnitNum());
                household.setMembers(mainRespondent != null ? List.of(mainRespondent) : List.of());
            }

            model.addAttribute("households", households);
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Failed to retrieve households from database.");
        }
        return "viewunits";
    }

    @GetMapping("/viewaunit/{buildingNum}/{unitNum}")
    public String showHouseholdInfo(@PathVariable int buildingNum, @PathVariable int unitNum, Model model) {
        try {
            Household household = databaseManager.getHousehold(buildingNum, unitNum);
            if (household != null) {
                //List<Member> members = databaseManager.getMembersByHousehold(buildingNum, unitNum);
                model.addAttribute("household", household);
                model.addAttribute("mainRespondent", databaseManager.getMainRespondent(buildingNum, unitNum));
                model.addAttribute("otherMembers", databaseManager.getAllMembers());
                return "viewaunit";
            } else {
                model.addAttribute("errorMessage", "Household not found for buildingNum: " + buildingNum + " and unitNum: " + unitNum);
                return "error";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Failed to retrieve household information.");
            return "error";
        }
    }



}
