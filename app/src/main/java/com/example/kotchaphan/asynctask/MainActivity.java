package com.example.kotchaphan.asynctask;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.File;

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


        btnMusicPlay = (Button)findViewById(R.id.btnProgress);
        btnMusicPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMusicPlay.setEnabled(false);
                File file = new File(Environment.getExternalStorageDirectory().getPath()+"/jai_ho.mp3");
            }
        });
    }
}
