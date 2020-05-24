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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder> implements Filterable {

    private Context context;
    private List<MuseumObject> filteredMuseumObjects;


    RecyclerViewAdapter(Context context) {
        this.context = context;
        filteredMuseumObjects = DataManager.museumObjects;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        final CustomViewHolder viewHolder = new CustomViewHolder(view);



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
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        MuseumObject museumObject = filteredMuseumObjects.get(position);

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

        // Thumbnail
        holder.thumbnail.setImageBitmap(museumObject.getThumbnail());

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
        return filteredMuseumObjects.size();
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
                    if (museumObject.getName().toLowerCase().contains(filteredPattern)) {
                        filteredList.add(museumObject);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (filteredMuseumObjects == DataManager.museumObjects) {
                filteredMuseumObjects = new ArrayList<>();
            }
            else {
                filteredMuseumObjects.clear();
            }
            filteredMuseumObjects.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    static class CustomViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout container;
        private ImageView thumbnail;
        private TextView name;
        private TextView brand;
        private TextView timeFrame;
        private TextView categories;


        CustomViewHolder(View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.container);
            thumbnail = itemView.findViewById(R.id.objectImage);
            name = itemView.findViewById(R.id.objectName);
            brand = itemView.findViewById(R.id.objectBrand);
            timeFrame = itemView.findViewById(R.id.timeFrame);
            categories = itemView.findViewById(R.id.objectCategories);
        }
    }
}
