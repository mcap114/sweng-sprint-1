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
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

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
            @RequestParam(value="originalLastName") String[] originalLastName,
            @RequestParam(value="originalFirstName") String[] originalFirstName,
            @RequestParam(value="originalMiddleName") String[] originalMiddleName,
            @RequestParam(value="originalBuildingNum") Integer originalBuildingNum,
            @RequestParam(value="originalUnitNum") Integer originalUnitNum,
            @RequestParam(value="originalPetName", required=false) String[] originalPetName,
            @RequestParam(value= "originalPetAnimalType", required=false) String[] originalPetAnimalType,
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
            @RequestParam(value="originalProfileImagePath", required=false) String[] originalProfileImagePath,
            @RequestParam(value="petName", required=false) String[] petName,
            @RequestParam(value="petAnimalType", required=false) String[] petAnimalType,
            @RequestParam(value="petPfp", required=false) MultipartFile[] petImages,
            @RequestParam(value="originalPetImagePath", required=false) String[] originalPetImagePath,
            Model model) throws IOException {

        try {
            dbManager.editHousehold(originalBuildingNum, originalUnitNum, "monthlyExpenditure", monthlyExpenditure);
            dbManager.editHousehold(originalBuildingNum, originalUnitNum, "monthlyAmortization", monthlyAmortization);
            dbManager.editHousehold(originalBuildingNum, originalUnitNum, "yearOfResidence", yearOfResidence);

            //iterate thru all members, assumes main respondent is index 0
            for (int i = 0; i < lastName.length; i++) {
                LocalDate birthDate = LocalDate.parse(birthday[i]);
                int age = InputValidation.calculateAge(birthDate);
                int isSeniorCitizen = InputValidation.isSeniorCitizen(age);

                //edit member information
                dbManager.editMember(originalLastName[i], originalFirstName[i], originalMiddleName[i], originalBuildingNum, originalUnitNum, "lastName", lastName[i]);
                dbManager.editMember(lastName[i], originalFirstName[i], originalMiddleName[i], originalBuildingNum, originalUnitNum, "firstName", firstName[i]);
                dbManager.editMember(lastName[i], firstName[i], originalMiddleName[i], originalBuildingNum, originalUnitNum, "middleName", middleName[i]);
                dbManager.editMember(lastName[i], firstName[i], middleName[i], originalBuildingNum, originalUnitNum, "gender", gender[i]);
                dbManager.editMember(lastName[i], firstName[i], middleName[i], originalBuildingNum, originalUnitNum, "birthday", birthDate);
                dbManager.editMember(lastName[i], firstName[i], middleName[i], originalBuildingNum, originalUnitNum, "healthStatus", healthStatus[i]);
                dbManager.editMember(lastName[i], firstName[i], middleName[i], originalBuildingNum, originalUnitNum, "pwdType", pwdType[i]);
                dbManager.editMember(lastName[i], firstName[i], middleName[i], originalBuildingNum, originalUnitNum, "isSeniorCitizen", isSeniorCitizen);
                dbManager.editMember(lastName[i], firstName[i], middleName[i], originalBuildingNum, originalUnitNum, "civilStatus", civilStatus[i]);
                dbManager.editMember(lastName[i], firstName[i], middleName[i], originalBuildingNum, originalUnitNum, "contactNumber", contactNumber[i]);
                dbManager.editMember(lastName[i], firstName[i], middleName[i], originalBuildingNum, originalUnitNum, "highestEducationalAttainment", highestEducationalAttainment[i]);
                dbManager.editMember(lastName[i], firstName[i], middleName[i], originalBuildingNum, originalUnitNum, "occupation", occupation[i]);
                dbManager.editMember(lastName[i], firstName[i], middleName[i], originalBuildingNum, originalUnitNum, "monthlyIncome", monthlyIncome[i]);
                
                dbManager.editMember(lastName[i], firstName[i], middleName[i], originalBuildingNum, originalUnitNum, "buildingNum", buildingNum);
                dbManager.editMember(lastName[i], firstName[i], middleName[i], originalBuildingNum, originalUnitNum, "unitNum", unitNum);

                if (i > 0) {
                    dbManager.editMember(lastName[i], firstName[i], middleName[i], originalBuildingNum, originalUnitNum, "relationToMainRespondent", relationToMainRespondent[i - 1]);
                }

                if (!profileImages[i].getOriginalFilename().equals("")) {
                    dbManager.editMemberImage(lastName[i], firstName[i], middleName[i], originalBuildingNum, originalUnitNum, profileImages[i], originalProfileImagePath[i]);
                }
            }

            if (petName != null) {
                for (int i = 0; i < petName.length; i++) {
                    dbManager.editPet(originalPetName[i], originalPetAnimalType[i], buildingNum, unitNum, "petName", petName[i]);
                    dbManager.editPet(petName[i], originalPetAnimalType[i], buildingNum, unitNum, "petSpecies", petAnimalType[i]);

                    if (!petImages[i].getOriginalFilename().equals("")) {
                        dbManager.editPetImage(petName[i], petAnimalType[i], buildingNum, unitNum, petImages[i], originalPetImagePath[i]);
                    }
                }
            }

            model.addAttribute("message", "Household updated successfully!");
            return "redirect:/viewaunit/" + buildingNum + "/" + unitNum;
        } catch (SQLException e) {
            model.addAttribute("errorMessage", "Failed to update household: " + e.getMessage());
            return "error";
        }
    }

    }