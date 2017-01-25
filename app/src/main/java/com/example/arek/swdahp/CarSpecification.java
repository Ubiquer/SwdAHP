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

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }
    //TODO: wyrzucic gettery powyżej i zrobić na switchu resztę, potrzebne do dynamicznego wywoływania metod w klasie budującej macierze kryteriów decyzji
    public int returnParameter(String theThing) {
        switch (theThing) {
            case "horsePower":
                return this.horsePower;

            default:
                return 0;
        }
    }


}
