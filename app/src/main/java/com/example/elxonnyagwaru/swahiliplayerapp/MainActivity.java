package com.example.elxonnyagwaru.swahiliplayerapp;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    String[] items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv=(ListView) findViewById(R.id.lvp);
        final ArrayList<File> mysongs=findSongs(Environment.getExternalStorageDirectory());
        items = new String[ mysongs.size()];
        // ArrayList<File> songs=findSongs(Environment.getRootDirectory());
        for (int i=0;i<mysongs.size();i++){
           // toast(mysongs.get(i).getName().toString());
            items[i] = mysongs.get(i).getName().toString().replace(".mp3","").replace(".aac","");
        }
        ArrayAdapter<String> adp = new ArrayAdapter<String>(getApplication(),R.layout.songlist,R.id.textView,items);
        lv.setAdapter(adp);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
              startActivity(new Intent(getApplicationContext(),Player.class).putExtra("pos",position).putExtra("songlist",mysongs));
            }
        });
    }
    public ArrayList<File> findSongs(File root){
        ArrayList<File> al = new ArrayList<File>();
        File[] files = root.listFiles();
        for (File singlefile : files){
            if (singlefile.isDirectory() && !singlefile.isHidden()){
                al.addAll(findSongs(singlefile));

            }
            else{
                if (singlefile.getName().endsWith(".mp3")|| singlefile.getName().endsWith(".aac")){
                    al.add(singlefile);
                }

            }
        }
        return al;
    }

    public void toast(String text){
        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();

    }

}
