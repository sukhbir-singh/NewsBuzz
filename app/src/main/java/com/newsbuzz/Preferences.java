package com.newsbuzz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
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

    private static final String KEY_USER_ID1 = "national";
    private static final String KEY_USER_ID2 = "international";
    private static final String KEY_USER_ID3 = "sports";
    private static final String KEY_USER_ID4 = "entertainment";
    private static final String KEY_USER_ID5 = "technology";
    private static final String KEY_USER_ID6 = "local";

    private static final String PREF_NAME = "newsbuzz";
    private boolean is_national=false,is_international=false,is_sports=false,is_entertainment=false,is_technology=false,is_local=false;
    private CheckBox national,international,sports,entertainment,technology,local;

    private Button next_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        pref = getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();

        national= (CheckBox) findViewById(R.id.checkBox1);
        international= (CheckBox) findViewById(R.id.checkBox2);
        sports= (CheckBox) findViewById(R.id.checkBox3);
        entertainment= (CheckBox) findViewById(R.id.checkBox4);
        technology= (CheckBox) findViewById(R.id.checkBox5);
        local= (CheckBox) findViewById(R.id.checkBox6);

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

        national.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                is_national = isChecked;
            }
        });
        international.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                is_international=isChecked;
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
        local.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                is_local = isChecked;
            }
        });
    }

    public void saveAllPreferences(){
        Log.v("saved","saved");
     editor.putBoolean(KEY_USER_ID1, is_national);
     editor.putBoolean(KEY_USER_ID2,is_international);
     editor.putBoolean(KEY_USER_ID3,is_sports);
     editor.putBoolean(KEY_USER_ID4,is_entertainment);
     editor.putBoolean(KEY_USER_ID5,is_technology);
     editor.putBoolean(KEY_USER_ID6,is_local);

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