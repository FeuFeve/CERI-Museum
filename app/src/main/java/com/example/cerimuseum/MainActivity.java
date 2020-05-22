package com.example.cerimuseum;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.example.cerimuseum.ui.main.SectionsPagerAdapter;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        try {
            System.out.println();
            System.out.println("##### SENDING THE REQUEST #####");

            AsyncTask<?, ?, ?> backgroundTask;
            backgroundTask = new AsyncTask<Object, Void, String>() {
                @Override
                protected String doInBackground(Object... params) {

                    try {
                        URL url = WebService.buildSearchCatalog();
                        WebService.sendRequestAndUpdate(url);
                        System.out.println("OBJECTS : " + DataManager.museumObjects.size());
                        DataManager.print();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };
            backgroundTask.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}