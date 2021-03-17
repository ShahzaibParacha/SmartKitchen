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

import com.smartkitchen.business.GroceryActions;
import com.smartkitchen.business.IGroceryActions;
import com.smartkitchen.business.IListActions;
import com.smartkitchen.business.ListActions;
import com.smartkitchen.objects.Allergies;
import com.smartkitchen.objects.Item;
import com.smartkitchen.R;
import com.smartkitchen.persistence.DBManager;

import java.util.ArrayList;

public class EditGroceryListItemActivity extends ParentActivity {

    IListActions listActions = new ListActions();
    IGroceryActions groceryActions = new GroceryActions();
    public static final String POSITION_KEY = "position";

    //Fields for user input
    private EditText editName, editQuantity, editUnits, editPrice, editCalories;
    private TextView title;
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

        setTitle("Edit " + item.getName());

        //Initializes the UI views
        initViews();
        setData(item);

        title.setText("Edit " + item.getName());

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
        editPrice.setText("" + item.getPricePerUnit());
        editCalories.setText("" + item.getCaloriesPerUnit());
        setAllergies(item);
    }

    //Updates the information in the item being edited
    private void updateData(Item item){
        item.setName(editName.getText().toString());
        item.setQuantityToBuy(Integer.parseInt(editQuantity.getText().toString()));
        item.setUnits(editUnits.getText().toString());
        if(!editPrice.getText().toString().equals(""))
            item.setPricePerUnit(Double.parseDouble(editPrice.getText().toString()));
        else
            item.setPricePerUnit(0);
        if(!editCalories.getText().toString().equals(""))
            item.setCaloriesPerUnit(Integer.parseInt(editCalories.getText().toString()));
        else
            item.setCaloriesPerUnit(0);
        item.setAllergies(getAllergies());
        groceryActions.updateGroceryItem(item);
    }

    //Grabs the info from the text field and stores in an Item object
    private Item checkData(Item item) {
        //Temporary parameter until edit button is created
        String checkName = editName.getText().toString();
        int checkQuantity = -1;
        if(!editQuantity.getText().toString().equals(""))
            checkQuantity = Integer.parseInt(editQuantity.getText().toString());
        String checkUnit = editUnits.getText().toString();
        double checkPrice = 0;
        if(!editPrice.getText().toString().equals(""))
            checkPrice = Double.parseDouble(editPrice.getText().toString());
        int checkCalories = 0;
        if(!editCalories.getText().toString().equals(""))
            checkCalories = Integer.parseInt(editCalories.getText().toString());
        Item checkItem = new Item(checkName, item.getQuantity(), checkUnit,
                checkQuantity, item.getThresholdQuantity(),
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
                checkEggs.setChecked(true);
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
        if(checkEggs.isChecked())
            allergies.add(Allergies.EGGS);
        return allergies;
    }

    //Initializes the UI elements
    private void initViews(){
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