package com.smartkitchen.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.smartkitchen.business.IListActions;
import com.smartkitchen.business.ListActions;
import com.smartkitchen.business.ListValidation;
import com.smartkitchen.objects.Allergies;
import com.smartkitchen.objects.Item;
import com.smartkitchen.R;
import com.smartkitchen.persistence.DBManager;

import java.util.ArrayList;

public class EditInventoryItemActivity extends ParentActivity {

    IListActions listActions = new ListActions();
    public static final String POSITION_KEY = "position";

    //Fields for user input
    private EditText editName, editQuantity, editUnits, editThreshold, editPrice, editCalories;
    private TextView title;
    private CheckBox checkLactose, checkGluten, checkNuts, checkFish, checkEgg, checkSoy;
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

        setTitle("Edit " + item.getName());

        //Initializes the UI elements
        initViews();
        setData(item);

        title.setText("Edit " + item.getName());

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
                try {
                    listActions.editValidation(checkData(item));
                    updateData(item);
                    //Checks if the item needed to be added to grocery list because quantity<threshold
                    boolean enteredThreshold = listActions.thresholdAddToGrocery(item, EditInventoryItemActivity.this, true);
                    //If not, return to the inventory screen as usual
                    if(!enteredThreshold){
                        Intent intent = new Intent(EditInventoryItemActivity.this, CurrentInventoryActivity.class);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    Toast.makeText(EditInventoryItemActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
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
        setAllergies(item);
    }

    //Updates the information in the item
    private void updateData(Item item){
        item.setName(editName.getText().toString());
        item.setQuantity(Integer.parseInt(editQuantity.getText().toString()));
        item.setUnits(editUnits.getText().toString());
        item.setThresholdQuantity(Integer.parseInt(editThreshold.getText().toString()));
        item.setPricePerUnit(Double.parseDouble(editPrice.getText().toString()));
        item.setCaloriesPerUnit(Integer.parseInt(editCalories.getText().toString()));
        item.setAllergies(getAllergies());
        listActions.updateItem(item);
    }

    //Grabs the info from the text field and stores in an Item object
    private Item checkData(Item item) {
        //Temporary parameter until edit button is created
        String checkName = editName.getText().toString();
        int checkQuantity = -1;
        if(!editQuantity.getText().toString().equals(""))
            checkQuantity = Integer.parseInt(editQuantity.getText().toString());
        String checkUnit = editUnits.getText().toString();
        int checkThreshold = -1;
        if(!editThreshold.getText().toString().equals(""))
            checkThreshold = Integer.parseInt(editThreshold.getText().toString());
        double checkPrice = -1;
        if(!editPrice.getText().toString().equals(""))
            checkPrice = Double.parseDouble(editPrice.getText().toString());
        int checkCalories = -1;
        if(!editCalories.getText().toString().equals(""))
            checkCalories = Integer.parseInt(editCalories.getText().toString());
        Item checkItem = new Item(checkName, checkQuantity, checkUnit,
                item.getQuantityToBuy(), checkThreshold,
                item.getAllergies(), checkCalories, checkPrice);
        return checkItem;
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

        checkEgg = findViewById(R.id.checkInvEggs);
        checkFish = findViewById(R.id.checkInvFish);
        checkGluten = findViewById(R.id.checkInvGluten);
        checkLactose = findViewById(R.id.checkInvLactose);
        checkSoy = findViewById(R.id.checkInvSoy);
        checkNuts = findViewById(R.id.checkInvNuts);
    }
}