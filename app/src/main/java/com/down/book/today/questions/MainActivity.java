package com.down.book.today.questions;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }


    public void start(View view) {
        MediaPlayer media1 = MediaPlayer.create(this,R.raw.sound_click);
        media1.start();

        Intent windows_asila = new Intent(this, windows_asila.class);
        windows_asila.putExtra("rtn",false);
        startActivity(windows_asila);
    }

    public void returen(View view) {
        MediaPlayer media2 = MediaPlayer.create(this,R.raw.sound_click);
        media2.start();

        Intent windows_asila = new Intent(this, windows_asila.class);
        windows_asila.putExtra("rtn",true);
        startActivity(windows_asila);
    }

    public void addpoint(View view) {
        MediaPlayer media3 = MediaPlayer.create(this,R.raw.sound_click);
        media3.start();

        Intent addPoin = new Intent(this, addPoint.class);
        startActivity(addPoin);
    }

    public void Share(View view) {
        MediaPlayer media4 = MediaPlayer.create(this,R.raw.sound_click);
        media4.start();

        Intent myintent = new Intent(Intent.ACTION_SEND);
        myintent.setType("text/plain");
        String body = "تطبيق اسئله  \n" + "\n" +
                "http://play.google.com/store/apps/details?id=" + getPackageName();
        String sub = "تطبيق نسالك وانت تجيب \n";
        myintent.putExtra(Intent.EXTRA_SUBJECT, sub);
        myintent.putExtra(Intent.EXTRA_TEXT, body);
        startActivity(Intent.createChooser(myintent, "مشاركة البرنامج"));
    }

}
