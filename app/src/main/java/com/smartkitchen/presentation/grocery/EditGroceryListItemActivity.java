package com.smartkitchen.presentation.grocery;

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
import com.smartkitchen.business.interfaces.IListActions;
import com.smartkitchen.business.InvalidInputException;
import com.smartkitchen.business.implementation.ListActions;
import com.smartkitchen.objects.Item;
import com.smartkitchen.R;
import com.smartkitchen.presentation.ParentActivity;

//Screen for editing an item in the grocery list
public class EditGroceryListItemActivity extends ParentActivity {

    //Access to relevant business methods
    IListActions listActions = new ListActions();
    IGroceryActions groceryActions = new GroceryActions();
    IAllergyActions allergyActions;

    //String used for getting the item passed in to the screen
    public static final String POSITION_KEY = "position";

    //Fields for user input
    private EditText editName, editQuantity, editUnits, editPrice, editCalories;
    private TextView title;
    //Checkboxes for allergies
    private CheckBox checkLactose, checkFish, checkEggs, checkGluten, checkNuts, checkSoy;
    //Button to cancel edit activity and submit edits
    private Button btnCancel, btnSubmit;

    //Create edit grocery item view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_grocery_list_item);

        //Gets the item that has been selected to be edited
        Intent intent = getIntent();
        int itemPosition = intent.getIntExtra(POSITION_KEY, -1);
        Item item = groceryActions.getGroceryItem(itemPosition);

        //Sets the title and colour of the taskbar
        setTitle("Edit " + item.getName());
        setColour(ContextCompat.getColor(this, R.color.greenColour3));

        //Initializes the UI views
        initViews();
        allergyActions = new AllergyActions(checkNuts, checkSoy, checkLactose, checkGluten, checkFish, checkEggs);
        setData(item);

        title.setText("Edit " + item.getName());

        //Creates on click listener, just return to grocery list screen
        btnCancel.setOnClickListener(v -> finish());

        //Creates on click listener, updates the item information and returns to grocery list screen
        btnSubmit.setOnClickListener(v -> {
            try {
                listActions.editValidation(checkData(item));
                updateData(item);
                finish();
            } catch (InvalidInputException e) {
                Toast.makeText(EditGroceryListItemActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Sets the default field values to the current values in item
    private void setData(Item item) {
        editName.setText(item.getName());
        editQuantity.setText("" + item.getQuantityToBuy());
        editUnits.setText(item.getUnits());
        editPrice.setText("" + item.getPricePerUnit());
        editCalories.setText("" + item.getCaloriesPerUnit());
        allergyActions.setAllergies(item);
    }

    //Updates the information in the item being edited
    private void updateData(Item item) {
        item.setName(editName.getText().toString());
        item.setQuantityToBuy(Integer.parseInt(editQuantity.getText().toString()));
        item.setUnits(editUnits.getText().toString());
        if (!editPrice.getText().toString().equals(""))
            item.setPricePerUnit(Double.parseDouble(editPrice.getText().toString()));
        else
            item.setPricePerUnit(0);
        if (!editCalories.getText().toString().equals(""))
            item.setCaloriesPerUnit(Integer.parseInt(editCalories.getText().toString()));
        else
            item.setCaloriesPerUnit(0);
        item.setAllergies(allergyActions.getAllergies());
        groceryActions.updateGroceryItem(item);
    }

    //Grabs the info from the text field and stores in an Item object
    private Item checkData(Item item) {
        String checkName = editName.getText().toString();
        int checkQuantity = -1;
        if (!editQuantity.getText().toString().equals(""))
            checkQuantity = Integer.parseInt(editQuantity.getText().toString());
        String checkUnit = editUnits.getText().toString();
        double checkPrice = 0;
        if (!editPrice.getText().toString().equals(""))
            checkPrice = Double.parseDouble(editPrice.getText().toString());
        int checkCalories = 0;
        if (!editCalories.getText().toString().equals(""))
            checkCalories = Integer.parseInt(editCalories.getText().toString());
        return new Item(checkName, item.getQuantity(), checkUnit,
                checkQuantity, item.getThresholdQuantity(),
                item.getAllergies(), checkCalories, checkPrice);
    }

    //Initializes the UI elements
    private void initViews() {
        editName = findViewById(R.id.editGroceryItemName);
        editQuantity = findViewById(R.id.editGroceryQuantity);
        editUnits = findViewById(R.id.editGroceryUnits);
        editPrice = findViewById(R.id.editGroceryPrice);
        editCalories = findViewById(R.id.editGroceryCalories);

        title = findViewById(R.id.txtEditGroceryTitle);

        btnCancel = findViewById(R.id.btnGroceryEditCancel);
        btnSubmit = findViewById(R.id.btnEditGrocerySubmit);

        checkEggs = findViewById(R.id.checkGroceryEgg);
        checkFish = findViewById(R.id.checkGroceryFish);
        checkGluten = findViewById(R.id.checkGroceryGluten);
        checkNuts = findViewById(R.id.checkGroceryNuts);
        checkLactose = findViewById(R.id.checkGroceryLactose);
        checkSoy = findViewById(R.id.checkGrocerySoy);
    }
}