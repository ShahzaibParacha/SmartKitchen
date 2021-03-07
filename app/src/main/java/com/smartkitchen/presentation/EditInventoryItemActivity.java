package com.smartkitchen.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.smartkitchen.business.IListActions;
import com.smartkitchen.business.ListActions;
import com.smartkitchen.business.ListValidation;
import com.smartkitchen.objects.Item;
import com.smartkitchen.R;
import com.smartkitchen.persistence.DBManager;

public class EditInventoryItemActivity extends AppCompatActivity {

    IListActions listActions = new ListActions();
    public static final String POSITION_KEY = "position";

    //Fields for user input
    private EditText editName, editQuantity, editUnits;
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
        Item item = listActions.getInventoryItem(itemPosition);

        //Initializes the UI elements
        initViews();
        setData(item);

        //Creates on click listener, just returns to inventory list screen
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditInventoryItemActivity.this, CurrentInventoryActivity.class);
                startActivity(intent);
            }
        });

        //Creates on click listener
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Updates the item information
                updateData(item);
                //Checks if the item needed to be added to grocery list because quantity<threshold
                boolean enteredThreshold = listActions.thresholdAddToGrocery(item, EditInventoryItemActivity.this, true);
                //If not, return to the inventory screen as usual
                if(!enteredThreshold){
                    Intent intent = new Intent(EditInventoryItemActivity.this, CurrentInventoryActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    //Sets the default information based on the current item information
    private void setData(Item item){
        editName.setText(item.getName());
        editQuantity.setText("" + item.getQuantity());
        editUnits.setText(item.getUnits());
    }

    //Updates the information in the item
    private void updateData(Item item){
        item.setName(editName.getText().toString());
        item.setQuantity(Integer.parseInt(editQuantity.getText().toString()));
        item.setUnits(editUnits.getText().toString());
    }

    //Initializes the views
    private void initViews(){
        editName = findViewById(R.id.editInvItemName);
        editQuantity = findViewById(R.id.editInvQuantity);
        editUnits = findViewById(R.id.editInvUnits);

        btnCancel = findViewById(R.id.btnInvEditCancel);
        btnSubmit = findViewById(R.id.btnEditInvSubmit);
    }
}