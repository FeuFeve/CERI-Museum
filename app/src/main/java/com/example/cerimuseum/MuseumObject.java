package com.example.cerimuseum;

import android.util.Log;
import android.util.Pair;

import java.util.List;

class MuseumObject {

    private final String TAG = "MuseumObject";

    String id;

    String name;
    String brand;
    String description;
    int year;
    int working;

    List<Integer> timeFrame;
    List<String> technicalDetails;
    List<String> categories;
    List<Pair<String, String>> pictures;


    MuseumObject(String id, String name, String description, List<String> categories, List<Integer> timeFrame) {
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
}
