package com.smartkitchen.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.smartkitchen.objects.Item;
import com.smartkitchen.R;
import com.smartkitchen.persistence.DBManager;

public class AddGroceryItemActivity extends AppCompatActivity {

    private EditText inputName, inputQuantityToBuy, inputUnits;
    private Button btnCancel, btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item_to_grocery_screen);

        initViews();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddGroceryItemActivity.this, GroceryListActivity.class);
                startActivity(intent);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item newItem = initItem();
                DBManager.getGroceryDB().addToGrocery(newItem);
                Intent intent = new Intent(AddGroceryItemActivity.this, GroceryListActivity.class);
                startActivity(intent);
            }
        });
    }

    private Item initItem(){
        String name = inputName.getText().toString();
        int quantityToBuy = Integer.parseInt(inputQuantityToBuy.getText().toString());
        String units = inputUnits.getText().toString();
        return new Item(name, 0, units, quantityToBuy, 0);
    }

    private void initViews(){
        inputName = findViewById(R.id.inputGroceryItemName);
        inputQuantityToBuy = findViewById(R.id.inputGroceryItemQuantity);
        inputUnits = findViewById(R.id.inputGroceryItemUnits);

        btnCancel = findViewById(R.id.btnCancelAddGrocery);
        btnAdd = findViewById(R.id.btnAddGroceryItem);
    }
}
