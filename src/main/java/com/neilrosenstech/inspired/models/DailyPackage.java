package com.neilrosenstech.inspired.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.neilrosenstech.inspired.lib.exceptions.InspiredServerException;
import com.neilrosenstech.inspired.lib.http.SimpleResponse;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class DailyPackage {
    public String imageUrl;
    public String videoId;
    public String title;
    public Bitmap image;

    public DailyPackage(String imageUrl, String videoId, String title) {
        this.imageUrl = imageUrl;
        this.videoId = videoId;
        this.title = title;
        setImage();
    }

    private void setImage() {
        try {
            image = BitmapFactory.decodeStream((InputStream) new URL(imageUrl).getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DailyPackage createFromRemoteData(String url) throws InspiredServerException {
        try {
            JSONObject result = new JSONObject(getRemote(url));
            return new DailyPackage(result.getString("image_url"), result.getString("video_id"), result.getString("title"));
        } catch (JSONException e) {
            e.printStackTrace();
            return new DailyPackage(null, null, null);
        }
    }

    private static String getRemote(String url) throws InspiredServerException {
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        SimpleResponse response = null;

        try {
            response = new SimpleResponse(client.execute(httpGet));
        } catch (IOException e) {
            e.printStackTrace();
        }

        response.validate();
        return response.body;
    }
}