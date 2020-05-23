package com.example.cerimuseum.model;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.Pair;
import android.widget.ImageView;

import java.util.List;

public class MuseumObject {

    public static final String TAG = "MuseumObject";

    private String id;

    private String name;
    private String brand;
    private String description;
    private int year;
    private int working;

    private List<Integer> timeFrame;
    private List<String> technicalDetails;
    private List<String> categories;
    private List<Pair<String, String>> pictures;

    private Bitmap thumbnail;


    public MuseumObject(String id, String name, String description, List<String> categories, List<Integer> timeFrame) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categories = categories;
        this.timeFrame = timeFrame;
    }

    void print() {
        Log.d(TAG, "id: " + id);

        Log.d(TAG, "\tname: " + name);
        Log.d(TAG, "\tbrand: " + brand);
        Log.d(TAG, "\tdescription: " + description);
        Log.d(TAG, "\tyear: " + year);
        Log.d(TAG, "\tworking: " + working);

        Log.d(TAG, "\ttimeFrame:");
        for (int i : timeFrame) {
            Log.d(TAG, "\t\t" + i);
        }

        Log.d(TAG, "\ttechnicalDetails:");
        for (String s : technicalDetails) {
            Log.d(TAG, "\t\t" + s);
        }

        Log.d(TAG, "\tcategories:");
        for (String s : categories) {
            Log.d(TAG, "\t\t" + s);
        }

        Log.d(TAG, "\tpictures:");
        for (Pair<String, String> p : pictures) {
            Log.d(TAG, "\t\t" + p.first + ": " + p.second);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getWorking() {
        return working;
    }

    public void setWorking(int working) {
        this.working = working;
    }

    public List<Integer> getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(List<Integer> timeFrame) {
        this.timeFrame = timeFrame;
    }

    public List<String> getTechnicalDetails() {
        return technicalDetails;
    }

    public void setTechnicalDetails(List<String> technicalDetails) {
        this.technicalDetails = technicalDetails;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<Pair<String, String>> getPictures() {
        return pictures;
    }

    public void setPictures(List<Pair<String, String>> pictures) {
        this.pictures = pictures;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }
}
