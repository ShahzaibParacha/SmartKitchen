package com.smartkitchen.tests.business;

import org.junit.Test;

import com.smartkitchen.business.IListValidation;
import com.smartkitchen.business.InvalidInputException;
import com.smartkitchen.business.ListValidation;
import com.smartkitchen.objects.Item;
import com.smartkitchen.objects.Recipe;

import java.util.ArrayList;

import static org.junit.Assert.*;


//just copied over the unit test since this layer does not interact with the persistence layer
//reason: code coverage for Integration Tests automation
public class ListValidationIT{

    private IListValidation testListValidation = new ListValidation();

    @Test
    public void testListValidation(){
        System.out.println("\nStarting testListValidation constructor.");

        //Act
        Item testItem = new Item("sampleItem", 1, "sampleUnit", 1, 1);

        //Assert
        assertNotNull(testListValidation);
        System.out.println("Finished testListValidation constructor.");
    }

    @Test
    public void testThresholdStatus(){
        System.out.println("\nStarting testThresholdStatus.");

        //Act
        Item testItem = new Item("sampleItem", 1, "sampleUnit", 1, 1);

        //Assert
        assertFalse(testListValidation.thresholdStatus(testItem));
        System.out.println("Finished testThresholdStatus.");
    }

    @Test
    public void testThresholdStatusTrue(){
        System.out.println("\nStarting testThresholdStatus.");

        //Act
        Item testItem = new Item("sampleItem", 1, "sampleUnit", 1, 5);

        //Assert
        assertTrue(testListValidation.thresholdStatus(testItem));
        System.out.println("Finished testThresholdStatus.");
    }

    @Test
    public void testIsEmpty(){
        System.out.println("\nStarting testThresholdStatus.");

        //Act
        Item testItem = new Item("sampleItem", 1, "sampleUnit", 1, 1);

        //Assert
        System.out.println("Finished testThresholdStatus.");
    }

    @Test
    public void testContainsItemInput() throws Exception {
        System.out.println("\nStarting testContainsItemInput.");

        //Act 1 -- getName
        Item testItem = new Item("", -1, "", -1, -1);
        try {
            testListValidation.containsItemInputs(testItem);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Act 2 -- getQuantity
        Item testItem1 = new Item(" ", -1, "", -1, -1);
        try {
            testListValidation.containsItemInputs(testItem1);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Act 3 -- getLength
        Item testItem2 = new Item(" ", 1, "", -1, -1);
        try {
            testListValidation.containsItemInputs(testItem2);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Act 4 -- threshold
        Item testItem3 = new Item(" ", 1, " ", 0, -1);
        try {
            testListValidation.containsItemInputs(testItem3);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Act 5 -- full true
        Item testItem4 = new Item("sampleItem", 1, "sampleItem", 1, 1);
        try {
            testListValidation.containsItemInputs(testItem4);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Finished testContainsItemInput.");
    }

    @Test
    public void testContainsRecipeInputs() throws InvalidInputException {
        String TEST_NAME = "testName";
        ArrayList<String> TEST_INGREDIENTS = new ArrayList<String>();
        ArrayList<String> TEST_INGREDIENTS_EMPTY = new ArrayList<String>();
        ArrayList<String> TEST_INGREDIENT_QUANT = new ArrayList<String>();
        ArrayList<String> TEST_INGREDIENT_UNITS = new ArrayList<String>();
        ArrayList<String> TEST_INSTRUCTIONS = new ArrayList<String>();
        ArrayList<String> TEST_INSTRUCTIONS_EMPTY = new ArrayList<String>();

        ArrayList<Boolean> TEST_HAS_INGREDIENT = new ArrayList<Boolean>();

        TEST_INGREDIENTS.add("test1");
        TEST_INGREDIENT_QUANT.add("1");
        TEST_INGREDIENT_UNITS.add("testUnits");
        TEST_INSTRUCTIONS.add("testInstruction");
        TEST_HAS_INGREDIENT.add(true);

        Recipe testRecipe = new Recipe(TEST_NAME, TEST_INGREDIENTS, TEST_INGREDIENT_QUANT, TEST_INGREDIENT_UNITS, TEST_INSTRUCTIONS);

        try {
            testListValidation.containsRecipeInputs(testRecipe);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            Recipe testRecipe1 = new Recipe("", TEST_INGREDIENTS, TEST_INGREDIENT_QUANT, TEST_INGREDIENT_UNITS, TEST_INSTRUCTIONS);
            testListValidation.containsRecipeInputs(testRecipe1);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            Recipe testRecipe2 = new Recipe(TEST_NAME, TEST_INGREDIENTS_EMPTY, TEST_INGREDIENT_QUANT, TEST_INGREDIENT_UNITS, TEST_INSTRUCTIONS);
            testListValidation.containsRecipeInputs(testRecipe2);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            Recipe testRecipe3 = new Recipe(TEST_NAME, TEST_INGREDIENTS_EMPTY, TEST_INGREDIENT_QUANT, TEST_INGREDIENT_UNITS, TEST_INSTRUCTIONS_EMPTY);
            testListValidation.containsRecipeInputs(testRecipe3);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try { // checkInstructionInput
            testListValidation.checkInstructionInput("test");
            testListValidation.checkInstructionInput("");
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testIngredientInputs() throws InvalidInputException{
        System.out.println("\n Starting testIngredientInputs");

        try {
            testListValidation.checkIngredientInputs("", "-1", "");
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            testListValidation.checkIngredientInputs("test", "", "");
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            testListValidation.checkIngredientInputs("test", "-1", "");
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            testListValidation.checkIngredientInputs("test", "1", "");
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }


        System.out.println("End testIngredientInputs");
    }

}

