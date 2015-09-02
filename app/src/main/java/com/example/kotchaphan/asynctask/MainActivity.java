package com.example.kotchaphan.asynctask;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    private Button btnMusicPlay;
    private MediaPlayer mediaPlayer;
    private ProgressDialog progressDialog;
    private static final int progressbar_type = 0;

    private static String url = "http://programmerguru.com/android-tutorial/wp-content/uploads/2014/01/jai_ho.mp3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnMusicPlay = (Button) findViewById(R.id.btnProgress);
        btnMusicPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMusicPlay.setEnabled(false);
                File file = new File(Environment.getExternalStorageDirectory().getPath() + "/jai_ho.mp3");

                //check if file already exist
                if (file.exists()) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Warning...").setMessage("File already exsits").setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                } else {

                    //call Async Task onPreExcute state
                    new DownloadMusic().execute(url);
                }
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progressbar_type:
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Downloading...");
                progressDialog.setIndeterminate(false);
                progressDialog.setMax(100);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setCancelable(false);
                progressDialog.show();
                return progressDialog;
            default:
                return null;
        }
    }

    class DownloadMusic extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //show progress bar and call doInBackground
            showDialog(progressbar_type);
        }

        @Override
        protected String doInBackground(String... params) {

            int count;
            try {
                URL url = new URL(params[0]);
                URLConnection connection = url.openConnection();
                //connection
                connection.connect();

                int sizeOfFile = connection.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream(), 10 * 1024);
                //Output to write
                OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/jai_go.mp3");
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    //display progress
                    publishProgress("" + (int) ((total * 100) / sizeOfFile));

                    //write data to file
                    output.write(data, 0, count);
                }
                //flush out
                output.flush();
                //close stream
                output.close();
                input.close();
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            //percent for download
            progressDialog.setProgress(Integer.parseInt(values[0]));
        }

        @Override
        protected void onPostExecute(String s) {
            //dismiss dialog
            dismissDialog(progressbar_type);
            new AlertDialog.Builder(MainActivity.this).setMessage("Downlaod complete").setTitle("Downloaded").show();
            btnMusicPlay.setEnabled(true);
        }
    }
}
