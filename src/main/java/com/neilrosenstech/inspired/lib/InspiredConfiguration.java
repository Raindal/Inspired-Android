package com.neilrosenstech.inspired.lib;

import android.content.res.AssetManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class InspiredConfiguration {
    private static InspiredConfiguration instance = null;
    private JSONObject config = null;

    private InspiredConfiguration() { }

    private static InspiredConfiguration getInstance() {
        if (instance == null) {
            instance = new InspiredConfiguration();
        }
        return instance;
    }

    public static JSONObject fromAssets(AssetManager assetManager) {
        JSONObject config = getInstance().config;
        if (config != null) {
            return config;
        }
        getInstance().config = readConfigFromFile(assetManager);
        return getInstance().config;
    }

    private static JSONObject readConfigFromFile(AssetManager assetManager) {
        try {
            InputStream inputStream = assetManager.open("config/config.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");
            return new JSONObject(json);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
