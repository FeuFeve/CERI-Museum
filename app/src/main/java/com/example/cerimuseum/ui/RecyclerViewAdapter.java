package com.example.cerimuseum.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cerimuseum.R;
import com.example.cerimuseum.model.DataManager;
import com.example.cerimuseum.model.MuseumObject;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder> {

    private Context context;


    RecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        MuseumObject museumObject = DataManager.museumObjects.get(position);

        // Fill the list item content
        holder.name.setText(museumObject.getName());
        holder.brand.setText(museumObject.getBrand());
        if (museumObject.getBrand() == null || museumObject.getBrand().isEmpty()) {
            holder.brand.setHeight(0);
        }

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
        return DataManager.museumObjects.size();
    }


    static class CustomViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout container;
        private ImageView thumbnail;
        private TextView name;
        private TextView brand;
        private TextView categories;


        CustomViewHolder(View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.container);
            thumbnail = itemView.findViewById(R.id.objectImage);
            name = itemView.findViewById(R.id.objectName);
            brand = itemView.findViewById(R.id.objectBrand);
            categories = itemView.findViewById(R.id.objectCategories);
        }
    }
}
