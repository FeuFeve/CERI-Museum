package com.cerimuseum.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.cerimuseum.R;
import com.cerimuseum.model.DataManager;
import com.cerimuseum.net.WebService;
import com.cerimuseum.parser.JsonParser;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ViewPagerAdapter adapter;
    ListFragment listFragment;
    CategoriesFragment categoriesFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = findViewById(R.id.tabs);
        ViewPager viewPager = findViewById(R.id.view_pager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Add fragments
        listFragment = new ListFragment();
        adapter.addFragment(listFragment, "Objects list");

        categoriesFragment = new CategoriesFragment();
        adapter.addFragment(categoriesFragment, "Categories");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        // Update the DataManager
        new DataManagerTask(this).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.actionSearch);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listFragment.adapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    private static class DataManagerTask extends AsyncTask<Void, Void, String> {

        private WeakReference<MainActivity> activityReference;


        DataManagerTask(MainActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                // Get the museum objects
                System.out.println("##### SENDING THE OBJECTS REQUEST #####");
                URL urlObjects = WebService.buildSearchCatalog();
                InputStream responseObjects = WebService.sendRequestAndUpdate(urlObjects);

                // Read the response and parse the file
                JsonParser.parseMuseumObjects(responseObjects);
                System.out.println("OBJECTS : " + DataManager.museumObjects.size());

                // Get the next demos' time
                System.out.println("##### SENDING THE DEMOS REQUEST #####");
                URL urlDemos = WebService.buildSearchDemos();
                InputStream responseDemos = WebService.sendRequestAndUpdate(urlDemos);

                // Read the response and parse the file
                JsonParser.parseDemos(responseDemos);
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

            // Actualize the RecyclerViews
            activity.listFragment.adapter.notifyDataSetChanged();
            activity.categoriesFragment.refreshSectionAdapter();
        }
    }
}