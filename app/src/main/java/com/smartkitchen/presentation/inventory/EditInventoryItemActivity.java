package com.smartkitchen.presentation.inventory;

import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.smartkitchen.business.implementation.AllergyActions;
import com.smartkitchen.business.implementation.GroceryActions;
import com.smartkitchen.business.interfaces.IAllergyActions;
import com.smartkitchen.business.interfaces.IGroceryActions;
import com.smartkitchen.business.interfaces.IInventoryActions;
import com.smartkitchen.business.interfaces.IListActions;
import com.smartkitchen.business.InvalidInputException;
import com.smartkitchen.business.implementation.InventoryActions;
import com.smartkitchen.business.implementation.ListActions;
import com.smartkitchen.objects.Item;
import com.smartkitchen.R;
import com.smartkitchen.presentation.ParentActivity;

import java.util.ArrayList;

//Screen for editing an item in the inventory
public class EditInventoryItemActivity extends ParentActivity {

    //Access to relevant business layer methods
    IListActions listActions = new ListActions();
    IInventoryActions inventoryActions = new InventoryActions();
    IGroceryActions groceryActions = new GroceryActions();
    IAllergyActions allergyActions;

    //String used for getting the item passed in to the screen
    public static final String POSITION_KEY = "position";

    //Fields for user input
    private EditText editName, editQuantity, editUnits, editThreshold, editPrice, editCalories;
    private TextView title;
    //Checkboxes for allergy information
    private CheckBox checkLactose, checkGluten, checkNuts, checkFish, checkEggs, checkSoy;
    //Buttons to cancel edit screen and submit changes
    private Button btnCancel, btnSubmit;

    //Create the edit inventory view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_inventory_item);

        //Gets the item that has been selected to be edited
        Intent intent = getIntent();
        int itemPosition = intent.getIntExtra(POSITION_KEY, -1);
        Item item = inventoryActions.getInventoryItem(itemPosition);

        //Sets the text and colour of the taskbar
        setTitle("Edit " + item.getName());
        setColour(ContextCompat.getColor(this, R.color.blueColour3));

        //Initializes the UI elements
        initViews();
        allergyActions = new AllergyActions(checkNuts, checkSoy, checkLactose, checkGluten, checkFish, checkEggs);
        setData(item);

        title.setText("Edit " + item.getName());

        //Creates on click listener, just returns to inventory list screen
        btnCancel.setOnClickListener(v -> finish());

        //Creates on click listener
        btnSubmit.setOnClickListener(v -> {
            //Updates the item information
            try {
                listActions.editValidation(checkData(item));
                updateData(item);
                //Checks if the item needed to be added to grocery list because quantity<threshold
                boolean enteredThreshold = groceryActions.thresholdAddToGrocery(item, EditInventoryItemActivity.this, true);
                //If not, return to the inventory screen as usual
                if(!enteredThreshold){
                    finish();
                }
            } catch (InvalidInputException e) {
                Toast.makeText(EditInventoryItemActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Sets the default information based on the current item information
    private void setData(Item item){
        editName.setText(item.getName());
        editQuantity.setText("" + item.getQuantity());
        editUnits.setText(item.getUnits());
        editThreshold.setText("" + item.getThresholdQuantity());
        editPrice.setText("" + item.getPricePerUnit());
        editCalories.setText("" + item.getCaloriesPerUnit());
        allergyActions.setAllergies(item);
    }

    //Updates the information in the item
    private void updateData(Item item){
        item.setName(editName.getText().toString());
        item.setQuantity(Integer.parseInt(editQuantity.getText().toString()));
        item.setUnits(editUnits.getText().toString());
        if(!editThreshold.getText().toString().equals(""))
            item.setThresholdQuantity(Integer.parseInt(editThreshold.getText().toString()));
        else
            item.setThresholdQuantity(0);
        if(!editPrice.getText().toString().equals(""))
            item.setPricePerUnit(Double.parseDouble(editPrice.getText().toString()));
        else
            item.setPricePerUnit(0);
        if(!editCalories.getText().toString().equals(""))
            item.setCaloriesPerUnit(Integer.parseInt(editCalories.getText().toString()));
        else
            item.setCaloriesPerUnit(0);
        item.setAllergies(allergyActions.getAllergies());
        inventoryActions.updateInventoryItem(item);
    }

    //Grabs the info from the text field and stores in an Item object
    private Item checkData(Item item) {
        //Temporary parameter until edit button is created
        String checkName = editName.getText().toString();
        int checkQuantity = -1;
        if(!editQuantity.getText().toString().equals(""))
            checkQuantity = Integer.parseInt(editQuantity.getText().toString());
        String checkUnit = editUnits.getText().toString();
        int checkThreshold = 0;
        if(!editThreshold.getText().toString().equals(""))
            checkThreshold = Integer.parseInt(editThreshold.getText().toString());
        double checkPrice = 0;
        if(!editPrice.getText().toString().equals(""))
            checkPrice = Double.parseDouble(editPrice.getText().toString());
        int checkCalories = 0;
        if(!editCalories.getText().toString().equals(""))
            checkCalories = Integer.parseInt(editCalories.getText().toString());
        return new Item(checkName, checkQuantity, checkUnit,
                item.getQuantityToBuy(), checkThreshold,
                item.getAllergies(), checkCalories, checkPrice);
    }

    //Initializes the views
    private void initViews(){
        editName = findViewById(R.id.editInvItemName);
        editQuantity = findViewById(R.id.editInvQuantity);
        editUnits = findViewById(R.id.editInvUnits);
        editThreshold = findViewById(R.id.editInvThreshold);
        editPrice = findViewById(R.id.editInvPrice);
        editCalories = findViewById(R.id.editInvCalories);

        title = findViewById(R.id.txtEditInvTitle);

        btnCancel = findViewById(R.id.btnInvEditCancel);
        btnSubmit = findViewById(R.id.btnEditInvSubmit);

        checkEggs = findViewById(R.id.checkInvEggs);
        checkFish = findViewById(R.id.checkInvFish);
        checkGluten = findViewById(R.id.checkInvGluten);
        checkLactose = findViewById(R.id.checkInvLactose);
        checkSoy = findViewById(R.id.checkInvSoy);
        checkNuts = findViewById(R.id.checkInvNuts);
    }
}