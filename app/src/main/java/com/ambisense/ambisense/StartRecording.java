package com.ambisense.ambisense;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.Timer;
import java.util.TimerTask;

public class StartRecording extends Service {
    // Name of the audio file
    private String fileName;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
//        if(intent != null){
//            try {
//                recordingStart();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        try {
            if(isAudioRecording){
                recordingStop();
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


    private void recordingStart() throws IOException{
            recorder = new MediaRecorder();
            audioFile = new File("AudioRecording.aac");
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

//    private void recordingHelper() throws IOException {
//        MediaRecorder recorder = new MediaRecorder();
//            File audioFile = new File("AudioRecording.aac");
//            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//            recorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
//            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
//
//            recorder.setOutputFile(audioFile);
//            try {
//                recorder.prepare();
//                recorder.start();
//
//                Timer recorderTimer = new Timer();
//                recorderTimer.schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        recorder.stop();
//                        recorder.release();
//                    }
//                }, 60000 * 15);
//            } catch (IOException e) {
//                System.out.println("IOException caused in recorder by: " + e.getCause());
//                System.out.println("IOException message in recorder: " + e.getMessage());
//
//                Log.e(e.getMessage(), "recording failed");
//
//            }
//        final String POST_URL = "http://288b0f001382.ngrok.io";
//        String boundary = Long.toHexString(System.currentTimeMillis());
//        String CRLF = "\r\n";
//        String charset = "UTF-8";
//        URLConnection connection = new URL(POST_URL).openConnection();
//        connection.setDoInput(true);
//        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
//        try (
//                OutputStream output = connection.getOutputStream();
//                PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);
//        ) {
//            writer.append("--" + boundary).append(CRLF);
//            writer.append("Content-Disposition: form-data; name=\"binaryFile\"; filename=\"" + audioFile.getName() + "\"").append(CRLF);
//            writer.append("Content-Length: " + audioFile.length()).append(CRLF);
//            writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(audioFile.getName())).append(CRLF);
//            writer.append("Content-Transfer-Encoding: binary").append(CRLF);
//            writer.append(CRLF).flush();
//            Files.copy(audioFile.toPath(), output);
//            output.flush();
//
//            int responseCode = ((HttpURLConnection) connection).getResponseCode();
//            System.out.println("Response code: [" + responseCode + "]");
//        }

//    }
}
