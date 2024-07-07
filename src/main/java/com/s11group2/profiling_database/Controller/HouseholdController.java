package com.s11group2.profiling_database.Controller;

import com.s11group2.profiling_database.Model.DatabaseManager;
import com.s11group2.profiling_database.Util.InputValidation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
            @RequestParam("resLastName") String lastName,
            @RequestParam("resFirstName") String firstName,
            @RequestParam("resMiddleName") String middleName,
            @RequestParam("resGender") String gender,
            @RequestParam("resBirthday") String birthday,
            @RequestParam("resHealthStatus") String healthStatus,
            @RequestParam("resPwdType") String pwdType,
            @RequestParam("resCivilStatus") String civilStatus,
            @RequestParam("resContactNumber") String contactNumber,
            @RequestParam("resHighestEducationalAttainment") String highestEducationalAttainment,
            @RequestParam("resOccupation") String occupation,
            @RequestParam("resMonthlyIncome") Double monthlyIncome,
            @RequestParam("buildingNum") Integer buildingNum,
            @RequestParam("unitNum") Integer unitNum,
            @RequestParam("monthlyExpenditure") Double monthlyExpenditure,
            @RequestParam("monthlyAmortization") Double monthlyAmortization,
            @RequestParam("yearOfResidence") Integer yearOfResidence,
            @RequestParam("resPfp") File profileImage,
            Model model) {

        try {

            
            // by default, the initial member added is the main respondent
            int isMainRespondent = 1;

            // Parse birthday
            LocalDate birthDate = LocalDate.parse(birthday);
            int age = InputValidation.calculateAge(birthDate);
            int isSeniorCitizen = InputValidation.isSeniorCitizen(age);

            // Insert member with profile image path
            dbManager.insertHousehold(buildingNum, unitNum, monthlyExpenditure, monthlyAmortization, yearOfResidence);
            dbManager.insertMember(
                    lastName, firstName, middleName, gender, birthDate, healthStatus, pwdType, isSeniorCitizen,
                    civilStatus, contactNumber, highestEducationalAttainment, occupation, monthlyIncome, isMainRespondent,
                    buildingNum, unitNum, profileImage
            );

           
            return "index";
        } catch (SQLException e) {
            System.out.println("Failed to register household: " + e.getMessage());
            return "addhouseholdpage";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
