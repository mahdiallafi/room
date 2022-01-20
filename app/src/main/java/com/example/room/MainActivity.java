package com.example.room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button save,reset;
    EditText tex;
    RecyclerView recyclerView;
    List<MainData> dataList=new ArrayList<>();
    RoomDB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    save=findViewById(R.id.button);
    reset=findViewById(R.id.button2);
    tex=findViewById(R.id.editTextTextPersonName);
    recyclerView=findViewById(R.id.recycle);

    //initilaize db
        database=RoomDB.getInstance(this);
        //store db value in dta list
        dataList=database.mainDao().getAll();
        //inilizae
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        //set layout
        recyclerView.setLayoutManager( linearLayoutManager);
        //iniliaze adapter
         MainAdapter mainAdapter=new MainAdapter(MainActivity.this,dataList);
         recyclerView.setAdapter(mainAdapter);

         save.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String stext=tex.getText().toString().trim();
                 if(!stext.equals("")){
                     MainData data=new MainData();
                     data.setText(stext);
                     database.mainDao().insert(data);
                     tex.setText("");
                     dataList.clear();
                     dataList.addAll(database.mainDao().getAll());
                     mainAdapter.notifyDataSetChanged();
                 }
             }
         });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.mainDao().reset(dataList);
                dataList.clear();
                dataList.addAll(database.mainDao().getAll());
                mainAdapter.notifyDataSetChanged();
            }
        });


    }
}