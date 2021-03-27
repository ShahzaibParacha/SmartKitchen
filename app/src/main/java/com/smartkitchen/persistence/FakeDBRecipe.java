package com.smartkitchen.persistence;

import com.smartkitchen.objects.Recipe;

import java.util.ArrayList;

public class FakeDBRecipe implements IDBRecipe {

    private ArrayList<Recipe> recipes;

    public FakeDBRecipe(){
        recipes = new ArrayList<>();
    }

    @Override
    public void addToRecipes(Recipe recipe) {
        recipes.add(recipe);
    }

    @Override
    public void updateRecipe(Recipe recipe) {

    }

    @Override
    public Recipe removeRecipe(Recipe recipe) {
        recipes.remove(recipe);
        return recipe;
    }

    @Override
    public ArrayList<Recipe> getRecipeList() {
        return recipes;
    }
}

