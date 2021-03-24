package com.smartkitchen.application;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import com.smartkitchen.R;
import com.smartkitchen.persistence.DBManager;
import com.smartkitchen.persistence.utils.DBCopier;
import com.smartkitchen.presentation.CurrentInventoryActivity;
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBCopier.copyDatabaseToDevice(MainActivity.this);
        DBManager.initialize();
        Intent intent = new Intent(MainActivity.this, CurrentInventoryActivity.class);
        startActivity(intent);
    }
}