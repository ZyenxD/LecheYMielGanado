package com.personal.ncasilla.lecheymielganado.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.personal.ncasilla.lecheymielganado.R;
import com.personal.ncasilla.lecheymielganado.adapters.CowAdapter;
import com.personal.ncasilla.lecheymielganado.models.Cow;

import java.util.ArrayList;

public class CattleListActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private CowAdapter cowAdapter;
    private ArrayList<Cow> cowArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cattel_list);

        cowAdapter = new CowAdapter(this, cowArrayList);
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

        databaseReference = FirebaseDatabase.getInstance().getReference("cattle");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Cow cow = dataSnapshot.getValue(Cow.class);
                cowArrayList.add(cow);
                cowAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Cow cow = dataSnapshot.getValue(Cow.class);
                cowArrayList.add(cow);
                cowAdapter.notifyDataSetChanged();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cattle_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_cow:
                Intent intent = new Intent(this, RegisterCattleActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        cowAdapter.notifyDataSetChanged();
    }
}
