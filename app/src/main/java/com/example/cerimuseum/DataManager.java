package com.example.cerimuseum;

import java.util.ArrayList;
import java.util.List;

class DataManager {

    static List<MuseumObject> museumObjects = new ArrayList<>();


    static void print() {
        for (MuseumObject museumObject : museumObjects) {
            museumObject.print();
        }
    }
}
