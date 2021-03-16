package com.example.socialmediaappfirebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.HashMap;
import java.util.List;

public class recyclerpage extends AppCompatActivity  {
RecyclerView rs1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerpage);
        rs1=(RecyclerView)findViewById(R.id.recycle);
        rs1.setAdapter(new Adapter(this));

        rs1.setLayoutManager(new LinearLayoutManager(this));

    }



}