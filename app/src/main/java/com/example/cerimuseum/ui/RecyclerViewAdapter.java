package com.example.cerimuseum.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cerimuseum.R;
import com.example.cerimuseum.model.DataManager;
import com.example.cerimuseum.model.MuseumObject;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CustomItemHolder> implements Filterable {

    private Context context;


    RecyclerViewAdapter(Context context) {
        this.context = context;
        DataManager.filteredMuseumObjects = DataManager.museumObjects;
    }

    @NonNull
    @Override
    public CustomItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        final CustomItemHolder viewHolder = new CustomItemHolder(view);

        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MuseumObjectActivity.class);
                intent.putExtra(MuseumObject.TAG, viewHolder.getAdapterPosition());
                context.startActivity(intent);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomItemHolder holder, int position) {
        MuseumObject museumObject = DataManager.filteredMuseumObjects.get(position);

        // Thumbnail
        holder.thumbnail.setImageBitmap(museumObject.getThumbnail());

        // Name
        holder.name.setText(museumObject.getName());

        // Brand
        holder.brand.setText(museumObject.getBrand());
        if (holder.brand.getText().equals(""))
            holder.brand.setHeight(0);
        else
            holder.brand.setMinHeight(40);

        // Time frame
        String tf = String.valueOf(museumObject.getTimeFrame().get(0));
        if (museumObject.getTimeFrame().size() > 1) {
            tf += " - " + museumObject.getTimeFrame().get(museumObject.getTimeFrame().size() - 1);
        }
        holder.timeFrame.setText(tf);

        // Categories
        String cat = "";
        List<String> categories = museumObject.getCategories();
        if (categories != null && !categories.isEmpty()) {
            cat = categories.get(0);
            for (int i = 1; i < categories.size(); i++) {
                cat += "\n" + categories.get(i);
            }
        }
        holder.categories.setText(cat);

        // Colors
        int color = 0;
        switch (position % 2) {
            case 0: color = R.color.white;
                break;
            case 1: color = R.color.lightGray;
                break;
        }
        holder.container.setBackgroundColor(ContextCompat.getColor(context, color));
    }

    @Override
    public int getItemCount() {
        return DataManager.filteredMuseumObjects.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<MuseumObject> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(DataManager.museumObjects);
            }
            else {
                String filteredPattern = constraint.toString().toLowerCase().trim();

                for (MuseumObject museumObject : DataManager.museumObjects) {
                    // Search in: Name
                    if (museumObject.getName().toLowerCase().contains(filteredPattern)) {
                        filteredList.add(museumObject);
                        continue;
                    }

                    // Search in: Brand
                    if (museumObject.getBrand().toLowerCase().contains(filteredPattern)) {
                        filteredList.add(museumObject);
                        continue;
                    }

                    // Search in: Description
                    if (museumObject.getDescription().toLowerCase().contains(filteredPattern)) {
                        filteredList.add(museumObject);
                        continue;
                    }

                    // Search in: Year (of acquisition)
                    if (String.valueOf(museumObject.getYear()).toLowerCase().contains(filteredPattern)) {
                        filteredList.add(museumObject);
                        continue;
                    }

                    // Search in: Time frame
                    if (!museumObject.getTimeFrame().isEmpty()) {
                        boolean found = false;
                        for (int year : museumObject.getTimeFrame()) {
                            if (String.valueOf(year).toLowerCase().contains(filteredPattern)) {
                                filteredList.add(museumObject);
                                found = true;
                                break;
                            }
                        }
                        if (found) {
                            continue;
                        }
                    }

                    // Search in: Categories
                    if (!museumObject.getCategories().isEmpty()) {
                        for (String category : museumObject.getCategories()) {
                            if (category.toLowerCase().contains(filteredPattern)) {
                                filteredList.add(museumObject);
                                break;
                            }
                        }
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (DataManager.filteredMuseumObjects == DataManager.museumObjects) {
                DataManager.filteredMuseumObjects = new ArrayList<>();
            }
            else {
                DataManager.filteredMuseumObjects.clear();
            }
            DataManager.filteredMuseumObjects.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    static class CustomItemHolder extends RecyclerView.ViewHolder {

        LinearLayout container;
        ImageView thumbnail;
        TextView name;
        TextView brand;
        TextView timeFrame;
        TextView categories;


        CustomItemHolder(View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.container);
            thumbnail = itemView.findViewById(R.id.objectImage);
            name = itemView.findViewById(R.id.objectName);
            brand = itemView.findViewById(R.id.objectBrand);
            timeFrame = itemView.findViewById(R.id.timeFrame);
            categories = itemView.findViewById(R.id.objectCategories);
        }
    }


    static class CustomHeaderHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;

        public CustomHeaderHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.sectionName);
        }
    }
}
