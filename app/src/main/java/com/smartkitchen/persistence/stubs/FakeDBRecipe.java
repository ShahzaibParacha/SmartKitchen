package com.smartkitchen.persistence.stubs;

import com.smartkitchen.business.InvalidInputException;
import com.smartkitchen.business.implementation.ListValidation;
import com.smartkitchen.business.interfaces.IListValidation;
import com.smartkitchen.objects.Recipe;
import com.smartkitchen.persistence.IDBRecipe;

import java.util.ArrayList;

//Fake DB implementation of the recipe database, used for testing purposes
public class FakeDBRecipe implements IDBRecipe {

    private final ArrayList<Recipe> recipes;
    private final IListValidation validation;

    // Constructor instantiating grocery list
    public FakeDBRecipe() {
        recipes = new ArrayList<>();
        validation = new ListValidation();
    }

    // Method validates input and checks whether the item contains proper parameters
    // If proper parameters are in place, add item to the grocery list
    @Override
    public void addToRecipes(Recipe recipe) {
        try {
            validation.containsRecipeInputs(recipe);
            recipes.add(recipe);
        } catch (InvalidInputException e) {
            // this is where UI can display a toast message with a warning for the user
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateRecipe(Recipe recipe) {
        //Doesn't do anything, only for actual db implementation
    }

    // Method removes specified item from inventory
    @Override
    public void removeRecipe(Recipe recipe) {
        recipes.remove(recipe);
    }

    // Getter
    @Override
    public ArrayList<Recipe> getRecipeList() {
        return recipes;
    }
}

