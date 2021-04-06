package com.smartkitchen.presentation.inventory;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smartkitchen.R;
import com.smartkitchen.business.interfaces.IInventoryActions;
import com.smartkitchen.business.implementation.InventoryActions;
import com.smartkitchen.presentation.ParentActivity;

public class CurrentInventoryActivity extends ParentActivity {

    //The list view of the current inventory and its adapter
    private RecyclerView itemsRecView;
    private ItemRecViewAdapter adapter;
    //Navigation button to add screen
    private FloatingActionButton btnAdd;
    private final IInventoryActions inventoryActions = new InventoryActions();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_inventory);
        setTitle("Current Inventory");
        setColour(ContextCompat.getColor(this, R.color.blueColour3));

        //Create the adapter and find the list view
        adapter = new ItemRecViewAdapter(this);
        itemsRecView = findViewById(R.id.itemRecView);
        btnAdd = findViewById(R.id.btnGoToAddInvActivity);

        //Create on click listener, navigates to add screen
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(CurrentInventoryActivity.this, AddInventoryItemActivity.class);
            startActivity(intent);
        });

        //Set up the list view
        itemsRecView.setAdapter(adapter);
        itemsRecView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setItems(inventoryActions.getInventoryList());
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.setItems(inventoryActions.getInventoryList());
    }
}