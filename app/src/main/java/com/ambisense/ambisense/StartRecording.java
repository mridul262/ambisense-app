package com.ambisense.ambisense;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class StartRecording extends Service {
    public StartRecording() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        return super.onStartCommand(intent, flags, startId);
        if(intent != null){
            try {
                recordingHelper();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void recordingHelper() throws IOException {
        MediaRecorder recorder = new MediaRecorder();
            File audioFile = new File("AudioRecording.aac");
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

            recorder.setOutputFile(audioFile);
            try {
                recorder.prepare();
                recorder.start();

                Timer recorderTimer = new Timer();
                recorderTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        recorder.stop();
                        recorder.release();
                    }
                }, 60000 * 15);
            } catch (IOException e) {
                System.out.println("IOException caused in recorder by: " + e.getCause());
                System.out.println("IOException message in recorder: " + e.getMessage());

                Log.e(e.getMessage(), "recording failed");

            }
    }
}
