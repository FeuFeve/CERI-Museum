package com.example.cerimuseum.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cerimuseum.R;
import com.example.cerimuseum.model.DataManager;
import com.example.cerimuseum.model.MuseumObject;

import java.util.List;

public class MuseumObjectActivity extends AppCompatActivity {

    MuseumObject museumObject;

    private ImageView ivThumbnail;

    private TextView tvName;
    private TextView tvBrand;
    private TextView tvIsWorking;
    private TextView tvTimeFrame;
    private TextView tvYear;

    private TextView tvDescription;
    private TextView tvCategories;
    private TextView tvTechnicalDetails;

    private LinearLayout technicalDetailsLayout;


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
        ivThumbnail = findViewById(R.id.thumbnail);

        tvName = findViewById(R.id.name);
        tvBrand = findViewById(R.id.brand);
        tvIsWorking = findViewById(R.id.isWorking);
        tvTimeFrame = findViewById(R.id.timeFrame);
        tvYear = findViewById(R.id.year);

        tvDescription = findViewById(R.id.description);
        tvCategories = findViewById(R.id.categories);
        tvTechnicalDetails = findViewById(R.id.technicalDetails);

        technicalDetailsLayout = findViewById(R.id.technicalDetailsLayout);
    }

    private void updateView() {
        // Thumbnail
        ivThumbnail.setImageBitmap(museumObject.getThumbnail());

        // Name
        tvName.setText(museumObject.getName());

        // Brand (if)
        if (museumObject.getBrand().isEmpty()) {
            tvBrand.setText("Brand: none/unknown");
        }
        else {
            tvBrand.setText("Brand: " + museumObject.getBrand());
        }

        // Is working (if)
        if (museumObject.getWorking() == -1) {
            tvIsWorking.setHeight(0);
        }
        else if (museumObject.getWorking() == 0) {
            tvIsWorking.setText("Is working: false");
        }
        else {
            tvIsWorking.setText("Is working: true");
        }

        // Time frame
        String tf = String.valueOf(museumObject.getTimeFrame().get(0));
        if (museumObject.getTimeFrame().size() > 1) {
            tf += " - " + museumObject.getTimeFrame().get(museumObject.getTimeFrame().size() - 1);
        }
        tvTimeFrame.setText("Time frame:\n" + tf);

        // Year of acquisition (if)
        if (museumObject.getYear() != 0) {
            tvYear.setText("Year of acquisition:\n" + museumObject.getYear());
        }
        else {
            tvYear.setText("Year of acquisition:\nunknown");
        }

        // Description
        tvDescription.setText(museumObject.getDescription());

        // Categories
        String cat = "";
        List<String> categories = museumObject.getCategories();
        if (categories != null && !categories.isEmpty()) {
            cat = "- " + categories.get(0);
            for (int i = 1; i < categories.size(); i++) {
                cat += "\n- " + categories.get(i);
            }
        }
        tvCategories.setText(cat);

        // Technical details (if)
        String td = "";
        List<String> technicalDetails = museumObject.getTechnicalDetails();
        if (technicalDetails != null && !technicalDetails.isEmpty()) {
            td = "- " + technicalDetails.get(0);
            for (int i = 1; i < technicalDetails.size(); i++) {
                td += "\n- " + technicalDetails.get(i);
            }
        }
        if (td.isEmpty()) {
            technicalDetailsLayout.setVisibility(View.GONE);
        }
        else {
            tvTechnicalDetails.setText(td);
        }
    }
}
