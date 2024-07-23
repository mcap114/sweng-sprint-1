package com.s11group2.profiling_database.Model;

import java.util.List;

public class Household {
    //@EmbeddedId
    //private HouseholdId id;

    private int buildingNum;
    private int unitNum;
    private double monthlyExpenditure;
    private double monthlyAmortization;
    private int yearOfResidence;
    private int memberCount;
    private List<Member> members;


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

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public Member getMainRespondent() {
        if (members != null) {
            for (Member member : members) {
                if (member.getIsMainRespondent() != null && member.getIsMainRespondent() == 1) {
                    return member;
                }
            }
        }
        return null;
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
