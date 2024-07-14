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
            @RequestParam("resLastName") String[] lastName,
            @RequestParam("resFirstName") String[] firstName,
            @RequestParam("resMiddleName") String[] middleName,
            @RequestParam("resGender") String[] gender,
            @RequestParam("resBirthday") String[] birthday,
            @RequestParam("resHealthStatus") String[] healthStatus,
            @RequestParam("resPwdType") String[] pwdType,
            @RequestParam("resCivilStatus") String[] civilStatus,
            @RequestParam("resContactNumber") String[] contactNumber,
            @RequestParam("resHighestEducationalAttainment") String[] highestEducationalAttainment,
            @RequestParam("resOccupation") String[] occupation,
            @RequestParam("resMonthlyIncome") Double[] monthlyIncome,
            @RequestParam("buildingNum") Integer buildingNum,
            @RequestParam("unitNum") Integer unitNum,
            @RequestParam("monthlyExpenditure") Double monthlyExpenditure,
            @RequestParam("monthlyAmortization") Double monthlyAmortization,
            @RequestParam("yearOfResidence") Integer yearOfResidence,
            @RequestParam("resPfp") MultipartFile[] profileImage,
            @RequestParam("petName") String[] petName,
            @RequestParam("petAnimalType") String[] petSpecies,
            Model model) {

        try {
            // Handle file upload
            // TODO: fix method of storing file path
            // String uploadDirectory = "src/main/resources/static/images/";
                
            
            // adsImagesString += imageService.saveImageToStorage(uploadDirectory, profileImage);
        
            
            // by default, the initial member added is the main respondent
            int isMainRespondent = 1;

            // Parse birthday
            LocalDate birthDate = LocalDate.parse(birthday[0]);
            int age = InputValidation.calculateAge(birthDate);
            int isSeniorCitizen = InputValidation.isSeniorCitizen(age);

            // Insert member with profile image path
            dbManager.insertHousehold(buildingNum, unitNum, monthlyExpenditure, monthlyAmortization, yearOfResidence);
            dbManager.insertMember(
                    lastName[0], firstName[0], middleName[0], gender[0], birthDate, healthStatus[0], pwdType[0], isSeniorCitizen,
                    civilStatus[0], contactNumber[0], highestEducationalAttainment[0], occupation[0], monthlyIncome[0], isMainRespondent,
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
                        civilStatus[i], contactNumber[i], highestEducationalAttainment[i], occupation[i], monthlyIncome[i], isMainRespondent,
                        buildingNum, unitNum, profileImage[i]
                );
            }

            for (int i = 0; i < petName.length; i++) {
                dbManager.insertPet(petName[i], petSpecies[i], buildingNum, unitNum);
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
