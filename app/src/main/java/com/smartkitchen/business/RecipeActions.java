package com.smartkitchen.business;

import com.smartkitchen.objects.Item;
import com.smartkitchen.objects.Recipe;
import com.smartkitchen.persistence.DBManager;
import com.smartkitchen.persistence.IDBRecipe;

import java.util.ArrayList;

public class RecipeActions implements IRecipeActions{

    private IDBRecipe recipeDB = DBManager.getRecipeDB();
    private IInventoryActions inventoryActions = new InventoryActions();

    public RecipeActions(){}

    public RecipeActions(IDBRecipe recipeDB){
        this.recipeDB = recipeDB;
    }

    @Override
    public void addToRecipes(Recipe recipe){
        recipeDB.addToRecipes(recipe);
        recipe.setTotalCalories(calculateTotalCalories(recipe));
        recipe.setHaveAllIngredients(hasAllIngredients(recipe));
    }

    @Override
    public void updateRecipe(Recipe recipe) {
        recipeDB.updateRecipe(recipe);
    }

    @Override
    public Recipe getRecipe(int position) {
        return recipeDB.getRecipeList().get(position);
    }

    @Override
    public ArrayList<Recipe> getRecipeList() {
        return recipeDB.getRecipeList();
    }

    @Override
    public void removeRecipe(Recipe recipe) {
        recipeDB.removeRecipe(recipe);
    }

    @Override
    public int calculateTotalCalories(Recipe recipe) {
        int total = 0;
        for(int i = 0; i < recipe.getIngredients().size(); i++){
            Item currIngredient = inventoryActions.getItemByName(recipe.getIngredients().get(i));
            int ingredientQuantity = Integer.parseInt(recipe.getIngredientQuantities().get(i));
            if(currIngredient != null)
                total += currIngredient.getCaloriesPerUnit() * ingredientQuantity;
        }
        return total;
    }

    @Override
    public boolean hasAllIngredients(Recipe recipe) {
        boolean hasAllIngredients = true;
        for (String s:recipe.getIngredients()) {
            if(inventoryActions.getItemByName(s) == null)
                hasAllIngredients = false;
        }
        return hasAllIngredients;
    }

    @Override
    public void refreshAvailability() {
        for (Recipe r:getRecipeList()) {
            r.setHaveAllIngredients(hasAllIngredients(r));
        }
    }
}
