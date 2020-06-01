package com.ambisense.ambisense;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialiseSharedPreferences();
        createNotificationChannel();
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

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        CharSequence name = getString(R.string.channel_name);
        String description = getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(getString(R.string.channel_name), name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        if(notificationManager != null){
            notificationManager.createNotificationChannel(channel);
        }
    }
}
