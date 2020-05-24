package com.example.cerimuseum.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.example.cerimuseum.parser.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WebService {

    private static String HOST = "demo-lia.univ-avignon.fr";
    private static String PATH_1 = "cerimuseum";

    private static String CATALOG = "catalog";
    private static String ITEMS = "items";
    private static String THUMBNAIL = "thumbnail";
    private static String IMAGES = "images";


    private static Uri.Builder commonBuilder() {
        Uri.Builder builder = new Uri.Builder();

        builder.scheme("https")
                .authority(HOST)
                .appendPath(PATH_1);
        return builder;
    }

    public static URL buildSearchCatalog() throws MalformedURLException {
        Uri.Builder builder = commonBuilder();
        builder.appendPath(CATALOG);
        return new URL(builder.build().toString());
    }

    public static URL buildSearchThumbnail(String objectID) throws MalformedURLException {
        Uri.Builder builder = commonBuilder();
        builder.appendPath(ITEMS)
                .appendPath(objectID)
                .appendPath(THUMBNAIL);
        return new URL(builder.build().toString());
    }

    public static URL buildSearchPicture(String objectID, String pictureID) throws MalformedURLException {
        Uri.Builder builder = commonBuilder();
        builder.appendPath(ITEMS)
                .appendPath(objectID)
                .appendPath(IMAGES)
                .appendPath(pictureID);
        return new URL(builder.build().toString());
    }

    public static void sendRequestAndUpdate(URL url) throws IOException {
        // Send the request
        final HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        // Get the response
        InputStream response = urlConnection.getInputStream();

        // Read the response and update the team information
        JsonParser.parseFile(response);
    }
}
