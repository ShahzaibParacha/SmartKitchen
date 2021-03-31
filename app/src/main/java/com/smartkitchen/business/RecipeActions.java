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
        //recipe.setHasIngredient(checkIngredients(recipe));
        //recipe.setHaveAllIngredients(hasAllIngredients(recipe));
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
    public ArrayList<Boolean> checkIngredients(Recipe recipe) {
        ArrayList<Boolean> hasIngredient = new ArrayList<>();
        for(int i = 0; i < recipe.getIngredients().size(); i++){
            hasIngredient.add(true);
            Item currIngredient = inventoryActions.getItemByName(recipe.getIngredients().get(i));
            if(currIngredient == null){
                hasIngredient.set(i, false);
            }
            else if(Integer.parseInt(recipe.getIngredientQuantities().get(i)) > currIngredient.getQuantity()){
                hasIngredient.set(i, false);
            }
        }
        return hasIngredient;
    }

    @Override
    public boolean hasAllIngredients(Recipe recipe) {
        boolean hasAllIngredients = true;
        for (Boolean b:recipe.getHasIngredient()) {
            if (!b) {
                hasAllIngredients = false;
                break;
            }
        }
        return hasAllIngredients;
    }

    @Override
    public void refreshAvailability(ArrayList<Recipe> recipes) {
        for (Recipe r:recipes) {
            r.setHasIngredient(checkIngredients(r));
            r.setHaveAllIngredients(hasAllIngredients(r));
        }
    }
}

