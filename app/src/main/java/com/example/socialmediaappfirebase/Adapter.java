package com.example.socialmediaappfirebase;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.net.CookieHandler;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<viewholders> {
    Context c1;


    public Adapter(recyclerpage activity) {
        c1=activity;
    }


    @NonNull
    @Override
    public viewholders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater ai= LayoutInflater.from(parent.getContext());
View view=ai.inflate(R.layout.posts,parent,false);



        return new viewholders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholders holder, final int position) {

        Picasso.with(c1).load(socialpage.adding.get(position).get("IMAGE LINK")).into(holder.getImg2());
holder.getTdesc().setText(socialpage.adding.get(position).get("des"));
holder.getTname().setText(socialpage.adding.get(position).get("from whom"));



    }

    @Override
    public int getItemCount() {
        return socialpage.adding.size();
    }
}
