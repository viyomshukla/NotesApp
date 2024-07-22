package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashSet;
import java.util.Set;

public class newactivity extends AppCompatActivity {
int idx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newactivity);
        EditText edt=findViewById(R.id.edt);
        Intent intent=getIntent();
         idx=intent.getIntExtra("position",-1);
        if(idx!=-1) {
            edt.setText(MainActivity.Notes.get(idx));
        }
        else {
            MainActivity.Notes.add("");
            idx=MainActivity.Notes.size()-1;
        }
        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            MainActivity.Notes.set(idx,String.valueOf(s));
            MainActivity.arrayAdapter.notifyDataSetChanged();


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Button btn=findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences=newactivity.this.getSharedPreferences("com.example.notesapp",MODE_PRIVATE);
                Set<String>set=new HashSet<>(MainActivity.Notes);
                sharedPreferences.edit().putStringSet("Notes",set).apply();
                
                Set<String>t=sharedPreferences.getStringSet("Notes",new HashSet<String>());
                Log.i("notes",t.toString());
                Intent intent1= new Intent(newactivity.this,MainActivity.class);
                startActivity(intent1);
            }
        });
    }
}