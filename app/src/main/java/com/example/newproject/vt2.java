package com.example.newproject;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class vt2 extends AppCompatActivity {
    private VideoView vidView;
    private MediaController vidControl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vt2);
        vidView = (VideoView) findViewById(R.id.vt);
        vidControl = new MediaController(this);
        vidView.setMediaController(vidControl);
        vidControl.setMediaPlayer(vidView);
        vidView.setVideoURI(Uri.parse("android.resource://com.example.newproject/" + R.raw.vt2));
        vidView.requestFocus();
        vidView.start();
    }
}
