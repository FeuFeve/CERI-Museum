package com.example.cerimuseum.parser;

import android.graphics.Bitmap;
import android.util.JsonReader;
import android.util.Pair;

import com.example.cerimuseum.model.DataManager;
import com.example.cerimuseum.model.MuseumObject;
import com.example.cerimuseum.net.DownloadImageTask;
import com.example.cerimuseum.net.WebService;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {


    public static void parseMuseumObjects(InputStream response) throws IOException {
        // Get the museum objects
        JsonReader reader = new JsonReader(new InputStreamReader(response, "UTF-8"));

        reader.beginObject();
        while (reader.hasNext()) {
            readMuseumObject(reader);
        }
        reader.endObject();
        DataManager.printCategories();
    }

    public static void parseDemos(InputStream response) throws IOException {
        // Get the museum objects
        JsonReader reader = new JsonReader(new InputStreamReader(response, "UTF-8"));

        String id;
        String time;

        reader.beginObject();
        while (reader.hasNext()) {
            id = reader.nextName();
            time = reader.nextString();

            for (MuseumObject museumObject : DataManager.museumObjects) {
                if (museumObject.getId().equals(id)) {
                    if (museumObject.getNextDemos() == null) {
                        museumObject.setNextDemos(new ArrayList<String>());
                    }
                    museumObject.getNextDemos().add(time);
                    break;
                }
            }
        }
        reader.endObject();
    }

    private static void readMuseumObject(JsonReader reader) throws IOException {
        String id = reader.nextName();

        String name = "";
        String brand = "";
        String description = "";
        int year = 0;
        int working = -1;

        List<Integer> timeFrame = new ArrayList<>();
        List<String> technicalDetails = new ArrayList<>();
        List<String> categories = new ArrayList<>();
        List<Pair<String, String>> pictureIDs = new ArrayList<>();

        List<Bitmap> pictures = new ArrayList<>();

        reader.beginObject();
        while (reader.hasNext()) {
            String key = reader.nextName();

            switch (key) {
                case "name":
                    name = reader.nextString();
                    break;
                case "brand":
                    brand = reader.nextString();
                    break;
                case "description":
                    description = reader.nextString();
                    break;
                case "year":
                    year = reader.nextInt();
                    break;
                case "working":
                    boolean isWorking = reader.nextBoolean();
                    if (isWorking)
                        working = 1;
                    else
                        working = 0;
                    break;
                case "timeFrame":
                case "technicalDetails":
                case "categories":
                    reader.beginArray();
                    while (reader.hasNext()) {
                        switch (key) {
                            case "timeFrame":
                                timeFrame.add(reader.nextInt());
                                break;
                            case "technicalDetails":
                                technicalDetails.add(reader.nextString());
                                break;
                            case "categories":
                                categories.add(reader.nextString());
                                break;
                            default:
                                reader.skipValue();
                                break;
                        }
                    }
                    reader.endArray();
                    break;
                case "pictures":
                    reader.beginObject();
                    while (reader.hasNext()) {
                        pictureIDs.add(new Pair<>(reader.nextName(), reader.nextString()));
                    }
                    reader.endObject();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();

        MuseumObject museumObject = new MuseumObject(id, name, description, categories, timeFrame);
        if (!brand.isEmpty()) {
            museumObject.setBrand(brand);
        }
        if (year != 0) {
            museumObject.setYear(year);
        }
        museumObject.setWorking(working);
        museumObject.setTechnicalDetails(technicalDetails);
        museumObject.setPictureIDs(pictureIDs);

        // Download the thumbnail of the object
        new DownloadImageTask(museumObject, 1000).execute(WebService.buildSearchThumbnail(id).toString());

        // Prepare the pictures list
        for (int i = 0; i < pictureIDs.size(); i++) {
            pictures.add(null);
        }
        museumObject.setPictures(pictures);

        DataManager.museumObjects.add(museumObject);
        DataManager.addToCorrespondingCategories(museumObject);
    }
}
