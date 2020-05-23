package com.example.cerimuseum.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DataManager {

    public static List<MuseumObject> museumObjects = new ArrayList<>();


    static void print() {
        for (MuseumObject museumObject : museumObjects) {
            museumObject.print();
        }
    }

    public static void sortByNameAZ() {
        Collections.sort(museumObjects, new Comparator<MuseumObject>() {
            @Override
            public int compare(MuseumObject o1, MuseumObject o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }

    public static void sortByNameZA() {
        sortByNameAZ();
        Collections.reverse(museumObjects);
    }

    public static void sortByOldest() {
        Collections.sort(museumObjects, new Comparator<MuseumObject>() {
            @Override
            public int compare(MuseumObject o1, MuseumObject o2) {
                return o1.getTimeFrame().get(0).compareTo(o2.getTimeFrame().get(0));
            }
        });
    }

    public static void sortByNewest() {
        sortByOldest();
        Collections.reverse(museumObjects);
    }
}
