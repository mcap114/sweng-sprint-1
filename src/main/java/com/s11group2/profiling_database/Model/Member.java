package com.s11group2.profiling_database.Model;

import java.time.LocalDate;

public class Member {
    private String lastName;
    private String firstName;
    private String middleName;
    private String gender;
    private LocalDate birthday;
    private String healthStatus;
    private String pwdType;
    private Integer isSeniorCitizen;
    private String civilStatus;
    private String contactNumber;
    private String highestEducationalAttainment;
    private String occupation;
    private Double monthlyIncome;
    private Integer isMainRespondent;
    private Integer buildingNum;
    private Integer unitNum;
    private String profileImagePath;

    // Constructors
    public Member() {
    }

    public Member(String lastName, String firstName, String middleName, String gender, LocalDate birthday,
                  String healthStatus, String pwdType, Integer isSeniorCitizen, String civilStatus,
                  String contactNumber, String highestEducationalAttainment, String occupation,
                  Double monthlyIncome, Integer isMainRespondent, Integer buildingNum, Integer unitNum,
                  String profileImagePath) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.gender = gender;
        this.birthday = birthday;
        this.healthStatus = healthStatus;
        this.pwdType = pwdType;
        this.isSeniorCitizen = isSeniorCitizen;
        this.civilStatus = civilStatus;
        this.contactNumber = contactNumber;
        this.highestEducationalAttainment = highestEducationalAttainment;
        this.occupation = occupation;
        this.monthlyIncome = monthlyIncome;
        this.isMainRespondent = isMainRespondent;
        this.buildingNum = buildingNum;
        this.unitNum = unitNum;
        this.profileImagePath = profileImagePath;
    }

    // Getters and Setters
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public String getPwdType() {
        return pwdType;
    }

    public void setPwdType(String pwdType) {
        this.pwdType = pwdType;
    }

    public Integer getIsSeniorCitizen() {
        return isSeniorCitizen;
    }

    public void setIsSeniorCitizen(Integer isSeniorCitizen) {
        this.isSeniorCitizen = isSeniorCitizen;
    }

    public String getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getHighestEducationalAttainment() {
        return highestEducationalAttainment;
    }

    public void setHighestEducationalAttainment(String highestEducationalAttainment) {
        this.highestEducationalAttainment = highestEducationalAttainment;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Double getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(Double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public Integer getIsMainRespondent() {
        return isMainRespondent;
    }

    public void setIsMainRespondent(Integer isMainRespondent) {
        this.isMainRespondent = isMainRespondent;
    }

    public Integer getBuildingNum() {
        return buildingNum;
    }

    public void setBuildingNum(Integer buildingNum) {
        this.buildingNum = buildingNum;
    }

    public Integer getUnitNum() {
        return unitNum;
    }

    public void setUnitNum(Integer unitNum) {
        this.unitNum = unitNum;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }


}
