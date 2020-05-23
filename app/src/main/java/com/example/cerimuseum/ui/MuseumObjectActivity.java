package com.example.cerimuseum.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cerimuseum.R;
import com.example.cerimuseum.model.DataManager;
import com.example.cerimuseum.model.MuseumObject;

public class MuseumObjectActivity extends AppCompatActivity {

    MuseumObject museumObject;

    private TextView tvName;
    private TextView tvBrand;
    private TextView tvDescription;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_museum_object);

        // Get intent
        Intent intent = getIntent();
        museumObject = DataManager.museumObjects.get(intent.getExtras().getInt(MuseumObject.TAG));

        getLayoutElements();

        updateView();
    }

    private void getLayoutElements() {
        tvName = findViewById(R.id.name);
        tvBrand = findViewById(R.id.brand);
        tvDescription = findViewById(R.id.description);
    }

    private void updateView() {
        tvName.setText(museumObject.getName());
        tvBrand.setText(museumObject.getBrand());
        tvDescription.setText(museumObject.getDescription());
    }
}
