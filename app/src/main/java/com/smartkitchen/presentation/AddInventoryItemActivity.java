package com.smartkitchen.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.smartkitchen.business.IListActions;
import com.smartkitchen.business.ListActions;
import com.smartkitchen.objects.Allergies;
import com.smartkitchen.objects.Item;
import com.smartkitchen.R;
import com.smartkitchen.persistence.DBManager;

import java.util.ArrayList;

public class AddInventoryItemActivity extends ParentActivity {

    IListActions listActions = new ListActions();
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

        //Initializes the views, defaults threshold to false
        initViews();
        thresholdEnabled = false;

        //Create on checked listener for checkbox
        enableThreshold.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
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
            }
        });

        //Creates on click listener for cancel button, just returns to inventory list
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddInventoryItemActivity.this, CurrentInventoryActivity.class);
                startActivity(intent);
            }
        });

        //Creates on click listener for add button
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initializes new item based on inputted information
                Item newItem = initItem();
                try {
                    //If an item with this name does not exist yet, then add it in
                    if(listActions.getDuplicateByName(newItem, listActions.getInventoryList()) == null) {
                        listActions.addToInventory(newItem);
                        //Checks if the item will be added to grocery because of quantity<threshold
                        boolean enteredThreshold = listActions.thresholdAddToGrocery(newItem, AddInventoryItemActivity.this, true);
                        //If not, just return to the main list as usual
                        if (!enteredThreshold) {
                            Intent intent = new Intent(AddInventoryItemActivity.this, CurrentInventoryActivity.class);
                            startActivity(intent);
                        }
                    }
                    else {
                        System.out.println("Duplicate exists");
                        Toast.makeText(AddInventoryItemActivity.this, "An Item with this name already exists in Inventory.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(AddInventoryItemActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

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
        ArrayList<String> allergies = new ArrayList<String>();
        if(checkNuts.isChecked())
            allergies.add(Allergies.NUTS);
        if(checkSoy.isChecked())
            allergies.add(Allergies.SOY);
        if(checkLactose.isChecked())
            allergies.add(Allergies.LACTOSE);
        if(checkGluten.isChecked())
            allergies.add(Allergies.GLUTEN);
        if(checkFish.isChecked())
            allergies.add(Allergies.FISH);
        if(checkEgg.isChecked())
            allergies.add(Allergies.EGGS);
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
