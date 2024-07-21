package com.s11group2.profiling_database.Model;
public class Pet {
    private String petName;
    private String petSpecies;
    private int buildingNum;
    private int unitNum;
    private String petImagePath;

    public Pet(String petName, String petSpecies, int buildingNum, int unitNum, String petImagePath) {
        this.petName = petName;
        this.petSpecies = petSpecies;
        this.buildingNum = buildingNum;
        this.unitNum = unitNum;
        this.petImagePath = petImagePath;
    }

    // Getters and Setters
    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetSpecies() {
        return petSpecies;
    }

    public void setPetSpecies(String petSpecies) {
        this.petSpecies = petSpecies;
    }

    public int getBuildingNum() {
        return buildingNum;
    }

    public void setBuildingNum(int buildingNum) {
        this.buildingNum = buildingNum;
    }

    public int getUnitNum() {
        return unitNum;
    }

    public void setUnitNum(int unitNum) {
        this.unitNum = unitNum;
    }

    public String getPetImagePath() {
        return petImagePath;
    }

    public void setPetImagePath(String petImagePath) {
        this.petImagePath = petImagePath;
    }
}
