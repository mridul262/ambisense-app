package com.ambisense.ambisense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialiseSharedPreferences();
    }

    public void onClickSettings(View view){
        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        startActivity(settingsIntent);
    }

    public void initialiseSharedPreferences(){
        SharedPreferences appSharedPreferences = getSharedPreferences("AppSharedPreference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = appSharedPreferences.edit();
        if(!appSharedPreferences.contains("identifySoundsSetting")){
            editor.putBoolean("identifySoundsSetting", false);
        }

        editor.apply();
    }
}
