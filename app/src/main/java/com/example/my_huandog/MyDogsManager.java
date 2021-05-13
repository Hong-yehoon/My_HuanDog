package com.example.my_huandog;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MyDogsManager extends AppCompatActivity {

    FloatingActionButton addDog;
    ArrayList<MyDogs> myDogsThumbList = new ArrayList<>();

    private RecyclerViewAdapter_MyDogs adapter;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_mydogs);

        //리사이클러뷰에 LinearLayoutManager 객체 지정
        recyclerView = findViewById(R.id.myDog_Recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //리사이클러뷰에 SimpleTextAdapter 객체 지정
        adapter = new RecyclerViewAdapter_MyDogs(myDogsThumbList);
        recyclerView.setAdapter(adapter);

        setAddDog();

        addDog = findViewById(R.id.floatBtn_addDog);
        addDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyDogsManager.this, MyDogsAddActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setAddDog (){
        myDogsThumbList.add(new MyDogs("1234"));
    }
}
