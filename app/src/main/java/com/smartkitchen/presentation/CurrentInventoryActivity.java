package com.smartkitchen.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smartkitchen.objects.ItemLists;
import com.smartkitchen.R;
import com.smartkitchen.persistence.DBManager;

public class CurrentInventoryActivity extends AppCompatActivity {

    //The list view of the current inventory and its adapter
    private RecyclerView itemsRecView;
    private ItemRecViewAdapter adapter;
    private Button toGroceryList;
    private FloatingActionButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_inventory);

        //Create the adapter and find the list view
        adapter = new ItemRecViewAdapter(this);
        itemsRecView = findViewById(R.id.itemRecView);
        toGroceryList = findViewById(R.id.btnToGroceryList);
        btnAdd = findViewById(R.id.btnGoToAddInvActivity);

        toGroceryList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CurrentInventoryActivity.this, GroceryListActivity.class);
                startActivity(intent);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CurrentInventoryActivity.this, AddInventoryItemActivity.class);
                startActivity(intent);
            }
        });

        //Set up the list view
        itemsRecView.setAdapter(adapter);
        itemsRecView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setItems(DBManager.getInventoryDB().getInventoryList());
    }
}