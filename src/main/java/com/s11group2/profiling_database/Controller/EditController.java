package com.s11group2.profiling_database.Controller;

import com.s11group2.profiling_database.Model.DatabaseManager;
import com.s11group2.profiling_database.Model.Household;
import com.s11group2.profiling_database.Model.Member;
import com.s11group2.profiling_database.Model.Pet;
import com.s11group2.profiling_database.Util.InputValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class EditController {

    private final DatabaseManager dbManager;

    @Autowired
    public EditController(DatabaseManager databaseManager) {
        this.dbManager = databaseManager;
    }


    @GetMapping("/edithousehold/{buildingNum}/{unitNum}")
    public String editPage(@PathVariable("buildingNum") int buildingNum, @PathVariable("unitNum") int unitNum, Model model) throws SQLException {
        Household household = dbManager.getHousehold(buildingNum, unitNum);
        Member mainRespondent = dbManager.getMainRespondent(buildingNum, unitNum);
        List<Member> additionalMembers = dbManager.getMembersByHousehold(buildingNum, unitNum);
        List<Pet> pets = dbManager.getPetsByHousehold(buildingNum, unitNum);

        model.addAttribute("household", household);
        model.addAttribute("mainRespondent", mainRespondent);
        model.addAttribute("otherMembers", additionalMembers);
        model.addAttribute("pets", pets);
        return "edithousehold";
    }

    @PostMapping("/edit")
    public String updateHousehold(
            @RequestParam(value="resLastName") String[] lastName,
            @RequestParam(value="resFirstName") String[] firstName,
            @RequestParam(value="resMiddleName") String[] middleName,
            @RequestParam(value="resGender") String[] gender,
            @RequestParam(value="resBirthday") String[] birthday,
            @RequestParam(value="resHealthStatus") String[] healthStatus,
            @RequestParam(value="resPwdType") String[] pwdType,
            @RequestParam(value="resCivilStatus") String[] civilStatus,
            @RequestParam(value="resContactNumber") String[] contactNumber,
            @RequestParam(value="resHighestEducationalAttainment") String[] highestEducationalAttainment,
            @RequestParam(value="resOccupation") String[] occupation,
            @RequestParam(value="resMonthlyIncome") Double[] monthlyIncome,
            @RequestParam(value="resRelationToRespondent", required=false) String[] relationToMainRespondent,
            @RequestParam(value="buildingNum") Integer buildingNum,
            @RequestParam(value="unitNum") Integer unitNum,
            @RequestParam(value="monthlyExpenditure") Double monthlyExpenditure,
            @RequestParam(value="monthlyAmortization") Double monthlyAmortization,
            @RequestParam(value="yearOfResidence") Integer yearOfResidence,
            @RequestParam(value="resPfp", required=false) MultipartFile[] profileImages,
            @RequestParam(value="petName", required=false) String[] petName,
            @RequestParam(value="petAnimalType", required=false) String[] petSpecies,
            @RequestParam(value="petPfp", required=false) MultipartFile[] petImages,
            Model model) {

        try {
            dbManager.editHousehold(buildingNum, unitNum, "monthlyExpenditure", monthlyExpenditure);
            dbManager.editHousehold(buildingNum, unitNum, "monthlyAmortization", monthlyAmortization);
            dbManager.editHousehold(buildingNum, unitNum, "yearOfResidence", yearOfResidence);

            //iterate thru all members, assumes main respondent is index 0
            for (int i = 0; i < lastName.length; i++) {
                LocalDate birthDate = LocalDate.parse(birthday[i]);
                int age = InputValidation.calculateAge(birthDate);
                int isSeniorCitizen = InputValidation.isSeniorCitizen(age);

                //edit member information
                dbManager.editMember(lastName[i], firstName[i], middleName[i], buildingNum, unitNum, "gender", gender[i]);
                dbManager.editMember(lastName[i], firstName[i], middleName[i], buildingNum, unitNum, "birthday", birthDate);
                dbManager.editMember(lastName[i], firstName[i], middleName[i], buildingNum, unitNum, "healthStatus", healthStatus[i]);
                dbManager.editMember(lastName[i], firstName[i], middleName[i], buildingNum, unitNum, "pwdType", pwdType[i]);
                dbManager.editMember(lastName[i], firstName[i], middleName[i], buildingNum, unitNum, "isSeniorCitizen", isSeniorCitizen);
                dbManager.editMember(lastName[i], firstName[i], middleName[i], buildingNum, unitNum, "civilStatus", civilStatus[i]);
                dbManager.editMember(lastName[i], firstName[i], middleName[i], buildingNum, unitNum, "contactNumber", contactNumber[i]);
                dbManager.editMember(lastName[i], firstName[i], middleName[i], buildingNum, unitNum, "highestEducationalAttainment", highestEducationalAttainment[i]);
                dbManager.editMember(lastName[i], firstName[i], middleName[i], buildingNum, unitNum, "occupation", occupation[i]);
                dbManager.editMember(lastName[i], firstName[i], middleName[i], buildingNum, unitNum, "monthlyIncome", monthlyIncome[i]);

                if (i > 0 && relationToMainRespondent != null && i <= relationToMainRespondent.length) {
                    dbManager.editMember(lastName[i], firstName[i], middleName[i], buildingNum, unitNum, "relationToMainRespondent", relationToMainRespondent[i - 1]);
                }

                if (profileImages != null && i < profileImages.length) {
                    dbManager.editMember(lastName[i], firstName[i], middleName[i], buildingNum, unitNum, "profileImagePath", profileImages[i].getOriginalFilename());
                }
            }

            if (petName != null) {
                for (int i = 0; i < petName.length; i++) {
                    dbManager.editPet(petName[i], petSpecies[i], buildingNum, unitNum, "petImagePath", petImages[i].getOriginalFilename());
                }
            }

            model.addAttribute("message", "Household updated successfully!");
            return "index";
        } catch (SQLException e) {
            model.addAttribute("errorMessage", "Failed to update household: " + e.getMessage());
            return "error";
        }
    }

    }
