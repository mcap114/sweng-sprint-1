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

import java.sql.SQLException;
import java.util.List;

@Controller
public class ViewUnitController {

    private final DatabaseManager databaseManager;

    @Autowired
    public ViewUnitController(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @GetMapping("/viewaunit/{buildingNum}/{unitNum}")
    public String showHouseholdInfo(@PathVariable("buildingNum") int buildingNum, @PathVariable("unitNum") int unitNum, Model model) {
        try {
            Household household = databaseManager.getHousehold(buildingNum, unitNum);
            if (household != null) {
                Member mainRespondent = databaseManager.getMainRespondent(buildingNum, unitNum);
                List<Member> additionalMembers = databaseManager.getMembersByHousehold(buildingNum, unitNum);
                List<Pet> pets = databaseManager.getPetsByHousehold(buildingNum, unitNum);

                model.addAttribute("household", household);
                model.addAttribute("mainRespondent", mainRespondent);
                model.addAttribute("otherMembers", additionalMembers);
                model.addAttribute("pets", pets);
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
