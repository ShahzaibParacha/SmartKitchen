package com.smartkitchen.application;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import com.smartkitchen.R;
import com.smartkitchen.persistence.utils.DBCopier;
import com.smartkitchen.presentation.inventory.CurrentInventoryActivity;

//The opening class of the app
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Start up the database
        DBCopier.copyDatabaseToDevice(MainActivity.this);

        //Automatically transition to Current Inventory screen
        Intent intent = new Intent(MainActivity.this, CurrentInventoryActivity.class);
        startActivity(intent);
    }
}