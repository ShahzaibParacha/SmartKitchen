package com.smartkitchen;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import java.util.List;

import com.smartkitchen.TestHelpers.TestViewAction;
import com.smartkitchen.application.MainActivity;
import com.smartkitchen.application.Services;
import com.smartkitchen.objects.Recipe;
import com.smartkitchen.persistence.hsqldb.RecipePersistenceDB;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class RecipeSystemTest{
    private RecipePersistenceDB recipedb;
    List<Recipe> recipeList;

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setupDatabase(){
        recipeList = Services.getRecipePersistence().getRecipeList();
        recipedb = (RecipePersistenceDB) Services.getRecipePersistence();
    }

    @Test
    public void addRemoveRecipeTest() throws InterruptedException{
        // USER STORY: View List of Recipes
        // https://code.cs.umanitoba.ca/3350-winter-2021-a01/refrigator-tracker-group-10/-/issues/122
        // navigate to Recipes screen
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Recipes")).perform(click());
        Thread.sleep(2000);

        // USER STORY: Add Recipe
        // https://code.cs.umanitoba.ca/3350-winter-2021-a01/refrigator-tracker-group-10/-/issues/130
        // add new Recipe
        // add recipe name
        onView(withId(R.id.btnGoToAddRecipeActivity)).perform(click());
        onView(withId(R.id.addRecipeName)).perform(typeText("testRecipeName"));
        Espresso.closeSoftKeyboard();

        // add test Ingredient
        onView(withId(R.id.btnAddIngredient)).perform(click());
        onView(withHint("Name")).perform(typeText("testIngredient"));
        onView(withHint("Quantity")).perform(typeText("1"));
        onView(withHint("Units")).perform(typeText("testUnits"));
        Espresso.closeSoftKeyboard();
        onView(withId(android.R.id.button1)).perform(click());

        // add test Instruction
        onView(withId(R.id.btnAddInstruction)).perform(scrollTo(), click());
        onView(withHint("Instruction")).perform(typeText("testInstruction"));
        Espresso.closeSoftKeyboard();
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.btnSubmitAddRecipe)).perform(scrollTo(), click()); // click submit -- create recipe

        // assertions -- check if new recipe has been added
        assertEquals(recipedb.getRecipeList().get(1).getName(), "testRecipeName");

        // USER STORY: Edit Recipe
        // https://code.cs.umanitoba.ca/3350-winter-2021-a01/refrigator-tracker-group-10/-/issues/138
        // edit recipe
        // name
        onView(withId(R.id.recipesRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testRecipeName")), TestViewAction.clickChildviewWithId(R.id.itemDownArrow)));
        onView(withId(R.id.recipesRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testRecipeName")), TestViewAction.clickChildviewWithId(R.id.btnEditRecipe)));
        onView(withText("testRecipeName")).perform(clearText());
        onView(withHint("Name")).perform(typeText("editedTestRecipe"));
        Espresso.closeSoftKeyboard();

        // USER STORY: Add/Remove Ingredient
        // https://code.cs.umanitoba.ca/3350-winter-2021-a01/refrigator-tracker-group-10/-/issues/140
        // add/remove ingredient
        onView(withId(R.id.btnRemoveIngredient)).perform(click()); // remove ingredient
        onView(withId(R.id.btnAddIngredient)).perform(click());
        onView(withHint("Name")).perform(typeText("testIngredient"));
        onView(withHint("Quantity")).perform(typeText("1"));
        onView(withHint("Units")).perform(typeText("testUnits"));
        Espresso.closeSoftKeyboard();
        onView(withId(android.R.id.button1)).perform(click());

        // USER STORY: Edit Ingredient
        // https://code.cs.umanitoba.ca/3350-winter-2021-a01/refrigator-tracker-group-10/-/issues/143
        // edit ingredient
        onView(withId(R.id.btnEditIngredient)).perform(click());
        onView(withText("testIngredient")).perform(clearText());
        onView(withHint("Name")).perform(typeText("editedIngredient"));
        Espresso.closeSoftKeyboard();
        onView(withId(android.R.id.button1)).perform(click());

        // USER STORY: Add/Remove Instruction
        // https://code.cs.umanitoba.ca/3350-winter-2021-a01/refrigator-tracker-group-10/-/issues/144
        // add/remove instruction
        onView(withId(R.id.btnRemoveInstruction)).perform(scrollTo(), click()); // remove instruction
        onView(withId(R.id.btnAddInstruction)).perform(scrollTo(), click());
        onView(withHint("Instruction")).perform(typeText("testInstruction"));
        Espresso.closeSoftKeyboard();
        onView(withId(android.R.id.button1)).perform(click());

        // USER STORY: Edit Instruction
        // https://code.cs.umanitoba.ca/3350-winter-2021-a01/refrigator-tracker-group-10/-/issues/145
        // edit instruction
        onView(withId(R.id.btnEditInstruction)).perform(click());
        onView(withText("testInstruction")).perform(clearText());
        onView(withHint("Instruction")).perform(typeText("editedInstruction"));
        Espresso.closeSoftKeyboard();
        onView(withId(android.R.id.button1)).perform(click());

        // submit recipe changes
        onView(withId(R.id.btnSubmitEditRecipe)).perform(scrollTo(), click());

        // remove recipe
        onView(withId(R.id.recipesRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("editedTestRecipe")), TestViewAction.clickChildviewWithId(R.id.itemDownArrow)));
        onView(withId(R.id.recipesRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("editedTestRecipe")), TestViewAction.clickChildviewWithId(R.id.btnRemoveRecipe)));
    }

    @Test
    public void makeRecipeTest() throws InterruptedException{
        // navigate to Recipes screen
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Recipes")).perform(click());
        Thread.sleep(2000);

        // add new Recipe
        // add recipe name
        onView(withId(R.id.btnGoToAddRecipeActivity)).perform(click());
        onView(withId(R.id.addRecipeName)).perform(typeText("testRecipeName"));
        Espresso.closeSoftKeyboard();

        // add test Ingredient
        onView(withId(R.id.btnAddIngredient)).perform(click());
        onView(withHint("Name")).perform(typeText("testIngredient"));
        onView(withHint("Quantity")).perform(typeText("1"));
        onView(withHint("Units")).perform(typeText("testUnits"));
        Espresso.closeSoftKeyboard();
        onView(withId(android.R.id.button1)).perform(click());

        // add test Instruction
        onView(withId(R.id.btnAddInstruction)).perform(scrollTo(), click());
        onView(withHint("Instruction")).perform(typeText("testInstruction"));
        Espresso.closeSoftKeyboard();
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.btnSubmitAddRecipe)).perform(scrollTo(), click()); // click submit -- create recipe

        // USER STORY: Add Missing Ingredients to Grocery List
        // https://code.cs.umanitoba.ca/3350-winter-2021-a01/refrigator-tracker-group-10/-/issues/148
        // at this point: not enough ingredients --> add them to grocery list
        onView(withId(R.id.recipesRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testRecipeName")), TestViewAction.clickChildviewWithId(R.id.itemDownArrow)));
        onView(withId(R.id.recipesRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testRecipeName")), TestViewAction.clickChildviewWithId(R.id.btnAddToGroceryList)));

        // go to grocery list --> buy item
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Grocery List")).perform(click());
        Thread.sleep(2000);

        Thread.sleep(3000);
        onView(withId(R.id.groceryListRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testIngredient")), TestViewAction.clickChildviewWithId(R.id.groceryDownArrow)));
        onView(withId(R.id.groceryListRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testIngredient")), TestViewAction.clickChildviewWithId(R.id.btnBuyItem))); // buy item

        // go to inventory to check if the item is bought
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Current Inventory")).perform(click());
        Thread.sleep(2000);

        // USER STORY: See Available Ingredients
        // https://code.cs.umanitoba.ca/3350-winter-2021-a01/refrigator-tracker-group-10/-/issues/152
        // USER STORY: Make Recipe
        // https://code.cs.umanitoba.ca/3350-winter-2021-a01/refrigator-tracker-group-10/-/issues/150
        // go back to Recipes to make the recipe
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Recipes")).perform(click());
        Thread.sleep(2000);

        onView(withId(R.id.recipesRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testRecipeName")), TestViewAction.clickChildviewWithId(R.id.itemDownArrow)));
        onView(withId(R.id.recipesRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testRecipeName")), TestViewAction.clickChildviewWithId(R.id.btnMakeRecipe)));

        // go to inventory to check if the item quantity has decreased
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Current Inventory")).perform(click());
        Thread.sleep(2000);

        // delete Inventory item for test cleanup purposes
        onView(withId(R.id.itemRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testIngredient")), TestViewAction.clickChildviewWithId(R.id.itemDownArrow)));
        onView(withId(R.id.itemRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testIngredient")), TestViewAction.clickChildviewWithId(R.id.btnRemoveInvItem)));

        // navigate to Recipes screen
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Recipes")).perform(click());
        Thread.sleep(2000);

        // remove recipe
        onView(withId(R.id.recipesRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testRecipeName")), TestViewAction.clickChildviewWithId(R.id.itemDownArrow)));
        onView(withId(R.id.recipesRecView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("testRecipeName")), TestViewAction.clickChildviewWithId(R.id.btnRemoveRecipe)));
    }

}