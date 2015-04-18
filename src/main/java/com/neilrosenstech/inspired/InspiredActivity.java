package com.neilrosenstech.inspired;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.neilrosenstech.inspired.helpers.InspiredActivityHelper;
import com.neilrosenstech.inspired.lib.InspiredConfiguration;
import com.neilrosenstech.inspired.lib.RemoteDataFetcher;
import com.neilrosenstech.inspired.models.DailyPackage;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InspiredActivity extends Activity {
    public ImageView backgroundPreview;
    public TextView titleView;
    public TextView dateView;
    public DailyPackage currentPack;
    public Typeface lato;

    public ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspired);

        dialog = new ProgressDialog(this);

        initializeViewsAndTypeface();
        fetchAndFeatureDailyVideo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_inspired);

        initializeViewsAndTypeface();
        fetchAndFeatureDailyVideo();
    }

    @Override
    protected void onPause() {
        super.onPause();
        InspiredActivityHelper.dismissDialog(dialog);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Intent videoIntent = null;
        try {
            String youtubeDataApiKey = InspiredConfiguration.fromAssets(getAssets()).getString("youtubeDataApiKey");
            videoIntent = YouTubeStandalonePlayer.createVideoIntent(this, youtubeDataApiKey, currentPack.videoId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        startActivity(videoIntent);
        return true;
    }

    private void fetchAndFeatureDailyVideo() {
        String inspiredFormatDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        new RemoteDataFetcher(this).execute("http://inspired-app.herokuapp.com/" + inspiredFormatDate +  ".js");
    }

    private void initializeViewsAndTypeface() {
        titleView = (TextView) findViewById(R.id.title);
        dateView = (TextView) findViewById(R.id.date);
        backgroundPreview = (ImageView) findViewById(R.id.backgroundPreview);
        lato = Typeface.createFromAsset(getAssets(), "fonts/Lato100.ttf");
    }
}
