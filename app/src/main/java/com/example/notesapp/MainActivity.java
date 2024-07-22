package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class MainActivity extends AppCompatActivity {
 SharedPreferences sharedPreferences;

   static ListView listView;
    static ArrayList<String>Notes=new ArrayList<>();
    static  ArrayAdapter<String>arrayAdapter;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int ide=item.getItemId();
        if(ide==R.id.add){
            Intent intent=new Intent(MainActivity.this,newactivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.list);
        sharedPreferences=getSharedPreferences("com.example.notesapp",MODE_PRIVATE);

        Toolbar toolbar=findViewById(R.id.toolbar);
         setSupportActionBar(toolbar);

         Set<String>Notesset=sharedPreferences.getStringSet("Notes",new HashSet<>());
         if(Notesset!=null){
             Notes=new ArrayList<>(Notesset);
         }
         else {
             Notes.add("add Notes");
         }
         //ArrayADaptor is use to attached arraylist with listview

         arrayAdapter=new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,Notes);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MainActivity.this,newactivity.class);
                intent.putExtra("position",position);
                startActivity(intent);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            new AlertDialog.Builder(MainActivity.this).setTitle("Are You Sure to delete this")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Notes.remove(position);
                            arrayAdapter.notifyDataSetChanged();
                            SharedPreferences sharedPreferencess = MainActivity.this.getSharedPreferences("com.example.notesapp", MODE_PRIVATE);
                            Set<String> set = new HashSet<>(Notes);
                            sharedPreferencess.edit().putStringSet("Notes", set).apply();

                        }
                    }).setNegativeButton("No",null).show();
            return true;
            }
        });
    }

}