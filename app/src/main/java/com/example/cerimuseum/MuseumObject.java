package com.example.cerimuseum;

import android.util.Pair;

import java.util.List;

class MuseumObject {

    String id;

    String name;
    String brand;
    String description;
    int year;
    boolean working;

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
}
