package com.smartkitchen.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.smartkitchen.objects.Item;
import com.smartkitchen.R;
import com.smartkitchen.persistence.DBManager;

public class AddInventoryItemActivity extends AppCompatActivity {

    private EditText inputName, inputQuantity, inputUnits, inputThreshold;
    private CheckBox enableThreshold;
    private TextView txtThreshold;
    private Button btnCancel, btnAdd;

    private boolean thresholdEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item_screen);

        initViews();
        thresholdEnabled = false;

        enableThreshold.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    txtThreshold.setVisibility(View.VISIBLE);
                    inputThreshold.setVisibility(View.VISIBLE);
                    thresholdEnabled = true;
                }
                else{
                    inputThreshold.setText("" + 0);
                    txtThreshold.setVisibility(View.GONE);
                    inputThreshold.setVisibility(View.GONE);
                    thresholdEnabled = false;
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddInventoryItemActivity.this, CurrentInventoryActivity.class);
                startActivity(intent);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item newItem = initItem();
                DBManager.getInventoryDB().addToInventory(newItem);
                Intent intent = new Intent(AddInventoryItemActivity.this, CurrentInventoryActivity.class);
                startActivity(intent);
            }
        });
    }

    private Item initItem(){
        String name = inputName.getText().toString();
        int quantity = Integer.parseInt(inputQuantity.getText().toString());
        String units = inputUnits.getText().toString();
        int threshold = 0;
        if(thresholdEnabled)
            threshold = Integer.parseInt(inputThreshold.getText().toString());
        return new Item(name, quantity, units, 0, threshold);
    }

    private void initViews(){
        inputName = findViewById(R.id.inputInventoryItemName);
        inputQuantity = findViewById(R.id.inputInventoryItemQuantity);
        inputUnits = findViewById(R.id.inputInventoryItemUnits);
        inputThreshold = findViewById(R.id.inputInventoryItemThreshold);

        enableThreshold = findViewById(R.id.enableThresholdCheckBox);
        txtThreshold = findViewById(R.id.txtThreshold);

        btnCancel = findViewById(R.id.btnCancelAddInventory);
        btnAdd = findViewById(R.id.btnAddInventoryItem);
    }
}
