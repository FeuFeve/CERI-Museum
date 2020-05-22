package com.example.cerimuseum;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class WebService {

    private static String HOST = "demo-lia.univ-avignon.fr";
    private static String PATH_1 = "cerimuseum";

    private static String CATALOG = "catalog";


    private static Uri.Builder commonBuilder() {
        Uri.Builder builder = new Uri.Builder();

        builder.scheme("https")
                .authority(HOST)
                .appendPath(PATH_1);
        return builder;
    }

    static URL buildSearchCatalog() throws MalformedURLException {
        Uri.Builder builder = commonBuilder();
        builder.appendPath(CATALOG);
        return new URL(builder.build().toString());
    }

    static void sendRequestAndUpdate(URL url) throws IOException {
        // Send the request
        final HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        // Get the response
        InputStream response = urlConnection.getInputStream();

        // Read the response and update the team information
        JsonParser.parseFile(response);
    }
}