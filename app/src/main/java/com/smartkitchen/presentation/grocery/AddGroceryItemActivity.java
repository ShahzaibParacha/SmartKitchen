package com.smartkitchen.presentation.grocery;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.smartkitchen.business.implementation.AllergyActions;
import com.smartkitchen.business.implementation.GroceryActions;
import com.smartkitchen.business.interfaces.IAllergyActions;
import com.smartkitchen.business.interfaces.IGroceryActions;
import com.smartkitchen.business.interfaces.IListActions;
import com.smartkitchen.business.InvalidInputException;
import com.smartkitchen.business.implementation.ListActions;
import com.smartkitchen.objects.Item;
import com.smartkitchen.R;
import com.smartkitchen.presentation.ParentActivity;

import java.util.ArrayList;

//Screen for adding an item to the grocery list
public class AddGroceryItemActivity extends ParentActivity {

    //Access to relevant business methods
    IListActions listActions = new ListActions();
    IGroceryActions groceryActions = new GroceryActions();
    IAllergyActions allergyActions;

    //Input Fields for item information
    private EditText inputName, inputQuantityToBuy, inputUnits, inputPrice, inputCalories;
    //Checkboxes for allergy information
    private CheckBox checkLactose, checkGluten, checkNuts, checkFish, checkEgg, checkSoy;
    //Buttons to add an item and cancel add activity
    private Button btnCancel, btnAdd;

    //Creates the Add Grocery View
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item_to_grocery_screen);
        //Sets the title and colour of the taskbar
        setTitle("Add New Grocery List Item");
        setColour(ContextCompat.getColor(this, R.color.greenColour3));

        //Initializes the UI elements
        initViews();
        allergyActions = new AllergyActions(checkNuts, checkSoy, checkLactose, checkGluten, checkFish, checkEgg);

        //Creates on click listener for cancel button, just returns to grocery list screen
        btnCancel.setOnClickListener(v -> finish());

        //Creates on click listener for add button
        btnAdd.setOnClickListener(v -> {
            //Creates item based on inputted values and adds
            Item newItem = initItem();
            try {
                //If an item with this name does not exist yet, then add it in
                if (listActions.getDuplicateByName(newItem, groceryActions.getGroceryList()) == null) {
                    groceryActions.addToGrocery(newItem);
                    //Once the item is added, return to grocery list screen
                    finish();
                } else {
                    Toast.makeText(AddGroceryItemActivity.this, "An item with this name already exists in Grocery List.", Toast.LENGTH_SHORT).show();
                }
            } catch (InvalidInputException e) {
                Toast.makeText(AddGroceryItemActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Initialize new item with the inputted information, sets default values for empty fields
    private Item initItem() {
        String name = inputName.getText().toString();
        int quantityToBuy = -1;
        if (!inputQuantityToBuy.getText().toString().equals(""))
            quantityToBuy = Integer.parseInt(inputQuantityToBuy.getText().toString());
        String units = inputUnits.getText().toString();
        double price = 0;
        if (!inputPrice.getText().toString().equals(""))
            price = Double.parseDouble(inputPrice.getText().toString());
        int calories = 0;
        if (!inputCalories.getText().toString().equals(""))
            calories = Integer.parseInt(inputCalories.getText().toString());
        ArrayList<String> allergies = allergyActions.getAllergies();
        return new Item(name, 0, units, quantityToBuy, 0, allergies, calories, price);
    }

    //Initialize the UI elements
    private void initViews() {
        inputName = findViewById(R.id.inputGroceryItemName);
        inputQuantityToBuy = findViewById(R.id.inputGroceryItemQuantity);
        inputUnits = findViewById(R.id.inputGroceryItemUnits);
        inputPrice = findViewById(R.id.inputGroceryItemPrice);
        inputCalories = findViewById(R.id.inputGroceryItemCalories);

        btnCancel = findViewById(R.id.btnCancelAddGrocery);
        btnAdd = findViewById(R.id.btnAddGroceryItem);

        checkEgg = findViewById(R.id.inputCheckGroceryEggs);
        checkFish = findViewById(R.id.inputCheckGroceryFish);
        checkGluten = findViewById(R.id.inputCheckGroceryGluten);
        checkLactose = findViewById(R.id.inputCheckGroceryLactose);
        checkSoy = findViewById(R.id.inputCheckGrocerySoy);
        checkNuts = findViewById(R.id.inputCheckGroceryNuts);
    }
}
