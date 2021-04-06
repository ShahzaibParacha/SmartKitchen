package com.smartkitchen.tests.business;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.smartkitchen.business.InvalidInputException;
import com.smartkitchen.objects.Item;
import com.smartkitchen.objects.Recipe;

import java.io.File;
import java.io.IOException;

import com.smartkitchen.business.implementation.GroceryActions;
import com.smartkitchen.business.implementation.InventoryActions;
import com.smartkitchen.business.implementation.ListActions;
import com.smartkitchen.business.implementation.RecipeActions;

import com.smartkitchen.persistence.hsqldb.GroceryPersistenceDB;
import com.smartkitchen.persistence.hsqldb.InventoryPersistenceDB;
import com.smartkitchen.persistence.hsqldb.RecipePersistenceDB;
import com.smartkitchen.tests.utils.TestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class RecipeActionsIT{
    private InventoryActions inventoryActions;
    private RecipeActions testTarget;
    private File tempDB;

    @Before
    public void setUp() throws IOException{
        this.tempDB = TestUtils.copyDB();
        final InventoryPersistenceDB inventoryPersistence = new InventoryPersistenceDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final RecipePersistenceDB recipePersistence = new RecipePersistenceDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        this.inventoryActions = new InventoryActions(inventoryPersistence);
        this.testTarget = new RecipeActions(recipePersistence);
    }

    @Test
    public void testAddRemoveRecipes() throws InvalidInputException {
        String TEST_NAME = "testName";
        ArrayList<String> TEST_INGREDIENTS = new ArrayList<String>();
        ArrayList<String> TEST_INGREDIENT_QUANT = new ArrayList<String>();
        ArrayList<String> TEST_INGREDIENT_UNITS = new ArrayList<String>();
        ArrayList<String> TEST_INSTRUCTIONS = new ArrayList<String>();
        ArrayList<Boolean> TEST_HAS_INGREDIENT = new ArrayList<Boolean>();

        TEST_INGREDIENTS.add("test1");
        TEST_INGREDIENT_QUANT.add("1");
        TEST_INGREDIENT_UNITS.add("testUnits");
        TEST_INSTRUCTIONS.add("testInstruction");
        TEST_HAS_INGREDIENT.add(true);

        Recipe testRecipe = new Recipe(TEST_NAME, TEST_INGREDIENTS, TEST_INGREDIENT_QUANT, TEST_INGREDIENT_UNITS, TEST_INSTRUCTIONS);


        testTarget.addToRecipes(testRecipe);
        assertEquals(testTarget.getRecipeList().get(1).getName(), testRecipe.getName());
        //testTarget.removeRecipe(testTarget.getRecipe(1));
    }

    @Test
    public void testUpdateRecipe() throws InvalidInputException{
        String TEST_NAME = "testName";
        ArrayList<String> TEST_INGREDIENTS = new ArrayList<String>();
        ArrayList<String> TEST_INGREDIENT_QUANT = new ArrayList<String>();
        ArrayList<String> TEST_INGREDIENT_UNITS = new ArrayList<String>();
        ArrayList<String> TEST_INSTRUCTIONS = new ArrayList<String>();
        ArrayList<Boolean> TEST_HAS_INGREDIENT = new ArrayList<Boolean>();

        TEST_INGREDIENTS.add("test1");
        TEST_INGREDIENT_QUANT.add("1");
        TEST_INGREDIENT_UNITS.add("testUnits");
        TEST_INSTRUCTIONS.add("testInstruction");
        TEST_HAS_INGREDIENT.add(true);

        Recipe testRecipe = new Recipe(TEST_NAME, TEST_INGREDIENTS, TEST_INGREDIENT_QUANT, TEST_INGREDIENT_UNITS, TEST_INSTRUCTIONS);

        testTarget.addToRecipes(testRecipe);
        testTarget.updateRecipe(testRecipe);

        assertEquals(testTarget.getRecipeList().get(1).getName(), testRecipe.getName());
    }

    @Test
    public void getRecipeList() throws InvalidInputException{
        String TEST_NAME = "testName";
        ArrayList<String> TEST_INGREDIENTS = new ArrayList<String>();
        ArrayList<String> TEST_INGREDIENT_QUANT = new ArrayList<String>();
        ArrayList<String> TEST_INGREDIENT_UNITS = new ArrayList<String>();
        ArrayList<String> TEST_INSTRUCTIONS = new ArrayList<String>();
        ArrayList<Boolean> TEST_HAS_INGREDIENT = new ArrayList<Boolean>();

        TEST_INGREDIENTS.add("test1");
        TEST_INGREDIENT_QUANT.add("1");
        TEST_INGREDIENT_UNITS.add("testUnits");
        TEST_INSTRUCTIONS.add("testInstruction");
        TEST_HAS_INGREDIENT.add(true);

        Recipe testRecipe = new Recipe(TEST_NAME, TEST_INGREDIENTS, TEST_INGREDIENT_QUANT, TEST_INGREDIENT_UNITS, TEST_INSTRUCTIONS);

        testTarget.addToRecipes(testRecipe);

        assertEquals(testTarget.getRecipeList().get(1).getName(), testRecipe.getName());
    }


    @Test
    public void testCheckIngredients() throws InvalidInputException{
        ArrayList<String> testAllergies = new ArrayList<String>();
        testAllergies.add("testAllergy");

        String TEST_NAME = "testName";
        ArrayList<String> TEST_INGREDIENTS = new ArrayList<String>();
        ArrayList<String> TEST_INGREDIENT_QUANT = new ArrayList<String>();
        ArrayList<String> TEST_INGREDIENT_UNITS = new ArrayList<String>();
        ArrayList<String> TEST_INSTRUCTIONS = new ArrayList<String>();
        ArrayList<Boolean> TEST_HAS_INGREDIENT = new ArrayList<Boolean>();

        TEST_INGREDIENTS.add("test1");
        TEST_INGREDIENT_QUANT.add("1");
        TEST_INGREDIENT_UNITS.add("testUnits");
        TEST_INSTRUCTIONS.add("testInstruction");
        TEST_HAS_INGREDIENT.add(true);

        Recipe testRecipe = new Recipe(TEST_NAME, TEST_INGREDIENTS, TEST_INGREDIENT_QUANT, TEST_INGREDIENT_UNITS, TEST_INSTRUCTIONS);
        TEST_HAS_INGREDIENT.add(true);
        testRecipe.setHasIngredient(TEST_HAS_INGREDIENT);

        Item testItem = new Item("testItem", 1, "testUnit", 1, 1, testAllergies, 1, 1);
        inventoryActions.addToInventory(testItem);

        testTarget.addToRecipes(testRecipe);
        testTarget.checkIngredients(testRecipe);
        testTarget.hasAllIngredients(testRecipe);
        testTarget.refreshAvailability(testTarget.getRecipeList());
    }

    @After
    public void tearDown(){
        //reset db
        this.tempDB.delete();
    }

}