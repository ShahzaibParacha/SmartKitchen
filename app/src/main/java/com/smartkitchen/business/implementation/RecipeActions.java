package com.smartkitchen.business.implementation;

import com.smartkitchen.business.InvalidInputException;
import com.smartkitchen.business.interfaces.IInventoryActions;
import com.smartkitchen.business.interfaces.IListValidation;
import com.smartkitchen.business.interfaces.IRecipeActions;
import com.smartkitchen.objects.Item;
import com.smartkitchen.objects.Recipe;
import com.smartkitchen.persistence.DBManager;
import com.smartkitchen.persistence.IDBInventory;
import com.smartkitchen.persistence.IDBRecipe;

import java.util.ArrayList;

//Functions for managing recipes
public class RecipeActions implements IRecipeActions {

    //Access to the database and relevant methods
    private IDBRecipe recipeDB = DBManager.getRecipeDB();
    private IInventoryActions inventoryActions = new InventoryActions();
    private final IListValidation validation = new ListValidation();

    //Empty constructor for regular use
    public RecipeActions() {
    }

    //Constructor used for testing purposes
    public RecipeActions(IDBRecipe recipeDB) {
        this.recipeDB = recipeDB;
    }

    // special constructor to test check ingredients logic
    public RecipeActions(IDBRecipe recipeDB, IDBInventory inventorydb){
        this.recipeDB = recipeDB;
        inventoryActions = new InventoryActions(inventorydb);
    }

    //Adds a recipe to the db, also sets the total calories
    @Override
    public void addToRecipes(Recipe recipe) throws InvalidInputException {
        validation.containsRecipeInputs(recipe);
        recipeDB.addToRecipes(recipe);
        recipe.setTotalCalories(calculateTotalCalories(recipe));
    }

    //Updates the recipe in the database
    @Override
    public void updateRecipe(Recipe recipe) {
        recipeDB.updateRecipe(recipe);
    }

    //Gets all recipes in ArrayList form
    @Override
    public ArrayList<Recipe> getRecipeList() {
        return recipeDB.getRecipeList();
    }

    //Removes a recipe from the db
    @Override
    public void removeRecipe(Recipe recipe) {
        recipeDB.removeRecipe(recipe);
    }

    //Calculates the total calories based on the ingredients and their amounts
    @Override
    public int calculateTotalCalories(Recipe recipe) {
        int total = 0;
        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            //Gets the ingredient from the inventory based on name
            Item currIngredient = inventoryActions.getItemByName(recipe.getIngredients().get(i));
            int ingredientQuantity = Integer.parseInt(recipe.getIngredientQuantities().get(i));
            //If that item exists in inventory, do the calculation, otherwise ignore and continue to next
            if (currIngredient != null)
                total += currIngredient.getCaloriesPerUnit() * ingredientQuantity;
        }
        return total;
    }

    //Returns boolean array list of whether the ingredient at that position is in inventory
    @Override
    public ArrayList<Boolean> checkIngredients(Recipe recipe) {
        ArrayList<Boolean> hasIngredient = new ArrayList<>();
        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            hasIngredient.add(true);
            Item currIngredient = inventoryActions.getItemByName(recipe.getIngredients().get(i));
            if (currIngredient == null) {
                hasIngredient.set(i, false);
            } else if (Integer.parseInt(recipe.getIngredientQuantities().get(i)) > currIngredient.getQuantity()) {
                hasIngredient.set(i, false);
            }
        }
        return hasIngredient;
    }

    //Checks if a recipe has all the ingredients available
    @Override
    public boolean hasAllIngredients(Recipe recipe) {
        boolean hasAllIngredients = true;
        for (Boolean b : recipe.getHasIngredient()) {
            if (!b) {
                hasAllIngredients = false;
                break;
            }
        }
        return hasAllIngredients;
    }

    //Refreshes the availability variables whenever changes are made
    @Override
    public void refreshAvailability(ArrayList<Recipe> recipes) {
        for (Recipe r : recipes) {
            r.setHasIngredient(checkIngredients(r));
            r.setHaveAllIngredients(hasAllIngredients(r));
        }
    }
}

