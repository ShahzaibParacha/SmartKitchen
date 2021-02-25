package com.smartkitchen.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.smartkitchen.objects.Item;
import com.smartkitchen.R;
import com.smartkitchen.persistence.DBManager;

public class EditGroceryListItemActivity extends AppCompatActivity {

    public static final String POSITION_KEY = "position";

    private EditText editName, editQuantity, editUnits;
    private Button btnCancel, btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_grocery_list_item);

        Intent intent = getIntent();
        int itemPosition = intent.getIntExtra(POSITION_KEY, -1);
        Item item = DBManager.getGroceryDB().getGroceryList().get(itemPosition);
        initViews();
        setData(item);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditGroceryListItemActivity.this, GroceryListActivity.class);
                startActivity(intent);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData(item);
                Intent intent = new Intent(EditGroceryListItemActivity.this, GroceryListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setData(Item item){
        editName.setText(item.getName());
        editQuantity.setText("" + item.getQuantityToBuy());
        editUnits.setText(item.getUnits());
    }

    private void updateData(Item item){
        item.setName(editName.getText().toString());
        item.setQuantityToBuy(Integer.parseInt(editQuantity.getText().toString()));
        item.setUnits(editUnits.getText().toString());
    }

    private void initViews(){
        editName = findViewById(R.id.editGroceryItemName);
        editQuantity = findViewById(R.id.editGroceryQuantity);
        editUnits = findViewById(R.id.editGroceryUnits);

        btnCancel = findViewById(R.id.btnGroceryEditCancel);
        btnSubmit = findViewById(R.id.btnEditGrocerySubmit);
    }
}