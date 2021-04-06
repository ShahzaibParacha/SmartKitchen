package com.smartkitchen.business.interfaces;

import com.smartkitchen.business.InvalidInputException;
import com.smartkitchen.objects.Recipe;

import java.util.ArrayList;

//Interface to recipe methods
public interface IRecipeActions {

    void addToRecipes(Recipe recipe) throws InvalidInputException;

    void updateRecipe(Recipe recipe);

    ArrayList<Recipe> getRecipeList();

    void removeRecipe(Recipe recipe);

    int calculateTotalCalories(Recipe recipe);

    boolean hasAllIngredients(Recipe recipe);

    ArrayList<Boolean> checkIngredients(Recipe recipe);

    void refreshAvailability(ArrayList<Recipe> recipes);
}

