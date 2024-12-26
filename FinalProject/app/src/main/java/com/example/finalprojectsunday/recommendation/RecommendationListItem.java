package com.example.finalprojectsunday.recommendation;

import com.example.finalprojectsunday.other.NutritionElement;

public class RecommendationListItem {


    final NutritionElement nutritionElement;
    final Float percentage;
    final Integer target;
    final Integer upperLimit;

    public RecommendationListItem(NutritionElement nutritionElement, Float percentage, Integer target, Integer upperLimit) {
        this.nutritionElement = nutritionElement;
        this.percentage = percentage;
        this.target = target;
        this.upperLimit = upperLimit;
    }
}
