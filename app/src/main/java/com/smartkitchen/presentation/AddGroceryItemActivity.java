package com.smartkitchen.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.smartkitchen.business.IListActions;
import com.smartkitchen.business.ListActions;
import com.smartkitchen.objects.Allergies;
import com.smartkitchen.objects.Item;
import com.smartkitchen.R;
import com.smartkitchen.persistence.DBManager;

import java.util.ArrayList;

public class AddGroceryItemActivity extends ParentActivity {

    IListActions listActions = new ListActions();
    //Input Fields for item information
    private EditText inputName, inputQuantityToBuy, inputUnits, inputPrice, inputCalories;

    private CheckBox checkLactose, checkGluten, checkNuts, checkFish, checkEgg, checkSoy;
    //Buttons to add an item and cancel add activity
    private Button btnCancel, btnAdd;


    //Creates the Add Grocery View
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item_to_grocery_screen);
        setTitle("Add New Grocery List Item");


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
                    if(listActions.getDuplicateByName(newItem, listActions.getGroceryList()) == null) {
                        listActions.addToGrocery(newItem);
                        //Once the item is added, return to grocery list screen
                        Intent intent = new Intent(AddGroceryItemActivity.this, GroceryListActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(AddGroceryItemActivity.this, "An item with this name already exists in Grocery List.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(AddGroceryItemActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Initialize new item with the inputted information
    private Item initItem(){
        String name = inputName.getText().toString();
        int quantityToBuy = -1;
        if(!inputQuantityToBuy.getText().toString().equals(""))
            quantityToBuy = Integer.parseInt(inputQuantityToBuy.getText().toString());
        String units = inputUnits.getText().toString();
        double price = 0;
        if(!inputPrice.getText().toString().equals(""))
            price = Double.parseDouble(inputPrice.getText().toString());
        int calories = 0;
        if(!inputCalories.getText().toString().equals(""))
            calories = Integer.parseInt(inputCalories.getText().toString());
        ArrayList<String> allergies = getAllergies();
        return new Item(name, 0, units, quantityToBuy, 0, allergies, calories, price);
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

    //Initialize the UI elements
    private void initViews(){
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
