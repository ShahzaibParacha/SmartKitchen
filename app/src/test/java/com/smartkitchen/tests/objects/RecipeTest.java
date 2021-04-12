package com.smartkitchen.tests.objects;

import org.junit.Test;

import com.smartkitchen.objects.Recipe;

import java.util.ArrayList;
import static org.junit.Assert.*;

public class RecipeTest{
    private int TEST_ID = 0;
    private String TEST_NAME = "testName";
    private ArrayList<String> TEST_INGREDIENTS = new ArrayList<String>();
    private ArrayList<String> TEST_INGREDIENT_QUANT = new ArrayList<String>();
    private ArrayList<String> TEST_INGREDIENT_UNITS = new ArrayList<String>();
    private ArrayList<String> TEST_INSTRUCTIONS = new ArrayList<String>();
    private int TEST_TOTAL_CALORIES = 0;
    private boolean TEST_HAVE_ALL_INGREDIENTS = true;
    private ArrayList<Boolean> TEST_HAS_INGREDIENT = new ArrayList<Boolean>();



    @Test
    public void testRecipeConstructor(){
        TEST_INGREDIENTS.add("test1");
        TEST_INGREDIENT_QUANT.add("1");
        TEST_INGREDIENT_UNITS.add("testUnits");
        TEST_INSTRUCTIONS.add("testInstruction");
        TEST_HAVE_ALL_INGREDIENTS = true;
        TEST_HAS_INGREDIENT.add(true);

        // create recipe
        Recipe testTarget = new Recipe(TEST_NAME, TEST_INGREDIENTS, TEST_INGREDIENT_QUANT, TEST_INGREDIENT_UNITS, TEST_INSTRUCTIONS);

        assertEquals(TEST_ID, testTarget.getId());
        assertEquals(TEST_NAME, testTarget.getName());
        assertEquals(testTarget.getIngredients().toString(), TEST_INGREDIENTS.toString());
        assertEquals(testTarget.getIngredientQuantities().toString(), TEST_INGREDIENT_QUANT.toString());
        assertEquals(testTarget.getTotalCalories(), TEST_TOTAL_CALORIES);
        assertEquals(testTarget.getIngredientUnits().toString(), TEST_INGREDIENT_UNITS.toString());
        assertEquals(testTarget.getInstructions().toString(), TEST_INSTRUCTIONS.toString());
        testTarget.setHaveAllIngredients(true);
        assertEquals(testTarget.haveAllIngredients(), TEST_HAVE_ALL_INGREDIENTS);
        testTarget.setHasIngredient(TEST_HAS_INGREDIENT);

        for(int i = 0; i < TEST_HAS_INGREDIENT.size(); i++){
            assertEquals(testTarget.getHasIngredient().get(i), TEST_HAS_INGREDIENT.get(i));
        }
    }
}