package com.s11group2.profiling_database.Controller;

import com.s11group2.profiling_database.Model.DatabaseManager;
import com.s11group2.profiling_database.Util.InputValidation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class HouseholdController {

    private DatabaseManager dbManager;

    public HouseholdController() {
        try {
            this.dbManager = new DatabaseManager();
        } catch (SQLException e) {
            Logger.getLogger(HouseholdController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @GetMapping("/addhousehold")
    public String registerPage() {
        return "addhouseholdpage";
    }

    @PostMapping("/addhousehold")
    public String registerMember(
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
            @RequestParam(value="resPfp") MultipartFile[] profileImage,
            @RequestParam(value="petName", required=false) String[] petName,
            @RequestParam(value="petAnimalType", required=false) String[] petSpecies,
            @RequestParam(value="resPfp", required=false) MultipartFile[] petImage,
            Model model) {

        try {
            // Handle file upload
            // TODO: fix method of storing file path
            // String uploadDirectory = "src/main/resources/static/images/";
                
            
            // adsImagesString += imageService.saveImageToStorage(uploadDirectory, profileImage);
        
            
            // by default, the initial member added is the main respondent
            int isMainRespondent = 1;
            String nullString = "";

            // Parse birthday
            LocalDate birthDate = LocalDate.parse(birthday[0]);
            int age = InputValidation.calculateAge(birthDate);
            int isSeniorCitizen = InputValidation.isSeniorCitizen(age);

            // Insert member with profile image path
            dbManager.insertHousehold(buildingNum, unitNum, monthlyExpenditure, monthlyAmortization, yearOfResidence);
            dbManager.insertMember(
                    lastName[0], firstName[0], middleName[0], gender[0], birthDate, healthStatus[0], pwdType[0], isSeniorCitizen,
                    civilStatus[0], contactNumber[0], highestEducationalAttainment[0], occupation[0], monthlyIncome[0], isMainRespondent, nullString,
                    buildingNum, unitNum, profileImage[0]
            );

            isMainRespondent = 0;

            for (int i = 1; i < lastName.length; i++) {
                // Parse birthday
                birthDate = LocalDate.parse(birthday[i]);
                age = InputValidation.calculateAge(birthDate);
                isSeniorCitizen = InputValidation.isSeniorCitizen(age);

                // Insert member with profile image path
                dbManager.insertMember(
                        lastName[i], firstName[i], middleName[i], gender[i], birthDate, healthStatus[i], pwdType[i], isSeniorCitizen,
                        civilStatus[i], contactNumber[i], highestEducationalAttainment[i], occupation[i], monthlyIncome[i], isMainRespondent, relationToMainRespondent[i-1],
                        buildingNum, unitNum, profileImage[i]
                );
            }

            if (petName != null) {
                for (int i = 0; i < petName.length; i++) {
                    dbManager.insertPet(petName[i], petSpecies[i], buildingNum, unitNum, petImage[i]);
                }
            }

            return "index";
        } catch (SQLException e) {
            System.out.println("Failed to register household: " + e.getMessage());
            return "addhouseholdpage";
        } catch (IOException e) {
            System.out.println("Failed to register household: " + e.getMessage());
            return "addhouseholdpage";
        }

    }
}
