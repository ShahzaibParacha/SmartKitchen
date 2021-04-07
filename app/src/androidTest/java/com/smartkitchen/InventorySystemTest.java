package com.smartkitchen;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import android.widget.EditText;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import com.smartkitchen.application.MainActivity;
import com.smartkitchen.application.Services;
import com.smartkitchen.objects.Item;
import com.smartkitchen.persistence.hsqldb.GroceryPersistenceDB;
import com.smartkitchen.persistence.hsqldb.InventoryPersistenceDB;
import com.smartkitchen.TestHelpers.TestViewAction;

import static org.junit.Assert.*;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class InventorySystemTest{
    private InventoryPersistenceDB inventorydb;
    private GroceryPersistenceDB grocerydb;
    List<Item> inventoryList;

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);


    @Before
    public void setupDatabase(){
        inventorydb = (InventoryPersistenceDB) Services.getInventoryPersistence();
        grocerydb = (GroceryPersistenceDB) Services.getGroceryPersistence();
        inventoryList = inventorydb.getInventoryList();
    }

    // ---------------- AUTOMATED ACCEPTANCE TESTS ----------------------
    @Test
    public void addRemoveInventoryItemTest() throws InterruptedException{ // User Story: Add Items to the Current Inventory AND Remove Items from the Current Inventory
        int beforeInvSize = 0;

        onView(withId(R.id.btnGoToAddInvActivity)).perform(click());

        // add new item
        onView(withId(R.id.inputInventoryItemName)).perform(typeText("testItem"));
        onView(withId(R.id.inputInventoryItemQuantity)).perform(typeText("1"));
        onView(withId(R.id.inputInventoryItemUnits)).perform(typeText("testUnits"));
        onView(withId(R.id.btnAddInventoryItem)).perform(scrollTo(), click());
        beforeInvSize = inventorydb.getInventoryList().size(); // records our new inventory size after adding the test item

        // check if our test item entry is actually added into the inventorydb
        assertEquals(inventorydb.getInventoryList().get(2).getName(), "testItem");

        // remove the newly added item
        onView(withId(R.id.itemRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testItem")), TestViewAction.clickChildviewWithId(R.id.itemDownArrow)));
        Thread.sleep(1000);
        onView(withId(R.id.itemRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testItem")), TestViewAction.clickChildviewWithId(R.id.btnRemoveInvItem)));
        Thread.sleep(1000);

        // check if the inventory size reduces after removing the test item
        assertEquals(beforeInvSize-1, inventorydb.getInventoryList().size());
    }

    @Test
    public void editInventoryItemTest() throws InterruptedException{ // User Story: Edit Items in the Current Inventory
        onView(withId(R.id.btnGoToAddInvActivity)).perform(click());

        // add new item
        onView(withId(R.id.inputInventoryItemName)).perform(typeText("testItem"));
        onView(withId(R.id.inputInventoryItemQuantity)).perform(typeText("1"));
        onView(withId(R.id.inputInventoryItemUnits)).perform(typeText("testUnits"));
        onView(withId(R.id.btnAddInventoryItem)).perform(scrollTo(), click());

        // check item name
        assertEquals(inventorydb.getInventoryList().get(2).getName(), "testItem");

        // edit item fields
        onView(withId(R.id.itemRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testItem")), TestViewAction.clickChildviewWithId(R.id.itemDownArrow)));
        onView(withId(R.id.itemRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testItem")), TestViewAction.clickChildviewWithId(R.id.btnEditInvItem)));
        onView(withId(R.id.editInvItemName)).perform(clearText());
        onView(withId(R.id.editInvItemName)).perform(typeText("editedTestItem"));
        onView(withId(R.id.editInvQuantity)).perform(clearText());
        onView(withId(R.id.editInvQuantity)).perform(typeText("100"));
        onView(withId(R.id.btnEditInvSubmit)).perform(scrollTo(), click());

        // view edited item information
        onView(withId(R.id.itemRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("editedTestItem")), click()));
        Thread.sleep(1000);
        pressBack();

        // check if the item name changed
        assertEquals(inventorydb.getInventoryList().get(2).getName(), "editedTestItem");

        // remove the newly added item
        onView(withId(R.id.itemRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("editedTestItem")), TestViewAction.clickChildviewWithId(R.id.itemDownArrow)));
        onView(withId(R.id.itemRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("editedTestItem")), TestViewAction.clickChildviewWithId(R.id.btnRemoveInvItem)));
    }

    @Test
    public void setThresholdTest() throws InterruptedException{ // User Story: Set Automatic Adding Threshold
        onView(withId(R.id.btnGoToAddInvActivity)).perform(click());

        // add new item
        onView(withId(R.id.inputInventoryItemName)).perform(typeText("testItem"));
        onView(withId(R.id.inputInventoryItemQuantity)).perform(typeText("1"));
        onView(withId(R.id.inputInventoryItemUnits)).perform(typeText("testUnits"));
        onView(withId(R.id.btnAddInventoryItem)).perform(scrollTo(), click());

        // edit item fields
        onView(withId(R.id.itemRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testItem")), TestViewAction.clickChildviewWithId(R.id.itemDownArrow)));
        onView(withId(R.id.itemRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testItem")), TestViewAction.clickChildviewWithId(R.id.btnEditInvItem)));
        onView(withId(R.id.editInvThreshold)).perform(clearText());
        onView(withId(R.id.editInvThreshold)).perform(typeText("1"));
        onView(withId(R.id.btnEditInvSubmit)).perform(scrollTo(), click());

        // check new item Threshold
        assertEquals(inventorydb.getInventoryList().get(2).getThresholdQuantity(), 1);

        // view edited item information
        onView(withId(R.id.itemRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testItem")), click()));
        Thread.sleep(3000);
        pressBack();

        onView(withId(R.id.itemRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testItem")), TestViewAction.clickChildviewWithId(R.id.itemDownArrow)));
        onView(withId(R.id.itemRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testItem")), TestViewAction.clickChildviewWithId(R.id.btnRemoveInvItem)));
    }


    @Test
    public void buyTest() throws InterruptedException { // User Story: Ask User for Quantity to Buy
        // navigate to Grocery List screen -- TO VERIFY THAT THE TEST ITEM IS NOT IN GROCERY LIST
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Grocery List")).perform(click());
        Thread.sleep(3000);

        // navigate to Inventory List screen
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Current Inventory")).perform(click());
        Thread.sleep(3000);

        onView(withId(R.id.btnGoToAddInvActivity)).perform(click());

        // add new item
        onView(withId(R.id.inputInventoryItemName)).perform(typeText("testItem"));
        onView(withId(R.id.inputInventoryItemQuantity)).perform(typeText("1"));
        onView(withId(R.id.inputInventoryItemUnits)).perform(typeText("testUnits"));
        onView(withId(R.id.btnAddInventoryItem)).perform(scrollTo(), click());

        // add to Grocery List
        onView(withId(R.id.itemRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testItem")), TestViewAction.clickChildviewWithId(R.id.itemDownArrow)));
        onView(withId(R.id.itemRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testItem")), TestViewAction.clickChildviewWithId(R.id.btnAddInvItemToGrocery)));
        onView(allOf(isAssignableFrom(EditText.class))).perform(typeText("1"));
        onView(withId(android.R.id.button1)).perform(click());

        // check our db if the testItem get added
        assertEquals(grocerydb.getGroceryList().get(2).getName(), "testItem");

        // navigate to Grocery List screen
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Grocery List")).perform(click());
        Thread.sleep(3000);

        // remove the bought item from grocery
        onView(withId(R.id.groceryListRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testItem")), TestViewAction.clickChildviewWithId(R.id.groceryDownArrow)));
        Thread.sleep(1000);
        onView(withId(R.id.groceryListRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testItem")), TestViewAction.clickChildviewWithId(R.id.btnRemoveGroceryItem)));
        Thread.sleep(1000);

        // navigate to Inventory List screen
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Current Inventory")).perform(click());
        Thread.sleep(3000);

        // remove the newly added item
        onView(withId(R.id.itemRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testItem")), TestViewAction.clickChildviewWithId(R.id.itemDownArrow)));
        Thread.sleep(1000);
        onView(withId(R.id.itemRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testItem")), TestViewAction.clickChildviewWithId(R.id.btnRemoveInvItem)));
        Thread.sleep(1000);
    }

    @Test
    public void editValuesTest() throws InterruptedException{
        // User Story: Edit and View Price of Items
        // User Story: Add/Edit Amount of Calories
        // User Story: Add/Edit Allergy Information
        // User Story: Edit the Thresholds

        onView(withId(R.id.btnGoToAddInvActivity)).perform(click());

        // add new item
        onView(withId(R.id.inputInventoryItemName)).perform(typeText("testItem"));
        onView(withId(R.id.inputInventoryItemQuantity)).perform(typeText("10"));
        onView(withId(R.id.inputInventoryItemUnits)).perform(typeText("testUnits"));
        onView(withId(R.id.btnAddInventoryItem)).perform(scrollTo(), click());

        // click edit button
        onView(withId(R.id.itemRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testItem")), TestViewAction.clickChildviewWithId(R.id.itemDownArrow)));
        onView(withId(R.id.itemRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testItem")), TestViewAction.clickChildviewWithId(R.id.btnEditInvItem)));

        // edit price
        onView(withId(R.id.editInvPrice)).perform(clearText());
        onView(withId(R.id.editInvPrice)).perform(typeText("50"));

        // edit threshold
        Thread.sleep(2000);
        onView(withId(R.id.editInvThreshold)).perform(clearText());
        onView(withId(R.id.editInvThreshold)).perform(typeText("3"));

        onView(withId(R.id.btnEditInvSubmit)).perform(scrollTo());
        // edit calories
        Thread.sleep(2000);
        onView(withId(R.id.editInvCalories)).perform(clearText());
        onView(withId(R.id.editInvCalories)).perform(typeText("50"));

        // edit allergies
        onView(withText("Contains Lactose")).perform(click());
        onView(withText("Contains Gluten")).perform(click());
        onView(withText("Contains Eggs")).perform(click());
        onView(withText("Contains Nuts")).perform(click());
        onView(withText("Contains Fish")).perform(click());
        onView(withText("Contains Soy")).perform(click());

        // submit
        onView(withId(R.id.btnEditInvSubmit)).perform(scrollTo(), click());

        // check if the item was edited
        assertEquals(inventorydb.getInventoryList().get(2).getThresholdQuantity(), 3);
        assertEquals(inventorydb.getInventoryList().get(2).getCaloriesPerUnit(), 50);

        // view edited item information
        onView(withId(R.id.itemRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testItem")), click()));
        Thread.sleep(3000);
        pressBack();

        Thread.sleep(2000);
        // remove the newly added item
        onView(withId(R.id.itemRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testItem")), TestViewAction.clickChildviewWithId(R.id.itemDownArrow)));
        Thread.sleep(1000);
        onView(withId(R.id.itemRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testItem")), TestViewAction.clickChildviewWithId(R.id.btnRemoveInvItem)));
        Thread.sleep(1000);
    }
    // --------------------------------------------------------------------

}
