package com.example.elxonnyagwaru.swahiliplayerapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.util.ArrayList;

public class Player extends AppCompatActivity {
     MediaPlayer mp;
     ArrayList<File> mysongs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Intent i=getIntent();
        Bundle  b= i.getExtras();
        mysongs =(ArrayList) b.getParcelableArrayList("songlist");
        int position = b.getInt("pos",0);
        Uri u = Uri.parse(mysongs.get(position).toString());
        mp=MediaPlayer.create(getApplicationContext(),u);
        mp.start();



    }
}
