package com.example.arek.swdahp;

/**
 * Created by Arek on 2016-12-08.
 */
public class CarSpecification {

    private String modelName;
    private int horsePower;
    private int safetyLevel;
    private int cost;
    private int year;
    private int kilometersDone;
    private int comfort;

    public CarSpecification(String modelName, int horsePower, int safetyLevel, int cost, int year, int comfort, int kilometersDone) {

        this.modelName = modelName;
        this.horsePower = horsePower;
        this.safetyLevel = safetyLevel;
        this.cost = cost;
        this.year = year;
        this.comfort = comfort;
        this.kilometersDone = kilometersDone;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    public void setSafetyLevel(int safetyLevel) {
        this.safetyLevel = safetyLevel;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setComfort(int comfort) {
        this.comfort = comfort;
    }

    public int returnParameter(String theThing) {
        switch (theThing) {
            case "horsePower":
                return this.horsePower;
            case "safetyLevel":
                return this.safetyLevel;
            case "cost":
                return this.cost;
            case  "year":
                return this.year;
            case "comfort":
                return this.comfort;
            case "kilometersDone":
                return this.kilometersDone;
            default:
                return 0;
        }
    }


}
