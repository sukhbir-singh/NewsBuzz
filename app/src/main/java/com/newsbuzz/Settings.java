package com.newsbuzz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;


public class Settings extends AppCompatActivity {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    int PRIVATE_MODE = 0;

    public static final String KEY_USER_ID1 = "entertainment";
    public static final String KEY_USER_ID2 = "technology";
    public static final String KEY_USER_ID3 = "business";
    public static final String KEY_USER_ID4 = "sports";
    public static final String KEY_USER_ID5 = "health";

    public static final String PREF_NAME = "newsbuzz";
    private boolean is_entertainment=false,is_technology=false,is_business=false,is_sports=false,is_health=false;
    private CheckBox business,sports,entertainment,technology,health;

    private Button next_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        pref = getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();

        business= (CheckBox) findViewById(R.id.business_checkbox);
        sports= (CheckBox) findViewById(R.id.sports_checkbox);
        entertainment= (CheckBox) findViewById(R.id.entertainment_checkbox);
        technology= (CheckBox) findViewById(R.id.technology_checkbox);
        health= (CheckBox) findViewById(R.id.health_checkbox);

        is_business=pref.getBoolean(KEY_USER_ID3,true);
        is_sports=pref.getBoolean(KEY_USER_ID4,true);
        is_entertainment=pref.getBoolean(KEY_USER_ID1,true);
        is_technology=pref.getBoolean(KEY_USER_ID2,true);
        is_health=pref.getBoolean(KEY_USER_ID5,true);


        business.setChecked(is_business);
        technology.setChecked(is_technology);
        entertainment.setChecked(is_entertainment);
        sports.setChecked(is_sports);
        health.setChecked(is_health);

        business.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                is_business = isChecked;
                saveAllPreferences();
            }
        });
        sports.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                is_sports=isChecked;
                saveAllPreferences();
            }
        });
        entertainment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                is_entertainment = isChecked;
                saveAllPreferences();
            }
        });
        technology.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                is_technology = isChecked;
                saveAllPreferences();
            }
        });
        health.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                is_health = isChecked;
                saveAllPreferences();
            }
        });
    }

    public void saveAllPreferences(){
        Log.v("saved", "saved");
        editor.putBoolean(KEY_USER_ID1, is_entertainment);
        editor.putBoolean(KEY_USER_ID2, is_technology);
        editor.putBoolean(KEY_USER_ID3, is_business);
        editor.putBoolean(KEY_USER_ID4, is_sports);
        editor.putBoolean(KEY_USER_ID5, is_health);
        editor.commit();
    }

    public void clear() {
        editor.clear();
        editor.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveAllPreferences();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent launch_logout=new Intent(Settings.this,MainActivity.class);
        launch_logout.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        launch_logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        launch_logout.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        startActivity(launch_logout);
        finish();
    }

}