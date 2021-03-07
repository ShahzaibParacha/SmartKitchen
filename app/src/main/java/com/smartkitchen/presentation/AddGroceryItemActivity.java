package com.smartkitchen.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.smartkitchen.business.IListActions;
import com.smartkitchen.business.ListActions;
import com.smartkitchen.objects.Item;
import com.smartkitchen.R;
import com.smartkitchen.persistence.DBManager;

public class AddGroceryItemActivity extends AppCompatActivity {

    IListActions listActions = new ListActions();
    //Input Fields for item information
    private EditText inputName, inputQuantityToBuy, inputUnits;
    //Buttons to add an item and cancel add activity
    private Button btnCancel, btnAdd;


    //Creates the Add Grocery View
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item_to_grocery_screen);


        //Initializes the UI elements
        initViews();

        //Creates on click listener for cancel button, just returns to grocery list screen
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddGroceryItemActivity.this, GroceryListActivity.class);
                startActivity(intent);
            }
        });

        //Creates on click listener for add button
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creates item based on inputted values and adds
                Item newItem = initItem();
                try {
                    listActions.addToGrocery(newItem);
                    //Once the item is added, return to grocery list screen
                    Intent intent = new Intent(AddGroceryItemActivity.this, GroceryListActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
    }

    //Initialize new item with the inputted information
    private Item initItem(){
        String name = inputName.getText().toString();
        int quantityToBuy = Integer.parseInt(inputQuantityToBuy.getText().toString());
        String units = inputUnits.getText().toString();
        return new Item(name, 0, units, quantityToBuy, 0, null, 0, 0);
    }

    //Initialize the UI elements
    private void initViews(){
        inputName = findViewById(R.id.inputGroceryItemName);
        inputQuantityToBuy = findViewById(R.id.inputGroceryItemQuantity);
        inputUnits = findViewById(R.id.inputGroceryItemUnits);

        btnCancel = findViewById(R.id.btnCancelAddGrocery);
        btnAdd = findViewById(R.id.btnAddGroceryItem);
    }
}
