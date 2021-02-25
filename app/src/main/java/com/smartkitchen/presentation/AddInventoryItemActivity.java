package com.smartkitchen.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.smartkitchen.objects.Item;
import com.smartkitchen.R;
import com.smartkitchen.persistence.DBManager;

public class AddInventoryItemActivity extends AppCompatActivity {

    //Input Fields for item information
    private EditText inputName, inputQuantity, inputUnits, inputThreshold;
    //Checkbox to enable/disable threshold
    private CheckBox enableThreshold;
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
                DBManager.getInventoryDB().addToInventory(newItem);
                //Return to the inventory screen
                Intent intent = new Intent(AddInventoryItemActivity.this, CurrentInventoryActivity.class);
                startActivity(intent);
            }
        });
    }

    //Initializes item based on inputted information
    private Item initItem(){
        String name = inputName.getText().toString();
        int quantity = Integer.parseInt(inputQuantity.getText().toString());
        String units = inputUnits.getText().toString();
        //Defaults threshold to 0, takes inputted number if enabled
        int threshold = 0;
        if(thresholdEnabled)
            threshold = Integer.parseInt(inputThreshold.getText().toString());
        return new Item(name, quantity, units, 0, threshold);
    }

    //Initializes the UI elements
    private void initViews(){
        inputName = findViewById(R.id.inputInventoryItemName);
        inputQuantity = findViewById(R.id.inputInventoryItemQuantity);
        inputUnits = findViewById(R.id.inputInventoryItemUnits);
        inputThreshold = findViewById(R.id.inputInventoryItemThreshold);

        enableThreshold = findViewById(R.id.enableThresholdCheckBox);
        txtThreshold = findViewById(R.id.txtThreshold);

        btnCancel = findViewById(R.id.btnCancelAddInventory);
        btnAdd = findViewById(R.id.btnAddInventoryItem);
    }
}