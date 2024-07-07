package com.s11group2.profiling_database.Model;

import java.io.Serializable;
import jakarta.persistence.Embeddable;
import java.util.Objects;

// reference for this implementation: https://www.baeldung.com/jpa-composite-primary-keys

@Embeddable
public class HouseholdId implements Serializable {
    private int buildingNum;
    private int unitNum;

    // Getters and setters
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

    // hashCode and equals methods
    @Override
    public int hashCode() {
        return Objects.hash(buildingNum, unitNum);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HouseholdId that = (HouseholdId) o;
        return buildingNum == that.buildingNum &&
                unitNum == that.unitNum;
    }
}
