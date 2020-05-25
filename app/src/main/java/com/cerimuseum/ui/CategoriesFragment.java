package com.cerimuseum.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cerimuseum.R;
import com.cerimuseum.model.DataManager;
import com.cerimuseum.model.MuseumObject;

import java.util.List;
import java.util.Map;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class CategoriesFragment extends Fragment {

    private RecyclerView recyclerView;
    private SectionedRecyclerViewAdapter sectionAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

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
        for(Map.Entry<String, List<MuseumObject>> entry : DataManager.categories.entrySet()) {
            sectionAdapter.addSection(new Category(getContext(), entry.getKey(), entry.getValue()));
        }

        sectionAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(sectionAdapter);
    }
}
