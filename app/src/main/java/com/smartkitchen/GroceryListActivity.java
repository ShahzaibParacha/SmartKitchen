package com.smartkitchen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GroceryListActivity extends AppCompatActivity {

    //The list view of the grocery list and its adapter
    private RecyclerView groceryListRecView;
    private GroceryListRecViewAdapter adapter;
    private Button btnToCurrentInventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grocery_list_activity);

        //Create the adapter and find the list view
        adapter = new GroceryListRecViewAdapter(this);
        groceryListRecView = findViewById(R.id.groceryListRecView);
        btnToCurrentInventory = findViewById(R.id.btnToCurrentInventory);

        btnToCurrentInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroceryListActivity.this, CurrentInventoryActivity.class);
                startActivity(intent);
            }
        });

        //Set up the list view
        groceryListRecView.setAdapter(adapter);
        groceryListRecView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setItems(ItemLists.getInstance().getGroceryList());

    }
}