package com.smartkitchen.presentation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.smartkitchen.business.GroceryActions;
import com.smartkitchen.business.IGroceryActions;
import com.smartkitchen.business.IInventoryActions;
import com.smartkitchen.business.IListActions;
import com.smartkitchen.business.InvalidInputException;
import com.smartkitchen.business.InventoryActions;
import com.smartkitchen.business.ListActions;
import com.smartkitchen.objects.Allergies;
import com.smartkitchen.objects.Item;
import com.smartkitchen.R;

import java.util.ArrayList;

public class AddInventoryItemActivity extends ParentActivity {

    IListActions listActions = new ListActions();
    IInventoryActions inventoryActions = new InventoryActions();
    IGroceryActions groceryActions = new GroceryActions();
    //Input Fields for item information
    private EditText inputName, inputQuantity, inputUnits, inputThreshold, inputPrice, inputCalories;
    //Checkbox to enable/disable threshold
    private CheckBox enableThreshold;
    private CheckBox checkLactose, checkGluten, checkNuts, checkFish, checkEgg, checkSoy;
    //text with information about threshold, visibility changes if enabled/disabled
    private TextView txtThreshold;
    //Buttons for adding an item or cancelling the add activity
    private Button btnCancel, btnAdd;

    //Flag if threshold is enabled
    private boolean thresholdEnabled;

    //Creates the add inventory item view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item_screen);
        setTitle("Add New Inventory Item");
        setColour(ContextCompat.getColor(this, R.color.blueColour3));

        //Initializes the views, defaults threshold to false
        initViews();
        thresholdEnabled = false;

        //Create on checked listener for checkbox
        enableThreshold.setOnCheckedChangeListener((buttonView, isChecked) -> {
            //Enabling threshold sets text and input fields to visible
            if(isChecked){
                txtThreshold.setVisibility(View.VISIBLE);
                inputThreshold.setVisibility(View.VISIBLE);
                thresholdEnabled = true;
            }
            //Disabling threshold sets text and input fields to gone, sets threshold to 0
            else{
                inputThreshold.setText("" + 0);
                txtThreshold.setVisibility(View.GONE);
                inputThreshold.setVisibility(View.GONE);
                thresholdEnabled = false;
            }
        });

        //Creates on click listener for cancel button, just returns to inventory list
        btnCancel.setOnClickListener(v -> finish());

        //Creates on click listener for add button
        btnAdd.setOnClickListener(v -> {
            //Initializes new item based on inputted information
            Item newItem = initItem();
            try {
                //If an item with this name does not exist yet, then add it in
                if(listActions.getDuplicateByName(newItem, inventoryActions.getInventoryList()) == null) {
                    inventoryActions.addToInventory(newItem);
                    //Checks if the item will be added to grocery because of quantity<threshold
                    boolean enteredThreshold = groceryActions.thresholdAddToGrocery(newItem, AddInventoryItemActivity.this, true);
                    //If not, just return to the main list as usual
                    if (!enteredThreshold) {
                        finish();
                    }
                }
                else {
                    Toast.makeText(AddInventoryItemActivity.this, "An Item with this name already exists in Inventory.", Toast.LENGTH_SHORT).show();
                }
            } catch (InvalidInputException e) {
                Toast.makeText(AddInventoryItemActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }

    //Initializes item based on inputted information
    private Item initItem(){
        String name = inputName.getText().toString();
        int quantity = -1;
        if(!inputQuantity.getText().toString().equals(""))
            quantity = Integer.parseInt(inputQuantity.getText().toString());
        String units = inputUnits.getText().toString();
        //Defaults threshold to 0, takes inputted number if enabled
        int threshold = 0;
        if(thresholdEnabled) {
            threshold = -1;
            if (!inputThreshold.getText().toString().equals("")) {
                threshold = Integer.parseInt(inputThreshold.getText().toString());
            }
        }
        double price = 0;
        if(!inputPrice.getText().toString().equals(""))
            price = Double.parseDouble(inputPrice.getText().toString());
        int calories = 0;
        if(!inputCalories.getText().toString().equals(""))
            calories = Integer.parseInt(inputCalories.getText().toString());
        ArrayList<String> allergies = getAllergies();
        return new Item(name, quantity, units, 0, threshold, allergies, calories, price);
    }

    private ArrayList<String> getAllergies(){
        ArrayList<String> allergies = new ArrayList<>();
        if(checkNuts.isChecked())
            allergies.add(Allergies.NUTS.getText());
        if(checkSoy.isChecked())
            allergies.add(Allergies.SOY.getText());
        if(checkLactose.isChecked())
            allergies.add(Allergies.LACTOSE.getText());
        if(checkGluten.isChecked())
            allergies.add(Allergies.GLUTEN.getText());
        if(checkFish.isChecked())
            allergies.add(Allergies.FISH.getText());
        if(checkEgg.isChecked())
            allergies.add(Allergies.EGGS.getText());
        return allergies;
    }

    //Initializes the UI elements
    private void initViews(){
        inputName = findViewById(R.id.inputInventoryItemName);
        inputQuantity = findViewById(R.id.inputInventoryItemQuantity);
        inputUnits = findViewById(R.id.inputInventoryItemUnits);
        inputThreshold = findViewById(R.id.inputInventoryItemThreshold);
        inputPrice = findViewById(R.id.inputInventoryItemPrice);
        inputCalories = findViewById(R.id.inputInventoryItemCalories);

        enableThreshold = findViewById(R.id.enableThresholdCheckBox);
        txtThreshold = findViewById(R.id.txtThreshold);

        btnCancel = findViewById(R.id.btnCancelAddInventory);
        btnAdd = findViewById(R.id.btnAddInventoryItem);

        checkEgg = findViewById(R.id.inputCheckInvEggs);
        checkFish = findViewById(R.id.inputCheckInvFish);
        checkGluten = findViewById(R.id.inputCheckInvGluten);
        checkLactose = findViewById(R.id.inputCheckInvLactose);
        checkSoy = findViewById(R.id.inputCheckInvSoy);
        checkNuts = findViewById(R.id.inputCheckInvNuts);
    }
}
