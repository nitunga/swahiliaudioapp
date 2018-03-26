package com.example.elxonnyagwaru.swahiliplayerapp;

import android.annotation.SuppressLint;
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

     Thread update;
     Button  btplay,btnext,btprev;
     int position;


    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_player);

        // myplayer=findViewById(R.id.myplayer);
         btplay = findViewById(R.id.play);
         btprev = findViewById(R.id.prev);
         btnext = findViewById(R.id.next);



         ///handling seekbar here by initializing it
        sb=findViewById(R.id.sbplayer);
        update=new Thread() {
            @Override
            public void run() {
                int totalDuration = mp.getDuration();
                int currentPosition = 0;
               // sb.setMax(totalDuration);
                while (currentPosition < totalDuration) {
                    try {
                        try{
                        currentPosition = mp.getCurrentPosition();}
                        catch (final Exception e){
                            e.printStackTrace();
                        }
                        sleep(500);



                        sb.setProgress(currentPosition);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                    // super.run();
                }
            }
        };



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
        u = Uri.parse(mysongs.get(position).toString());
        mp=MediaPlayer.create(getApplicationContext(),u);
        mp.start();
        mp.setLooping(true);
        sb.setMax(mp.getDuration());
        update.start();


        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean input) {


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
               mp.seekTo(seekBar.getProgress());
            }
        });


    }






    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id){
            case R.id.play:
                if(mp.isPlaying()){
                    btplay.setText("play");
                   mp.pause();
                    mp.setLooping(true);
                }
                else {
                    btplay.setText("stop");
                    mp.start();
                    mp.setLooping(true);
                }

                break;

            case R.id.next:
                mp.reset();
                mp.release();
                position =(position+1)%mysongs.size();
                Uri u = Uri.parse(mysongs.get(position).toString());
                mp=MediaPlayer.create(getApplicationContext(),u);
                mp.start();
                sb.setMax(mp.getDuration());
                break;
                case R.id.prev:
                    mp.reset();
                    mp.release();
                    position =(position -1<0)? mysongs.size()-1:position -1;
                    u = Uri.parse(mysongs.get(position).toString());
                    mp=MediaPlayer.create(getApplicationContext(),u);
                    mp.start();
                    sb.setMax(mp.getDuration());
                break;



        }

    }
}
