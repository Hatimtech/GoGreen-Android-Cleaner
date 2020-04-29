package com.gogreencleaner.main.screens;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.gogreencleaner.main.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * Created by AKASH SHUKLA on 28-May-18.
 */


public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        startActivity();

    }


    private void startActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }, 2500);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
