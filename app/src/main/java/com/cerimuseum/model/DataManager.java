package com.cerimuseum.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DataManager {

    public static List<MuseumObject> museumObjects = new ArrayList<>();
    public static List<MuseumObject> filteredMuseumObjects;

    public static Map<String, List<MuseumObject>> categories = new TreeMap<>();


    public static void addToCorrespondingCategories(MuseumObject museumObject) {
        boolean categoryFound;

        if (!museumObject.getCategories().isEmpty()) {
            for (String currentCategory : museumObject.getCategories()) {

                categoryFound = false;
                for (String category : categories.keySet()) {
                    if (category.equals(currentCategory)) {
                        categories.get(category).add(museumObject);
                        categoryFound = true;
                        break;
                    }
                }

                if (!categoryFound) {
                    List<MuseumObject> newList = new ArrayList<>();
                    newList.add(museumObject);
                    categories.put(currentCategory, newList);
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

    private static void sortByName(List<MuseumObject> list) {
        Collections.sort(list, ((o1, o2) -> o1.getName().compareTo(o2.getName())));
    }

    public static void sortByNameAZ() {
        sortByName(museumObjects);
        sortByName(filteredMuseumObjects);
    }

    public static void sortByNameZA() {
        sortByNameAZ();
        Collections.reverse(museumObjects);
        if (filteredMuseumObjects != museumObjects)
            Collections.reverse(filteredMuseumObjects);
    }

    private static void sortByFirstTimeFrame(List<MuseumObject> list) {
        Collections.sort(list, ((o1, o2) -> o1.getTimeFrame().get(0).compareTo(o2.getTimeFrame().get(0))));
    }

    public static void sortByOldest() {
        sortByFirstTimeFrame(museumObjects);
        sortByFirstTimeFrame(filteredMuseumObjects);
    }

    public static void sortByNewest() {
        sortByOldest();
        Collections.reverse(museumObjects);
        if (filteredMuseumObjects != museumObjects)
            Collections.reverse(filteredMuseumObjects);
    }
}
