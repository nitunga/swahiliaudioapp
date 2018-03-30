package com.example.elxonnyagwaru.swahiliplayerapp;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.widget.Toast;
import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    String[] items;
    ArrayList<File> mysongs;
    ArrayAdapter<String> adp;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv=findViewById(R.id.lvp);
         searchView=findViewById(R.id.menu_search);

         mysongs=findSongs(Environment.getExternalStorageDirectory());

        items = new String[ mysongs.size()];

        for (int i=0;i<mysongs.size();i++){
          //  toast(mysongs.get(i).getName().toString());
            items[i] = mysongs.get(i).getName().toString().replace(".mp3","").replace(".aac","").replace(".m4a","");
        }
        adp = new ArrayAdapter<String>(getApplication(),R.layout.songlist,R.id.textView,items);

        lv.setAdapter(adp);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                    startActivity(new Intent(getApplicationContext( ), Player.class).putExtra("pos", position).putExtra("songlist", mysongs));

                    Intent myintent=new Intent(view.getContext(),Player.class);
                    startActivityForResult(myintent,position);




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
                if (singlefile.getName().endsWith(".mp3")|| singlefile.getName().endsWith(".aac")|| singlefile.getName().endsWith(".m4a")){
                    al.add(singlefile);
                }

            }
        }
        return al;
    }

    public void toast(String text){
        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();

    }

////handling searchView in a actionbar//////

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_search,menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this,Player.class)));
        searchView.setQueryHint(getResources().getString(R.string.search_hint));


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener( ) {

            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                adp.getFilter().filter(text);


                if (TextUtils.isEmpty(text)) {
                    lv.clearTextFilter();
                }
                else {
                    lv.setFilterText(text);
                }

                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

}



