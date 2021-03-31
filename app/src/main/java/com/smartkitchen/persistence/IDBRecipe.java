package com.smartkitchen.persistence;

import com.smartkitchen.objects.Recipe;

import java.util.ArrayList;

public interface IDBRecipe {

    void addToRecipes(Recipe recipe);

    void updateRecipe(Recipe recipe);

    Recipe removeRecipe(Recipe recipe);

    ArrayList<Recipe> getRecipeList();

}
