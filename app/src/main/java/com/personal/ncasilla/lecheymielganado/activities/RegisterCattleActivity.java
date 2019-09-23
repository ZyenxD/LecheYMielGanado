package com.personal.ncasilla.lecheymielganado.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.personal.ncasilla.lecheymielganado.R;
import com.personal.ncasilla.lecheymielganado.models.Cow;

import java.util.Random;

public class RegisterCattleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_cattle);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("cattle/"+ new Random().nextInt());
        Cow cow = new Cow();
        cow.setAge(5);
        cow.setCharacteristic("EXAMPLE");
        cow.setGender("male");
        cow.setId(1);
        cow.setColor("black");
        reference.setValue(cow);
    }
}
