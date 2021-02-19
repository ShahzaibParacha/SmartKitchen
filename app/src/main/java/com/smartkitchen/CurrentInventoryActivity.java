package com.smartkitchen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class CurrentInventoryActivity extends AppCompatActivity {

    //The list view of the current inventory and its adapter
    private RecyclerView itemsRecView;
    private ItemRecViewAdapter adapter;
    private Button toGroceryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_inventory);

        //Create the adapter and find the list view
        adapter = new ItemRecViewAdapter(this);
        itemsRecView = findViewById(R.id.itemRecView);
        toGroceryList = findViewById(R.id.btnToGroceryList);

        toGroceryList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CurrentInventoryActivity.this, GroceryListActivity.class);
                startActivity(intent);
            }
        });

        //Set up the list view
        itemsRecView.setAdapter(adapter);
        itemsRecView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setItems(ItemLists.getInstance().getInventoryList());
    }
}