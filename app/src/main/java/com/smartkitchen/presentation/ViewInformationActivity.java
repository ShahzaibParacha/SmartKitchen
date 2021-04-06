package com.smartkitchen.presentation;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.smartkitchen.R;
import com.smartkitchen.business.implementation.AllergyActions;
import com.smartkitchen.business.implementation.GroceryActions;
import com.smartkitchen.business.interfaces.IAllergyActions;
import com.smartkitchen.business.interfaces.IGroceryActions;
import com.smartkitchen.business.interfaces.IInventoryActions;
import com.smartkitchen.business.implementation.InventoryActions;
import com.smartkitchen.objects.Item;

//Screen for viewing information of inventory or grocery list items
public class ViewInformationActivity extends ParentActivity {

    //Access to relevant business layer methods
    IInventoryActions inventoryActions = new InventoryActions();
    IGroceryActions groceryActions = new GroceryActions();
    IAllergyActions allergyActions;

    //Views to display item information
    private ConstraintLayout background;
    private CardView cardView;
    private TextView title;
    private TextView txtName, txtQuantity, txtQuantityToBuy, txtUnits, txtThreshold, txtPrice, txtCalories;
    private CheckBox checkEgg, checkFish, checkGluten, checkLactose, checkSoy, checkNuts;
    private Button btnBackToList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_information);

        //Initializes the views
        initViews();
        allergyActions = new AllergyActions(checkNuts, checkSoy, checkLactose, checkGluten, checkFish, checkEgg);

        //Gets the item to be displayed
        Intent intent = getIntent();
        String origin = intent.getStringExtra("Origin");
        int itemPosition = intent.getIntExtra("Position", -1);

        //Gets the item from correct list and sets the relevant colour
        Item item = null;
        if (origin.equals("Inventory")) {
            item = inventoryActions.getInventoryItem(itemPosition);
            setColourBlue();
        } else if (origin.equals("Grocery")) {
            item = groceryActions.getGroceryItem(itemPosition);
            setColourGreen();
        }

        //Sets the title of the taskbar and screen
        setTitle("View " + item.getName() + " Information");
        title.setText("View " + item.getName() + " Information");

        //Sets the data to be displayed
        setData(item);

        //Navigation back to list screen
        btnBackToList.setOnClickListener(v -> supportFinishAfterTransition());
    }

    //Sets the colour scheme to blue when in inventory
    private void setColourBlue() {
        setColour(ContextCompat.getColor(this, R.color.blueColour3));
        btnBackToList.setBackgroundColor(ContextCompat.getColor(this, R.color.blueColour3));
        background.setBackgroundColor(ContextCompat.getColor(this, R.color.blueColour1));
        cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.blueColour2));
    }

    //Sets the colour scheme to green when in grocery
    private void setColourGreen() {
        setColour(ContextCompat.getColor(this, R.color.greenColour3));
        btnBackToList.setBackgroundColor(ContextCompat.getColor(this, R.color.greenColour3));
        background.setBackgroundColor(ContextCompat.getColor(this, R.color.greenColour1));
        cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greenColour2));
    }

    //Sets the data to be displayed
    private void setData(Item item) {
        txtName.setText(item.getName());
        txtQuantity.setText("" + item.getQuantity() + " " + item.getUnits());
        txtQuantityToBuy.setText("" + item.getQuantityToBuy() + " " + item.getUnits());
        txtUnits.setText(item.getUnits());
        txtThreshold.setText("" + item.getThresholdQuantity());
        txtPrice.setText("" + item.getPricePerUnit() + "/" + item.getUnits());
        txtCalories.setText("" + item.getCaloriesPerUnit());
        allergyActions.setAllergies(item);
    }

    //Initializes the views
    private void initViews() {
        background = findViewById(R.id.background);
        cardView = findViewById(R.id.infoCardView);

        txtName = findViewById(R.id.txtViewName);
        txtQuantity = findViewById(R.id.txtViewQuantity);
        txtQuantityToBuy = findViewById(R.id.txtViewQuantityToBuy);
        txtUnits = findViewById(R.id.txtViewUnits);
        txtThreshold = findViewById(R.id.txtViewThreshold);
        txtPrice = findViewById(R.id.txtViewPrice);
        txtCalories = findViewById(R.id.txtViewCalories);

        title = findViewById(R.id.txtViewInfoTitle);

        btnBackToList = findViewById(R.id.btnBackToList);

        checkEgg = findViewById(R.id.viewEggs);
        checkEgg.setEnabled(false);
        checkFish = findViewById(R.id.viewFish);
        checkFish.setEnabled(false);
        checkGluten = findViewById(R.id.viewGluten);
        checkGluten.setEnabled(false);
        checkLactose = findViewById(R.id.viewLactose);
        checkLactose.setEnabled(false);
        checkSoy = findViewById(R.id.viewSoy);
        checkSoy.setEnabled(false);
        checkNuts = findViewById(R.id.viewNuts);
        checkNuts.setEnabled(false);
    }
}