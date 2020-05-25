package com.example.cerimuseum.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cerimuseum.R;
import com.example.cerimuseum.model.DataManager;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class CategoriesFragment extends Fragment {

    View view;
    private RecyclerView recyclerView;
    SectionedRecyclerViewAdapter sectionAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_categories, container, false);

        recyclerView = view.findViewById(R.id.categoriesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        sectionAdapter = new SectionedRecyclerViewAdapter();
        recyclerView.setAdapter(sectionAdapter);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    void refreshSectionAdapter() {
        sectionAdapter = new SectionedRecyclerViewAdapter();

        // Sections
        System.out.println("NUMBER OF CATEGORIES: " + DataManager.categories.size());
        for (String category : DataManager.categories.keySet()) {
            sectionAdapter.addSection(new Category(getContext(), category, DataManager.categories.get(category)));
            System.out.println(category + ": " + DataManager.categories.get(category).size() + " objects");
        }
        sectionAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(sectionAdapter);
    }
}
