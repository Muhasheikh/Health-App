package com.example.finalprojectsunday;

public class data {
    String meal;
    String Totalmeal;
    int totalnutertion;
    int nutertion;


    public String getMeal() {
        return meal;
    }

    public int getNutertion() {
        return nutertion;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public void setNutertion(int nutertion) {
        this.nutertion = nutertion;
    }

    public data(String meal, int nutertion) {
        this.meal = meal;
        this.nutertion = nutertion;
    }


}
