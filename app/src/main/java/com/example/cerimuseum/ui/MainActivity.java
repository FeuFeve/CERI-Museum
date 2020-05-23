package com.example.cerimuseum.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.cerimuseum.R;
import com.example.cerimuseum.model.DataManager;
import com.example.cerimuseum.net.WebService;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    ViewPagerAdapter adapter;
    ListFragment listFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.view_pager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Add fragments
        listFragment = new ListFragment();
        adapter.addFragment(listFragment, "Objects list");
        adapter.addFragment(new CategoriesFragment(), "Categories");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        // Update the DataManager
        new DataManagerTask(this).execute();
    }


    private static class DataManagerTask extends AsyncTask<Void, Void, String> {

        private WeakReference<MainActivity> activityReference;


        DataManagerTask(MainActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                System.out.println("##### SENDING THE REQUEST #####");
                URL url = WebService.buildSearchCatalog();
                WebService.sendRequestAndUpdate(url);
                System.out.println("OBJECTS : " + DataManager.museumObjects.size());
//                DataManager.print();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Task finished";
        }

        @Override
        protected void onPostExecute(String s) {
            MainActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }

            Log.d("MAIN ACTIVITY", "Notify data set changed");
            activity.listFragment.adapter.notifyDataSetChanged();
        }
    }
}