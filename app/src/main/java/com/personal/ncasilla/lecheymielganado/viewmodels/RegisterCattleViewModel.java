package com.personal.ncasilla.lecheymielganado.viewmodels;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;


import androidx.annotation.NonNull;
import androidx.databinding.InverseBindingMethod;
import androidx.databinding.InverseBindingMethods;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.personal.ncasilla.lecheymielganado.activities.RegisterCattleActivity;
import com.personal.ncasilla.lecheymielganado.models.Cow;

import java.io.ByteArrayOutputStream;
import java.util.Random;

@InverseBindingMethods({
        @InverseBindingMethod(type = Spinner.class, attribute = "android:selectedItemPosition"),
})

public class RegisterCattleViewModel extends ViewModel {


    private Spinner spinner;
    public Bitmap image;

    private Cow cow;

    public ObservableField<String> nameId = new ObservableField<>();
    public MutableLiveData<Integer> genders = new MutableLiveData<>();
    public ObservableField<String> age = new ObservableField<>();
    public ObservableField<String> color = new ObservableField<>();
    public ObservableField<String> characteristic = new ObservableField<>();

    public RegisterCattleViewModel(Spinner spinner) {
        this.spinner = spinner;
        cow = new Cow();

    }


    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String element = spinner.getItemAtPosition(i).toString();
        cow.setGender(element);
    }


    public  DatabaseReference sendData(final Context context, final ProgressBar progressBar){
        progressBar.setVisibility(View.VISIBLE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        int id = new Random().nextInt();
        DatabaseReference reference = database.getReference("cattle/"+id );
        cow.setAge(age.get());
        cow.setCharacteristic(characteristic.get());
        cow.setNameCode(nameId.get());
        cow.setId(id);
        cow.setColor(color.get());
        cow.setImage(encodeBitmap(image));

        reference.setValue(cow).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressBar.setVisibility(View.GONE);
                ((RegisterCattleActivity)context).finish();
            }
        });

        return reference;
    }

    private String encodeBitmap(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        return Base64.encodeToString(stream.toByteArray(),Base64.DEFAULT);
    }
}
