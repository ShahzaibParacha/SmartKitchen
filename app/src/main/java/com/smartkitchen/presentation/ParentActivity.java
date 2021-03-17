package com.smartkitchen.presentation;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.smartkitchen.R;

public class ParentActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch(item.getItemId()){
            case R.id.menuCurrentInventory:
                intent = new Intent(ParentActivity.this, CurrentInventoryActivity.class);
                startActivity(intent);
                return true;
            case R.id.menuGroceryList:
                intent = new Intent(ParentActivity.this, GroceryListActivity.class);
                startActivity(intent);
                return true;
//            To be Implemented in Iteration 3
//            case R.id.menuRecipes:
//                Toast.makeText(this, "Recipe Feature Coming Soon!", Toast.LENGTH_SHORT).show();
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
