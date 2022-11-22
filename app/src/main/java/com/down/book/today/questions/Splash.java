package com.down.book.today.questions;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



/**
 * Splash Activity that inflates splash activity xml.
 */
public class Splash extends AppCompatActivity {
    private static final String LOG_TAG = "Splash";

    /**
     * Number of seconds to count down before showing the app open ad. This simulates the time needed
     * to load the app.
     */
    private static final long COUNTER_TIME = 2;

    private long secondsRemaining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);





        ////// Create a timer so the Splash will be displayed for a fixed amount of time.
        createTimer();
    }

    /**
     * Create the countdown timer, which counts down to zero and show the app open ad.
     */
    private void createTimer() {
        final TextView counterTextView = findViewById(R.id.timer);

        CountDownTimer countDownTimer =
                new CountDownTimer(Splash.COUNTER_TIME * 1000, 1000) {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTick(long millisUntilFinished) {
                        secondsRemaining = ((millisUntilFinished / 1000) + 1);
                        counterTextView.setText("جاري تجهيز التطبيق : " + secondsRemaining);
                    }

                    @Override
                    public void onFinish() {
                        secondsRemaining = 0;
                        counterTextView.setText(R.string.Open);

                        Application application = getApplication();

                        // If the application is not an instance of MyApplication, log an error message and
                        // start the Home without showing the app open ad.
                        if (!(application instanceof MyApplication)) {
                            Log.e(LOG_TAG, "Failed to cast application to MyApplication.");
                            startHome();
                            return;
                        }

                        // Show the app open ad.
                        ((MyApplication) application)
                                .showAdIfAvailable(
                                        Splash.this,
                                        () -> startHome());
                    }
                };
        countDownTimer.start();
    }

    public void Home(View view) {
        Intent i = new Intent(Splash.this, MainActivity.class);
        startActivity(i);
    }

    /**
     * Start the Home.
     */
    public void startHome() {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);


    }
}