package com.example.cerimuseum.parser;

import android.util.JsonReader;
import android.util.Pair;

import com.example.cerimuseum.model.DataManager;
import com.example.cerimuseum.model.MuseumObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {


    public static void parseFile(InputStream response) throws IOException {

        JsonReader reader = new JsonReader(new InputStreamReader(response, "UTF-8"));

        reader.beginObject();
        while (reader.hasNext()) {
            readMuseumObject(reader);
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
        List<Pair<String, String>> pictures = new ArrayList<>();

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
                        pictures.add(new Pair<>(reader.nextName(), reader.nextString()));
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
        museumObject.setPictures(pictures);

        DataManager.museumObjects.add(museumObject);
    }
}
