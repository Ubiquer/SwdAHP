package com.example.arek.swdahp;

/**
 * Created by Arek on 2016-12-08.
 */
public class CarSpecification {

    private String companyName;
    private String horsePower;
    private int safetyLevel;
    private int doorAmount;
    private int minCost;
    private int maxCost;

    public CarSpecification(String companyName, String horsePower, int safetyLevel, int doorAmount, int minCost, int maxCost) {
        this.companyName = companyName;
        this.horsePower = horsePower;
        this.safetyLevel = safetyLevel;
        this.doorAmount = doorAmount;
        this.minCost = minCost;
        this.maxCost = maxCost;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(String horsePower) {
        this.horsePower = horsePower;
    }

    public int getSafetyLevel() {
        return safetyLevel;
    }

    public void setSafetyLevel(int safetyLevel) {
        this.safetyLevel = safetyLevel;
    }

    public int getDoorAmount() {
        return doorAmount;
    }

    public void setDoorAmount(int doorAmount) {
        this.doorAmount = doorAmount;
    }

    public int getMinCost() {
        return minCost;
    }

    public void setMinCost(int minCost) {
        this.minCost = minCost;
    }

    public int getMaxCost() {
        return maxCost;
    }

    public void setMaxCost(int maxCost) {
        this.maxCost = maxCost;
    }
}
