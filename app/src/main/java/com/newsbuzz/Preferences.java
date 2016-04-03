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

public class Preferences extends AppCompatActivity {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    int PRIVATE_MODE = 0;

    public static final String KEY_USER_ID1 = "entertainment";
    public static final String KEY_USER_ID2 = "technology";
    public static final String KEY_USER_ID3 = "business";
    public static final String KEY_USER_ID4 = "sports";
    public static final String KEY_USER_ID5 = "health";
    public static final String KEY_SHOW_AT_START = "show_at_start";

    public static final String PREF_NAME = "newsbuzz";
    private boolean is_entertainment=false,is_technology=false,is_business=false,is_sports=false,is_health=false;
    private CheckBox business,sports,entertainment,technology,health;

    private Button next_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        pref = getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();

        editor.putBoolean(KEY_SHOW_AT_START,true);

        business= (CheckBox) findViewById(R.id.business_checkbox);
        sports= (CheckBox) findViewById(R.id.sports_checkbox);
        entertainment= (CheckBox) findViewById(R.id.entertainment_checkbox);
        technology= (CheckBox) findViewById(R.id.technology_checkbox);
        health= (CheckBox) findViewById(R.id.health_checkbox);

        next_button = (Button) findViewById(R.id.next_button);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Preferences.this,MainActivity.class);
                saveAllPreferences();
                startActivity(i);
                finish();
            }
        });

        business.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                is_business = isChecked;
            }
        });
        sports.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                is_sports=isChecked;
            }
        });
        entertainment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                is_entertainment = isChecked;
            }
        });
        technology.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                is_technology = isChecked;
            }
        });
        health.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                is_health = isChecked;
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

        editor.putBoolean(KEY_SHOW_AT_START,false);
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

}