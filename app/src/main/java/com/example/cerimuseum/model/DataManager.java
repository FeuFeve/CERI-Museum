package com.example.cerimuseum.model;

import java.util.ArrayList;
import java.util.List;

public class DataManager {

    public static List<MuseumObject> museumObjects = new ArrayList<>();


    static void print() {
        for (MuseumObject museumObject : museumObjects) {
            museumObject.print();
        }
    }
}
