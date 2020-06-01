package com.ambisense.ambisense;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class StartRecording extends Service {
    // Records the phone audio
    private MediaRecorder recorder;
    // The audiofile containing the audio recording
    private File audioFile;
    // The server url to which the audiofile is sent
    final private String postURL =  "http://288b0f001382.ngrok.io";
    private boolean isAudioRecording = false;

    public StartRecording() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            recordingStart();
            Notification startRecordingNotification = notificationHelper();
            startForeground(50074, startRecordingNotification);
            Log.e("Recording Start", "Start Recording");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        try {
            if(isAudioRecording){
                recordingStop();
                Log.e("Recording Stop", "Stop Recording");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();

    }

        @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private Notification notificationHelper(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification serviceNotification = new NotificationCompat.Builder(this, getString(R.string.channel_name))
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Sound Identification is On")
                .setContentText("Sounds are being actively identified")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setOngoing(true).build();

        return serviceNotification;
    }

    private void recordingStart() throws IOException{
            recorder = new MediaRecorder();
            audioFile = new File(this.getFilesDir(), "AudioRecording.aac");
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

            recorder.setOutputFile(audioFile);
            recorder.prepare();
            recorder.start();
            isAudioRecording = true;
            System.out.println("Recording Started");
            Log.e("Recording Start: ", "Started");
    }

    private void recordingStop() throws IOException {
        if(recorder != null){
            recorder.stop();
//            recorder.reset();
            recorder.release();
            isAudioRecording = false;
            System.out.println("Recording Ended");
            Log.e("Recording End: ", "Ended");
        }

        // Stores the audio file in bytes
        byte[] audioByteArray = new byte[(int) audioFile.length()];

        FileInputStream fileInputStream = new FileInputStream(audioFile);
        fileInputStream.read(audioByteArray);
        fileInputStream.close();

        URL url = new URL(postURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        String boundary = Long.toHexString(System.currentTimeMillis());
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        OutputStream output = connection.getOutputStream();
//        output.write(audioByteArray);
        String CRLF = "\r\n";
        String charset = "UTF-8";

        PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);
        writer.append("--").append(boundary).append(CRLF);
        writer.append("Content-Disposition: form-data; name=\"binaryFile\"; filename=\"").append(audioFile.getName()).append("\"").append(CRLF);
        writer.append("Content-Length: ").append(String.valueOf(audioFile.length())).append(CRLF);
        writer.append("Content-Type: ").append(URLConnection.guessContentTypeFromName(audioFile.getName())).append(CRLF);
        writer.append("Content-Transfer-Encoding: binary").append(CRLF);
        writer.append(CRLF).flush();
        output.write(audioByteArray);

        int responseCode = connection.getResponseCode();
        System.out.println(responseCode);
    }
}
