package com.cerimuseum.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.cerimuseum.model.MuseumObject;

import java.io.InputStream;
import java.util.List;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    private ImageView imageView;
    private MuseumObject museumObject;
    private List<Bitmap> pictures;
    private int listIndex;
    private int maxSize;


    private DownloadImageTask(ImageView imageView, int maxSize) {
        this.imageView = imageView;
        this.maxSize = maxSize;
    }

    public DownloadImageTask(MuseumObject museumObject, int maxSize) {
        this.museumObject = museumObject;
        this.maxSize = maxSize;
    }

    public DownloadImageTask(List<Bitmap> pictures, int listIndex, ImageView imageView, int maxSize) {
        this(imageView, maxSize);
        this.pictures = pictures;
        this.listIndex = listIndex;
    }

    protected Bitmap doInBackground(String... urls) {
        String urlDisplay = urls[0];
        Bitmap bitmap = null;
        try {
            InputStream in = new java.net.URL(urlDisplay).openStream();
            bitmap = BitmapFactory.decodeStream(in);

            int width = bitmap.getWidth();
            int height = bitmap.getHeight();

            if (width > maxSize) {
                float bitmapRatio = (float) width / (float) height;
                if (bitmapRatio > 1) {
                    width = maxSize;
                    height = (int) (width / bitmapRatio);
                } else {
                    height = maxSize;
                    width = (int) (height * bitmapRatio);
                }

                return Bitmap.createScaledBitmap(bitmap, width, height, true);
            }
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return bitmap;
    }

    protected void onPostExecute(Bitmap result) {
        if (pictures != null) {
            pictures.set(listIndex, result);
        }
        if (imageView != null) {
            imageView.setImageBitmap(result);
        }
        if (museumObject != null) {
            museumObject.setThumbnail(result);
        }
    }
}
