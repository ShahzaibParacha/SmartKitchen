package com.smartkitchen.tests.business;

import android.widget.CheckBox;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.smartkitchen.business.implementation.GroceryActions;
import com.smartkitchen.business.implementation.InventoryActions;
import com.smartkitchen.business.implementation.ListActions;
import com.smartkitchen.objects.Item;
import com.smartkitchen.objects.Recipe;
import com.smartkitchen.persistence.hsqldb.GroceryPersistenceDB;
import com.smartkitchen.persistence.hsqldb.InventoryPersistenceDB;
import com.smartkitchen.tests.utils.TestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ListActionsIT {
    //The three classes to be tested
    private ListActions listTestTarget;
    private InventoryActions invTestTarget;
    private GroceryActions groceryTestTarget;
    //The testing database
    private File tempDB;

    //Sets up the testing environment
    @Before
    public void setUp() throws IOException {
        //Create the test DB and instantiate classes to be tested
        this.tempDB = TestUtils.copyDB();
        final GroceryPersistenceDB groceryPersistence = new GroceryPersistenceDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final InventoryPersistenceDB inventoryPersistence = new InventoryPersistenceDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        this.listTestTarget = new ListActions();
        this.invTestTarget = new InventoryActions(inventoryPersistence);
        this.groceryTestTarget = new GroceryActions(groceryPersistence, invTestTarget);
    }

    //Tests adding to either inventory or grocery
    @Test
    public void addTest() {
        System.out.println("\nStarting addTest");
        ArrayList<String> testAllergies = new ArrayList<>();
        testAllergies.add("testAllergy");

        Item testItem = new Item("sampleItem", 1, "testUnit", 1, 1, testAllergies, 1, 1);

        try {
            groceryTestTarget.addToGrocery(testItem);
            invTestTarget.addToInventory(testItem);
            groceryTestTarget.addToGrocery(testItem); // add existing

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        assertEquals(groceryTestTarget.getGroceryItem(2).getName(), testItem.getName());
        assertEquals(invTestTarget.getInventoryItem(2).getName(), testItem.getName());

        Item testItem2 = new Item("sampleItem", 1, "", 1, 1, testAllergies, 1, 1);

        try {
            groceryTestTarget.addToGrocery(testItem2);
            invTestTarget.addToInventory(testItem2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        for (Item x : invTestTarget.getInventoryList()) {
            System.out.println(x.getName() + ", " + x.getQuantity() + ", " + invTestTarget.getInventoryList().indexOf(x));
        }

        System.out.println("Finishing addTest");
    }

    //Tests updating either inventory or grocery item
    @Test
    public void updateItemTest() {
        System.out.println("\nStarting updateItemTest");
        ArrayList<String> testAllergies = new ArrayList<>();
        testAllergies.add("testAllergy");

        Item testItem = new Item("sampleItem", 1, "testUnit", 1, 1, testAllergies, 1, 1);
        try {
            groceryTestTarget.addToGrocery(testItem);
            invTestTarget.addToInventory(testItem);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        testItem.setQuantity(3);
        testItem.setQuantityToBuy(3);

        groceryTestTarget.updateGroceryItem(testItem);
        invTestTarget.updateInventoryItem(testItem);

        System.out.println("Finished updateItemTest");
    }

    //Tests validation for editing an item
    @Test
    public void editValidationTest() {
        System.out.println("\nStarting editValidationTest");

        ArrayList<String> testAllergies = new ArrayList<>();
        testAllergies.add("testAllergy");
        Item testItem = new Item("sampleItem", 1, "", 1, 1, testAllergies, 1, 1);
        Item testItem2 = new Item("sampleItem", 1, "testUnit", 1, 1, testAllergies, 1, 1);

        try {
            listTestTarget.editValidation(testItem);
            listTestTarget.editValidation(testItem2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

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
        Recipe testRecipe2 = new Recipe("", TEST_INGREDIENTS, TEST_INGREDIENT_QUANT, TEST_INGREDIENT_UNITS, TEST_INSTRUCTIONS);

        try{
            listTestTarget.editValidation(testRecipe);
            listTestTarget.editValidation(testRecipe2);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        try{
            listTestTarget.editValidation(testRecipe2);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        System.out.print("Finished editValidationTest");

        System.out.print("Finished editValidationTest");
    }

    @Test
    public void getDuplicateByNameTest(){
        System.out.println("\nStarting getDuplicateByNameTest");

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

        ArrayList<Recipe> recipeList = new ArrayList<Recipe>();
        recipeList.add(testRecipe);

        assertEquals(listTestTarget.getDuplicateByName(testRecipe, recipeList), testRecipe);

        System.out.println("Send getDuplicateByNameTest");
    }

    @Test
    public void getListTest(){
        System.out.println("\nStarting getListTest");

        ArrayList<String> testAllergies = new ArrayList<>();
        testAllergies.add("testAllergy");
        Item testItem = new Item("sampleItem", 1, "testUnit", 1, 1, testAllergies, 1, 1);

        try {
            groceryTestTarget.addToGrocery(testItem);
            invTestTarget.addToInventory(testItem);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        assertEquals(groceryTestTarget.getGroceryList().get(2).getName(), testItem.getName());
        assertEquals(invTestTarget.getInventoryList().get(2).getName(), testItem.getName());

        System.out.println("Finished getListTest");
    }

    //Testing the buy item method
    @Test
    public void buyItemTest() {
        System.out.println("\nStarting buyItemTest");
        ArrayList<String> testAllergies = new ArrayList<>();
        testAllergies.add("testAllergy");

        Item testItem = new Item("sampleItem", 1, "testUnit", 5, 2, testAllergies, 1, 1);
        Item testItem2 = new Item("sampleItem2", 1, "testUnit", 1, 1, testAllergies, 1, 1);
        Item testItem3 = new Item("sampleItem2", 1, "", 1, 1, testAllergies, 1, 1);

        try {
            invTestTarget.addToInventory(testItem);
            groceryTestTarget.buyItem(testItem);
            groceryTestTarget.buyItem(testItem2);
            groceryTestTarget.buyItem(testItem3);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Starting buyItemTest");
    }

    @Test
    public void buyAllTest() {
        System.out.println("\nStarting buyAllTest");

        ArrayList<String> testAllergies = new ArrayList<>();
        testAllergies.add("testAllergy");
        Item testItem = new Item("sampleItem", 1, "testUnit", 1, 1, testAllergies, 1, 1);

        try {
            groceryTestTarget.addToGrocery(testItem);
            groceryTestTarget.buyAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        assertEquals(invTestTarget.getInventoryItem(4).getName(), testItem.getName());

        System.out.println("Finished buyAllTest");
    }

    @Test
    public void removeTest() {
        System.out.println("\nStarting removeTest");
        ArrayList<String> testAllergies = new ArrayList<>();
        testAllergies.add("testAllergy");
        Item testItem = new Item("sampleItem", 1, "testUnit", 1, 1, testAllergies, 1, 1);

        try {
            groceryTestTarget.addToGrocery(testItem);
            invTestTarget.addToInventory(testItem);
            groceryTestTarget.removeFromGrocery(testItem);
            invTestTarget.removeFromInventory(testItem);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        assertNotEquals(groceryTestTarget.getGroceryList().get(groceryTestTarget.getGroceryList().size() - 1), "testTarget");
        assertNotEquals(invTestTarget.getInventoryList().get(invTestTarget.getInventoryList().size() - 1), "testTarget");

        System.out.println("Finished removeTest");
    }

    @Test
    public void isInListTest() {
        System.out.println("\nStarting isInListTest");
        ArrayList<String> testAllergies = new ArrayList<>();
        testAllergies.add("testAllergy");

        assertTrue(listTestTarget.isInList(testAllergies, "testAllergy"));

        System.out.println("Finished isInListTest");
    }

    @Test
    public void getGroceryListTotalTest() {
        GroceryActions testTarget1 = new GroceryActions();
        System.out.println("\nStarting getGroceryListTotal");
        ArrayList<String> testAllergies = new ArrayList<>();
        testAllergies.add("testAllergy");
        Item testItem = new Item("sampleItem", 1, "testUnit", 1, 1, testAllergies, 1, 50);

        try {
            groceryTestTarget.addToGrocery(testItem);
            invTestTarget.addToInventory(testItem);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        assertEquals(61, groceryTestTarget.getGroceryListTotal(), 0.0);

        System.out.println("Finished getGroceryListTotal");
    }

    //Dismantles the testing environment
    @After
    public void tearDown() {
        //reset db
        this.tempDB.delete();
    }


}