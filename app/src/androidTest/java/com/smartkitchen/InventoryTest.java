package com.smartkitchen;

import android.view.View;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.smartkitchen.application.MainActivity;
import com.smartkitchen.application.Services;
import com.smartkitchen.business.GroceryActions;
import com.smartkitchen.business.InventoryActions;
import com.smartkitchen.business.ListActions;
import com.smartkitchen.objects.Item;
import com.smartkitchen.persistence.hsqldb.GroceryPersistenceDB;
import com.smartkitchen.persistence.hsqldb.InventoryPersistenceDB;
import com.smartkitchen.presentation.ItemRecViewAdapter;
import com.smartkitchen.TestHelpers.TestViewAction;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsAnything.anything;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class InventoryTest{
    private final int TEST_ITEM = 2;

    private GroceryPersistenceDB grocerydb;
    private InventoryPersistenceDB inventorydb;
    List<Item> inventoryList;
    private Item testItem;

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);


    @Before
    public void setupDatabase(){
        grocerydb = (GroceryPersistenceDB) Services.getGroceryPersistence();
        inventorydb = (InventoryPersistenceDB) Services.getInventoryPersistence();
        inventoryList = inventorydb.getInventoryList();
    }

    // ---------------- AUTOMATED ACCEPTANCE TESTS ----------------------

    @Test
    public void viewInventoryTest(){ // User Story: View Current Inventory
        // default view
        assertEquals(inventorydb.getInventoryList().get(0).getName(), "Cheese"); // just check to see if we get the right item on the list

    }

    @Test
    public void addRemoveInventoryItemTest() throws InterruptedException{ // User Story: Add Items to the Current Inventory AND Remove Items from the Current Inventory
        onView(withId(R.id.btnGoToAddInvActivity)).perform(click());

        // add new item
        onView(withId(R.id.inputInventoryItemName)).perform(typeText("testItem"));
        onView(withId(R.id.inputInventoryItemQuantity)).perform(typeText("1"));
        onView(withId(R.id.inputInventoryItemUnits)).perform(typeText("testUnits"));
        onView(withId(R.id.btnAddInventoryItem)).perform(scrollTo(), click());

        // remove the newly added item
        onView(withId(R.id.itemRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testItem")), TestViewAction.clickChildviewWithId(R.id.itemDownArrow)));
        Thread.sleep(1000);
        onView(withId(R.id.itemRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testItem")), TestViewAction.clickChildviewWithId(R.id.btnRemoveInvItem)));
        Thread.sleep(1000);
    }

    @Test
    public void editInventoryItemTest() throws InterruptedException{ // User Story: Edit Items in the Current Inventory
        onView(withId(R.id.btnGoToAddInvActivity)).perform(click());

        // add new item
        onView(withId(R.id.inputInventoryItemName)).perform(typeText("testItem"));
        onView(withId(R.id.inputInventoryItemQuantity)).perform(typeText("1"));
        onView(withId(R.id.inputInventoryItemUnits)).perform(typeText("testUnits"));
        onView(withId(R.id.btnAddInventoryItem)).perform(scrollTo(), click());

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

        // view edited item information
        onView(withId(R.id.itemRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testItem")), click()));
        Thread.sleep(3000);
        pressBack();

        onView(withId(R.id.itemRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testItem")), TestViewAction.clickChildviewWithId(R.id.itemDownArrow)));
        onView(withId(R.id.itemRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testItem")), TestViewAction.clickChildviewWithId(R.id.btnRemoveInvItem)));
    }

    /*
    @Test
    public void askQuantityTest() throws InterruptedException{ // User Story: Ask User for Quantity to Buy
        onView(withId(R.id.btnGoToAddInvActivity)).perform(click());

        // add new item
        onView(withId(R.id.inputInventoryItemName)).perform(typeText("testItem"));
        onView(withId(R.id.inputInventoryItemQuantity)).perform(typeText("1"));
        onView(withId(R.id.inputInventoryItemUnits)).perform(typeText("testUnits"));
        onView(withId(R.id.btnAddInventoryItem)).perform(scrollTo(), click());

        // add to Grocery List
        //onView(withId(R.id.itemRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testItem")), TestViewAction.clickChildviewWithId(R.id.itemDownArrow)));
        //onView(withId(R.id.itemRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testItem")), TestViewAction.clickChildviewWithId(R.id.btnAddInvItemToGrocery)));
        //onView(withId(R.id.))


        // remove the newly added item
        onView(withId(R.id.itemRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testItem")), TestViewAction.clickChildviewWithId(R.id.itemDownArrow)));
        Thread.sleep(1000);
        onView(withId(R.id.itemRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testItem")), TestViewAction.clickChildviewWithId(R.id.btnRemoveInvItem)));
        Thread.sleep(1000);
    }
     */

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

        // view edited item information
        onView(withId(R.id.itemRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testItem")), click()));
        onView(withId(R.id.btnBackToList)).perform(scrollTo());
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
