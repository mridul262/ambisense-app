package com.ambisense.ambisense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences appSharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        appSharedPreference = getSharedPreferences("AppSharedPreference", Context.MODE_PRIVATE);
        init();
    }

    protected void init(){
        SharedPreferences.Editor editor = appSharedPreference.edit();

        // Identify Sounds setting
        SwitchCompat identifySoundSwitch = findViewById(R.id.settings_identifySounds);

        identifySoundSwitch.setChecked(appSharedPreference.getBoolean("identifySoundsSetting", true));
        identifySoundSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editorNew = appSharedPreference.edit();

            editorNew.putBoolean("identifySoundsSetting", isChecked);
            TextView identifySoundsSettingTextView = findViewById(R.id.settings_identifySounds_description);
            Intent recordingIntent = new Intent(this, StartRecording.class);
            if (isChecked) {
                identifySoundsSettingTextView.setText(R.string.soundSettings_record_summaryOn);
                startService(recordingIntent);
            } else {
                identifySoundsSettingTextView.setText(R.string.soundSettings_record_summaryOff);
                stopService(recordingIntent);
            }
            editorNew.apply();
        });
        editor.apply();
    }
}
