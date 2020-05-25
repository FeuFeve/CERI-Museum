package com.example.cerimuseum.ui;

import android.content.Context;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cerimuseum.R;
import com.example.cerimuseum.model.DataManager;
import com.example.cerimuseum.model.MuseumObject;

import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;

class Category extends Section {

    private Context context;
    private String title;
    private List<MuseumObject> objects;

    Category(Context context, String title, List<MuseumObject> objects) {
        // call constructor with layout resources for this Section header and items
        super(SectionParameters.builder()
                .itemResourceId(R.layout.list_item)
                .headerResourceId(R.layout.section_header)
                .build());

        this.context = context;
        this.title = title;
        this.objects = objects;
    }

    @Override
    public int getContentItemsTotal() {
        return objects.size(); // number of items of this section
    }

    @Override
    public RecyclerViewAdapter.CustomItemHolder getItemViewHolder(View view) {
        // return a custom instance of ViewHolder for the items of this section
        return new RecyclerViewAdapter.CustomItemHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecyclerViewAdapter.CustomItemHolder itemHolder = (RecyclerViewAdapter.CustomItemHolder) holder;

        // bind your view here
        MuseumObject museumObject = objects.get(position);

        // Thumbnail
        itemHolder.thumbnail.setImageBitmap(museumObject.getThumbnail());

        // Name
        itemHolder.name.setText(museumObject.getName());

        // Brand
        itemHolder.brand.setText(museumObject.getBrand());
        if (itemHolder.brand.getText().equals(""))
            itemHolder.brand.setHeight(0);
        else
            itemHolder.brand.setMinHeight(40);

        // Time frame
        String tf = String.valueOf(museumObject.getTimeFrame().get(0));
        if (museumObject.getTimeFrame().size() > 1) {
            tf += " - " + museumObject.getTimeFrame().get(museumObject.getTimeFrame().size() - 1);
        }
        itemHolder.timeFrame.setText(tf);

        // Categories
        String cat = "";
        List<String> categories = museumObject.getCategories();
        if (categories != null && !categories.isEmpty()) {
            cat = categories.get(0);
            for (int i = 1; i < categories.size(); i++) {
                cat += "\n" + categories.get(i);
            }
        }
        itemHolder.categories.setText(cat);

        // Colors
        int color = 0;
        switch (position % 2) {
            case 0: color = R.color.white;
                break;
            case 1: color = R.color.lightGray;
                break;
        }
        itemHolder.container.setBackgroundColor(ContextCompat.getColor(context, color));
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        // return an empty instance of ViewHolder for the headers of this section
        return new RecyclerViewAdapter.CustomHeaderHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        RecyclerViewAdapter.CustomHeaderHolder headerHolder = (RecyclerViewAdapter.CustomHeaderHolder) holder;

        // bind your header view here
        headerHolder.tvTitle.setText(title);
    }
}