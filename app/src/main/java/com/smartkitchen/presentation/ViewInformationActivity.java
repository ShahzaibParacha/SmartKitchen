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
import com.smartkitchen.business.GroceryActions;
import com.smartkitchen.business.IGroceryActions;
import com.smartkitchen.business.IInventoryActions;
import com.smartkitchen.business.IListActions;
import com.smartkitchen.business.InventoryActions;
import com.smartkitchen.business.ListActions;
import com.smartkitchen.objects.Allergies;
import com.smartkitchen.objects.Item;

import java.util.ArrayList;

public class ViewInformationActivity extends ParentActivity {

    IInventoryActions inventoryActions = new InventoryActions();
    IGroceryActions groceryActions = new GroceryActions();

    IListActions listActions = new ListActions();
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

        initViews();
        Intent intent = getIntent();
        String origin = intent.getStringExtra("Origin");
        int itemPosition = intent.getIntExtra("Position", -1);
        Item item = null;
        if(origin.equals("Inventory")) {
            item = inventoryActions.getInventoryItem(itemPosition);
            setColourBlue();
        }
        else if(origin.equals("Grocery")) {
            item = groceryActions.getGroceryItem(itemPosition);
            setColourRed();
        }

        setTitle("View " + item.getName() + " Information");
        title.setText("View " + item.getName() + " Information");
        setData(item);

        btnBackToList.setOnClickListener(v -> supportFinishAfterTransition());
    }

    private void setColourBlue(){
        setColour(ContextCompat.getColor(this, R.color.blueColour3));
        btnBackToList.setBackgroundColor(ContextCompat.getColor(this, R.color.blueColour3));
        background.setBackgroundColor(ContextCompat.getColor(this, R.color.blueColour1));
        cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.blueColour2));
    }

    private void setColourRed(){
        setColour(ContextCompat.getColor(this, R.color.greenColour3));
        btnBackToList.setBackgroundColor(ContextCompat.getColor(this, R.color.greenColour3));
        background.setBackgroundColor(ContextCompat.getColor(this, R.color.greenColour1));
        cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.greenColour2));
    }

    private void setData(Item item){
        txtName.setText(item.getName());
        txtQuantity.setText("" + item.getQuantity() + " " + item.getUnits());
        txtQuantityToBuy.setText("" + item.getQuantityToBuy() + " " + item.getUnits());
        txtUnits.setText(item.getUnits());
        txtThreshold.setText("" + item.getThresholdQuantity());
        txtPrice.setText("" + item.getPricePerUnit() + "/" + item.getUnits());
        txtCalories.setText("" + item.getCaloriesPerUnit());
        setAllergies(item);
    }

    private void setAllergies(Item item){
        ArrayList<String> allergies = item.getAllergies();
        if(allergies != null) {
            if (listActions.isInList(allergies, Allergies.NUTS))
                checkNuts.setChecked(true);
            if (listActions.isInList(allergies, Allergies.SOY))
                checkSoy.setChecked(true);
            if (listActions.isInList(allergies, Allergies.LACTOSE))
                checkLactose.setChecked(true);
            if (listActions.isInList(allergies, Allergies.GLUTEN))
                checkGluten.setChecked(true);
            if (listActions.isInList(allergies, Allergies.FISH))
                checkFish.setChecked(true);
            if (listActions.isInList(allergies, Allergies.EGGS))
                checkEgg.setChecked(true);
        }
    }

    //Initializes the views
    private void initViews(){
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