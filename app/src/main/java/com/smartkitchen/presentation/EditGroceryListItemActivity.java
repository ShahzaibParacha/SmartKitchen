package com.smartkitchen.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.smartkitchen.business.IListActions;
import com.smartkitchen.business.ListActions;
import com.smartkitchen.objects.Item;
import com.smartkitchen.R;
import com.smartkitchen.persistence.DBManager;

public class EditGroceryListItemActivity extends ParentActivity {

    IListActions listActions = new ListActions();
    public static final String POSITION_KEY = "position";

    //Fields for user input
    private EditText editName, editQuantity, editUnits;
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
        Item item = listActions.getGroceryItem(itemPosition);

        setTitle("Edit " + item.getName());

        //Initializes the UI views
        initViews();
        setData(item);

        //Creates on click listener, just return to grocery list screen
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditGroceryListItemActivity.this, GroceryListActivity.class);
                startActivity(intent);
            }
        });

        //Creates on click listener, updates the item information and returns to grocery list screen
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    listActions.editValidation(checkData(item));
                    updateData(item);
                    Intent intent = new Intent(EditGroceryListItemActivity.this, GroceryListActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(EditGroceryListItemActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Sets the default field values to the current values in item
    private void setData(Item item){
        editName.setText(item.getName());
        editQuantity.setText("" + item.getQuantityToBuy());
        editUnits.setText(item.getUnits());
    }

    //Updates the information in the item being edited
    private void updateData(Item item){
        item.setName(editName.getText().toString());
        item.setQuantityToBuy(Integer.parseInt(editQuantity.getText().toString()));
        item.setUnits(editUnits.getText().toString());
    }

    //Grabs the info from the text field and stores in an Item object
    private Item checkData(Item item) {
        //Temporary parameter until edit button is created
        String checkName = editName.getText().toString();
        int checkQuantity = -1;
        if(!editQuantity.getText().toString().equals(""))
            checkQuantity = Integer.parseInt(editQuantity.getText().toString());
        String checkUnit = editUnits.getText().toString();
        Item checkItem = new Item(checkName, checkQuantity, checkUnit,
                item.getQuantityToBuy(), item.getThresholdQuantity(),
                item.getAllergies(), item.getCaloriesPerUnit(), item.getPricePerUnit());
        return checkItem;
    }

    //Initializes the UI elements
    private void initViews(){
        editName = findViewById(R.id.editGroceryItemName);
        editQuantity = findViewById(R.id.editGroceryQuantity);
        editUnits = findViewById(R.id.editGroceryUnits);

        btnCancel = findViewById(R.id.btnGroceryEditCancel);
        btnSubmit = findViewById(R.id.btnEditGrocerySubmit);
    }
}