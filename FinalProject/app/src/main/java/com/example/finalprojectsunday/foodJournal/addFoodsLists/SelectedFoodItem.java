package com.example.finalprojectsunday.foodJournal.addFoodsLists;

import com.example.finalprojectsunday.other.Food;
import com.example.finalprojectsunday.other.PortionType;

public class SelectedFoodItem {
    public final Food food;

    public SelectedFoodItem(Food f, double amount, PortionType portionTypes){
        this.food = f;
        this.food.associatedAmount = amount;
        this.food.associatedPortionType = portionTypes;
    }
}
