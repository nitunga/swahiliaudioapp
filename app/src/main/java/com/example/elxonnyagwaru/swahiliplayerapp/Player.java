package com.example.elxonnyagwaru.swahiliplayerapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.io.File;
import java.util.ArrayList;

public class Player extends AppCompatActivity implements View.OnClickListener   {
     static MediaPlayer mp;
     ArrayList<File> mysongs;
     SeekBar sb;
     Uri u;
     Button  btplay,btnext,btprev;
     int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_player);
         btplay =(Button) findViewById(R.id.play);
         btprev = (Button) findViewById(R.id.prev);
         btnext = (Button) findViewById(R.id.next);


         btplay.setOnClickListener(this);
         btnext.setOnClickListener(this);
         btprev.setOnClickListener(this);

         if (mp!=null){
             mp.stop();
             mp.release();
         }

        Intent i=getIntent();
        Bundle  b= i.getExtras();
        mysongs =(ArrayList) b.getParcelableArrayList("songlist");
        int position = b.getInt("pos",0);
        Uri u = Uri.parse(mysongs.get(position).toString());
        mp=MediaPlayer.create(getApplicationContext(),u);
        mp.start();

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id){
            case R.id.play:
                if(mp.isPlaying()){
                    btplay.setText("play");
                   mp.pause();
                }
                else {
                    btplay.setText("stop");
                    mp.start();
                }

                break;

            case R.id.next:
                mp.stop();
                mp.release();
                position =(position+1)%mysongs.size();
                Uri u = Uri.parse(mysongs.get(position).toString());
                mp=MediaPlayer.create(getApplicationContext(),u);
                mp.start();
                break;
                case R.id.prev:
                    mp.stop();
                    mp.release();
                    position =(position -1<0)? mysongs.size()-1:position -1;
                    u = Uri.parse(mysongs.get(position).toString());
                    mp=MediaPlayer.create(getApplicationContext(),u);
                    mp.start();
                break;



        }

    }
}
