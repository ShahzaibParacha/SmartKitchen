package com.smartkitchen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditInventoryItemActivity extends AppCompatActivity {

    public static final String POSITION_KEY = "position";

    private EditText editName, editQuantity, editUnits;
    private Button btnCancel, btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_inventory_item);

        Intent intent = getIntent();
        int itemPosition = intent.getIntExtra(POSITION_KEY, -1);
        Item item = ItemLists.getInstance().getInventoryList().get(itemPosition);
        initViews();
        setData(item);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditInventoryItemActivity.this, CurrentInventoryActivity.class);
                startActivity(intent);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData(item);
                // get the grocery item and check if current item is already in grocery list
                Item groceryItem = ItemLists.getInstance().getGroceryList().get(itemPosition);
                if (item.thresholdStatus()) {   // check threshold
                    if (item.getName().equals(groceryItem.getName()))
                        groceryItem.setQuantityToBuy(item.getQuantity());
                    else {
                        ItemLists.getInstance().addToGrocery(item);
                    }
                }
                Intent intent = new Intent(EditInventoryItemActivity.this, CurrentInventoryActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setData(Item item){
        editName.setText(item.getName());
        editQuantity.setText("" + item.getQuantity());
        editUnits.setText(item.getUnits());
    }

    private void updateData(Item item){
        item.setName(editName.getText().toString());
        item.setQuantity(Integer.parseInt(editQuantity.getText().toString()));
        item.setUnits(editUnits.getText().toString());
    }

    private void initViews(){
        editName = findViewById(R.id.editInvItemName);
        editQuantity = findViewById(R.id.editInvQuantity);
        editUnits = findViewById(R.id.editInvUnits);

        btnCancel = findViewById(R.id.btnInvEditCancel);
        btnSubmit = findViewById(R.id.btnEditInvSubmit);
    }
}