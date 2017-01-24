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
    private int fuel;

    public CarSpecification(String modelName, int horsePower, int safetyLevel, int cost, int year, int fuel, int kilometersDone) {

        this.modelName = modelName;
        this.horsePower = horsePower;
        this.safetyLevel = safetyLevel;
        this.cost = cost;
        this.year = year;
        this.fuel = fuel;
        this.kilometersDone = kilometersDone;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

//    public int getHorsePower() {
//        return horsePower;
//    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

//    public int getSafetyLevel() {
//        return safetyLevel;
//    }

    public void setSafetyLevel(int safetyLevel) {
        this.safetyLevel = safetyLevel;
    }

//    public int getCost() {
//        return cost;
//    }

    public void setCost(int cost) {
        this.cost = cost;
    }

//    public int getYear() {
//        return year;
//    }

    public void setYear(int year) {
        this.year = year;
    }

    public int isFuel() {
        return fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }
    //TODO: wyrzucic gettery powyżej i zrobić na switchu resztę, potrzebne do dynamicznego wywoływania metod w klasie budującej macierze kryteriów decyzji
    public float returnParameter(String theThing) {
        switch (theThing) {
            case "horsePower":
                return this.horsePower;
            case "safetyLevel":
                return this.safetyLevel;
            case "cost":
                return this.cost;
            case "year":
                return this.year;
            case "fuel":
                return this.fuel;
            case "kilometersDone":
                return this.kilometersDone;
            default:
                return 0;
        }
    }


}
