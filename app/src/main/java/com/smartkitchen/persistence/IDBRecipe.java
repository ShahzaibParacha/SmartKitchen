package com.smartkitchen.persistence;

import com.smartkitchen.objects.Recipe;

import java.util.ArrayList;

//Interface to recipe database
public interface IDBRecipe {

    void addToRecipes(Recipe recipe);

    void updateRecipe(Recipe recipe);

    void removeRecipe(Recipe recipe);

    ArrayList<Recipe> getRecipeList();

}
