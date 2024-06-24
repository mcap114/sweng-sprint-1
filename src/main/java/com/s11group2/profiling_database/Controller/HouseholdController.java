package com.s11group2.profiling_database.Controller;

import com.s11group2.profiling_database.AppTerminal;
import com.s11group2.profiling_database.Model.DatabaseManager;
import com.s11group2.profiling_database.Util.InputValidation;
import org.springframework.beans.factory.annotation.Value;
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

@Controller
public class HouseholdController {

    @Value("${upload.path}")
    private String uploadPath;

    private AppController appController;

    public HouseholdController() {
        this.appController = new AppController();
    }

    @GetMapping("/addhousehold")
    public String registerPage() {
        return "addhouseholdpage";  // Mustache will look for addhouseholdpage.html in the templates folder
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
            @RequestParam("resPfp") MultipartFile profileImage,
            Model model) {

        try {
            // Handle file upload
            String fileName = profileImage.getOriginalFilename();
            File dest = new File(uploadPath + "/" + fileName);
            profileImage.transferTo(dest);
            String profileImagePath = "/images/" + fileName;

            // by default, the initial member added is the main respondent
            int isMainRespondent = 1;

            // Parse birthday
            LocalDate birthDate = LocalDate.parse(birthday);
            int age = InputValidation.calculateAge(birthDate);
            int isSeniorCitizen = InputValidation.isSeniorCitizen(age);

            // Insert member with profile image path
            appController.insertHousehold(buildingNum, unitNum, monthlyExpenditure, monthlyAmortization, yearOfResidence);
            appController.insertMember(
                    lastName, firstName, middleName, gender, birthDate, healthStatus, pwdType, isSeniorCitizen,
                    civilStatus, contactNumber, highestEducationalAttainment, occupation, monthlyIncome, isMainRespondent,
                    buildingNum, unitNum, profileImagePath
            );

            model.addAttribute("message", "Household registered successfully.");

            return "redirect:/index";
        } catch (IOException e) {
            model.addAttribute("message", "Failed to register household: " + e.getMessage());
            return "addhouseholdpage";
        }

    }
}
