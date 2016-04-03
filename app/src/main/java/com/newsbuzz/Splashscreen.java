package com.newsbuzz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class Splashscreen extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 1700;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        SharedPreferences pref = getSharedPreferences(Preferences.PREF_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        final boolean value=pref.getBoolean(Preferences.KEY_SHOW_AT_START,true);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent i=null;
                if(value==true){
                    i= new Intent(Splashscreen.this, Preferences.class);
                }else{
                    i= new Intent(Splashscreen.this, MainActivity.class);
                }

                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}