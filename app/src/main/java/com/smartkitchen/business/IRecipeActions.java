package com.smartkitchen.business;

import com.smartkitchen.objects.Recipe;

import java.util.ArrayList;

public interface IRecipeActions {

    void addToRecipes(Recipe recipe);

    void updateRecipe(Recipe recipe);

    Recipe getRecipe(int position);

    ArrayList<Recipe> getRecipeList();

    void removeRecipe(Recipe recipe);

    int calculateTotalCalories(Recipe recipe);

    boolean hasAllIngredients(Recipe recipe);

    void refreshAvailability();
}
