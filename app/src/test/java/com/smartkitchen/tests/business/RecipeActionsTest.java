package com.smartkitchen.tests.business;

import com.smartkitchen.business.InvalidInputException;
import com.smartkitchen.business.InventoryActions;
import com.smartkitchen.business.RecipeActions;
import com.smartkitchen.objects.Item;
import com.smartkitchen.objects.Recipe;
import com.smartkitchen.persistence.stubs.FakeDBInventory;
import com.smartkitchen.persistence.stubs.FakeDBRecipe;

import org.mockito.Mockito;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class RecipeActionsTest{
    private RecipeActions testTarget;
    private RecipeActions mockTarget;
    private FakeDBRecipe idbRecipe = new FakeDBRecipe();
    private FakeDBInventory dbInventory = new FakeDBInventory();
    private InventoryActions inventoryActions = new InventoryActions(dbInventory);


    @Before
    public void setup(){
        testTarget = new RecipeActions(idbRecipe);
        mockTarget = Mockito.mock(RecipeActions.class);
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

        Mockito.when(mockTarget.getRecipe(0)).thenReturn(testRecipe);

        testTarget.addToRecipes(testRecipe);
        mockTarget.addToRecipes(testRecipe);
        Mockito.verify(mockTarget).addToRecipes(testRecipe);

        assertEquals(testTarget.getRecipe(0), testRecipe);

        testTarget.removeRecipe(testRecipe);
        mockTarget.removeRecipe(testRecipe);
        Mockito.verify(mockTarget).removeRecipe(testRecipe);
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

        Mockito.when(mockTarget.getRecipe(0)).thenReturn(testRecipe);

        testTarget.addToRecipes(testRecipe);
        mockTarget.addToRecipes(testRecipe);
        Mockito.verify(mockTarget).addToRecipes(testRecipe);

        testTarget.updateRecipe(testRecipe);
        mockTarget.updateRecipe(testRecipe);
        Mockito.verify(mockTarget).updateRecipe(testRecipe);

        assertEquals(testTarget.getRecipe(0), testRecipe);
    }

    @Test
    public void getRecipeList() throws InvalidInputException{
        RecipeActions testOtherConst = new RecipeActions();
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

        Mockito.when(mockTarget.getRecipe(0)).thenReturn(testRecipe);

        testTarget.addToRecipes(testRecipe);
        mockTarget.addToRecipes(testRecipe);
        Mockito.verify(mockTarget).addToRecipes(testRecipe);

        assertEquals(testTarget.getRecipeList().get(0), testRecipe);
        assertEquals(testTarget.getRecipe(0), testRecipe);
    }

    @Test
    public void testCheckIngredients() throws InvalidInputException{
        RecipeActions testTarget1 = new RecipeActions(idbRecipe, dbInventory);
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

        testTarget1.addToRecipes(testRecipe);
        testTarget1.checkIngredients(testRecipe);
        testTarget1.hasAllIngredients(testRecipe);
        testTarget1.refreshAvailability(testTarget1.getRecipeList());
    }
}