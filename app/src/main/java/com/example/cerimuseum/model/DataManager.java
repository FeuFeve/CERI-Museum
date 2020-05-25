package com.example.cerimuseum.model;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class DataManager {

    public static List<MuseumObject> museumObjects = new ArrayList<>();
    public static List<MuseumObject> filteredMuseumObjects;

    public static HashMap<String, List<MuseumObject>> categories = new HashMap<>();


    public static void addToCorrespondingCategories(MuseumObject museumObject) {
        boolean categoryFound;
//        System.out.println("SEARCHING CATEGORIES OF: " + museumObject.getName());

        if (!museumObject.getCategories().isEmpty()) {
            for (String currentCategory : museumObject.getCategories()) {

                categoryFound = false;
                for (String category : categories.keySet()) {
                    if (category.equals(currentCategory)) {
                        categories.get(category).add(museumObject);
//                        System.out.println("CATEGORY FOUND: " + category);
                        categoryFound = true;
                        break;
                    }
                }

                if (!categoryFound) {
                    List<MuseumObject> newList = new ArrayList<>();
                    newList.add(museumObject);
                    categories.put(currentCategory, newList);
//                    System.out.println("NEW CATEGORY FOUND: " + currentCategory);
                }
            }
        }
    }

    public static void printCategories() {
        for (String category : categories.keySet()) {
            System.out.println("CATEGORY: " + category);
            for (MuseumObject museumObject : categories.get(category)) {
                System.out.println("\tName: " + museumObject.getName());
            }
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
