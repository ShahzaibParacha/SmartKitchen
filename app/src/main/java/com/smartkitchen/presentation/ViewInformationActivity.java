package com.smartkitchen.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.smartkitchen.R;
import com.smartkitchen.business.IListActions;
import com.smartkitchen.business.ListActions;
import com.smartkitchen.objects.Allergies;
import com.smartkitchen.objects.Item;

import java.util.ArrayList;

public class ViewInformationActivity extends ParentActivity {

    IListActions listActions = new ListActions();
    private TextView title;
    private TextView txtID, txtName, txtQuantity, txtQuantityToBuy, txtUnits, txtThreshold, txtPrice, txtCalories;
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
        if(origin.equals("Inventory"))
            item = listActions.getInventoryItem(itemPosition);
        else if(origin.equals("Grocery"))
            item = listActions.getGroceryItem(itemPosition);

        setTitle("View " + item.getName() + " Information");
        title.setText("View " + item.getName() + " Information");
        setData(item);

        btnBackToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                if(origin.equals("Inventory"))
                    intent = new Intent(ViewInformationActivity.this, CurrentInventoryActivity.class);
                else if(origin.equals("Grocery"))
                    intent = new Intent(ViewInformationActivity.this, GroceryListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setData(Item item){
        txtID.setText("" + item.getId());
        txtName.setText(item.getName());
        txtQuantity.setText("" + item.getQuantity());
        txtQuantityToBuy.setText("" + item.getQuantityToBuy());
        txtUnits.setText(item.getUnits());
        txtThreshold.setText("" + item.getThresholdQuantity());
        txtPrice.setText("" + item.getPricePerUnit());
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
        txtID = findViewById(R.id.idLabel);
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
        checkFish = findViewById(R.id.viewFish);
        checkGluten = findViewById(R.id.viewGluten);
        checkLactose = findViewById(R.id.viewLactose);
        checkSoy = findViewById(R.id.viewSoy);
        checkNuts = findViewById(R.id.viewNuts);
    }
}