package com.s11group2.profiling_database.Model;

public class Household {
    //@EmbeddedId
    //private HouseholdId id;

    private int buildingNum;
    private int unitNum;
    private double monthlyExpenditure;
    private double monthlyAmortization;
    private int yearOfResidence;


    public int getBuildingNum(){
        return buildingNum;
    }

    public void setBuildingNum(int buildingNum){
        this.buildingNum = buildingNum;
    }

    public int getUnitNum(){
        return unitNum;
    }

    public void setUnitNum(int unitNum){
        this.unitNum = unitNum;
    }

    public double getMonthlyExpenditure() {
        return monthlyExpenditure;
    }

    public void setMonthlyExpenditure(double monthlyExpenditure) {
        this.monthlyExpenditure = monthlyExpenditure;
    }

    public double getMonthlyAmortization() {
        return monthlyAmortization;
    }

    public void setMonthlyAmortization(double monthlyAmortization) {
        this.monthlyAmortization = monthlyAmortization;
    }

    public int getYearOfResidence() {
        return yearOfResidence;
    }

    public void setYearOfResidence(int yearOfResidence) {
        this.yearOfResidence = yearOfResidence;
    }
    /*
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Household household = (Household) o;
        return Objects.equals(id, household.id);
    }
*/

}
