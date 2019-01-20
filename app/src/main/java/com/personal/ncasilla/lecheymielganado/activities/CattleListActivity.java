package com.personal.ncasilla.lecheymielganado.activities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.personal.ncasilla.lecheymielganado.R;
import com.personal.ncasilla.lecheymielganado.adapters.CowAdapter;
import com.personal.ncasilla.lecheymielganado.models.Cow;

import java.util.ArrayList;

public class CattleListActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private ArrayList<Cow> cowArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cattel_list);


        databaseReference = FirebaseDatabase.getInstance().getReference("cattle");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Cow cow = dataSnapshot.getValue(Cow.class);
                cowArrayList.add(cow);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Cow cow = dataSnapshot.getValue(Cow.class);
                cowArrayList.add(cow);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        CowAdapter cowAdapter = new CowAdapter(this,cowArrayList);

        ListView cattleListView = findViewById(R.id.cattel_listView);

        cattleListView.setAdapter(cowAdapter);

        cattleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),
                        "Click ListItem Number " + i, Toast.LENGTH_LONG)
                        .show();

            }
        });
    }
}
