package com.personal.ncasilla.lecheymielganado.viewmodels;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Spinner;


import androidx.annotation.NonNull;
import androidx.databinding.InverseBindingMethod;
import androidx.databinding.InverseBindingMethods;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.personal.ncasilla.lecheymielganado.R;
import com.personal.ncasilla.lecheymielganado.activities.RegisterCattleActivity;
import com.personal.ncasilla.lecheymielganado.models.Cow;
import com.personal.ncasilla.lecheymielganado.util.CustomDatePicker;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.logging.SimpleFormatter;

@InverseBindingMethods({
        @InverseBindingMethod(type = Spinner.class, attribute = "android:selectedItemPosition"),
})

public class RegisterCattleViewModel extends ViewModel implements CustomDatePicker.OnDateChangedListener {


    private Spinner cowGenderSpinner;
    private CustomDatePicker datePicker;
    private RegisterCattleActivity registerCattleActivity;
    public Bitmap image;
    private String numOfYears;
    private String numOfMonths;
    private Cow cow;

    public ObservableField<String> nameId = new ObservableField<>();
    public MutableLiveData<Integer> genders = new MutableLiveData<>();
    public MutableLiveData<Integer> status = new MutableLiveData<>();
    public MutableLiveData<Integer> year = new MutableLiveData<>();
    public MutableLiveData<Integer> month = new MutableLiveData<>();
    public MutableLiveData<Integer> bornHere = new MutableLiveData<>();
    public ObservableField<String> age = new ObservableField<>();
    public ObservableField<String> color = new ObservableField<>();
    public ObservableField<String> characteristic = new ObservableField<>();

    public RegisterCattleViewModel(Spinner cowGenderSpinner, RegisterCattleActivity registerCattleActivity) {
        this.cowGenderSpinner = cowGenderSpinner;
        this.registerCattleActivity = registerCattleActivity;
        this.datePicker = registerCattleActivity.findViewById(R.id.cow_date_picker);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.datePicker.setOnDateChangedListener(this);
        }
        cow = new Cow();
    }


    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Spinner spinner = (Spinner) adapterView;


        switch (spinner.getId()) {
            case R.id.cow_gender_spinner:
                String element = cowGenderSpinner.getItemAtPosition(i).toString();
                cow.setGender(element);
                if (element.contains("Hembra")) {
                    this.registerCattleActivity.findViewById(R.id.cow_status_field).setVisibility(View.VISIBLE);
                } else {
                    this.registerCattleActivity.findViewById(R.id.cow_status_field).setVisibility(View.GONE);
                    cow.setStatus("Normal");
                }
                break;
            case R.id.cow_status_spinner:

                String status = ((Spinner) this.registerCattleActivity.findViewById(R.id.cow_status_spinner)).getItemAtPosition(i).toString();
                cow.setStatus(status);

                break;
            case R.id.cow_bornhere_spinner:

                String bornHere = ((Spinner) this.registerCattleActivity.findViewById(R.id.cow_bornhere_spinner)).getItemAtPosition(i).toString();

                if (bornHere.contains("Si")) {
                    cow.setBornHere(true);
                    registerCattleActivity.findViewById(R.id.cow_date_picker_field).setVisibility(View.VISIBLE);
                    registerCattleActivity.findViewById(R.id.cattel_age_field).setVisibility(View.GONE);
                } else {
                    cow.setBornHere(false);
                    registerCattleActivity.findViewById(R.id.cow_date_picker_field).setVisibility(View.GONE);
                    registerCattleActivity.findViewById(R.id.cattel_age_field).setVisibility(View.VISIBLE);
                }
                break;
            case R.id.cow_year_spinner:
                numOfYears = ((Spinner) this.registerCattleActivity.findViewById(R.id.cow_year_spinner)).getItemAtPosition(i).toString();
                break;
            case R.id.cow_months_spinner:
                numOfMonths = ((Spinner) this.registerCattleActivity.findViewById(R.id.cow_months_spinner)).getItemAtPosition(i).toString();
                break;

        }
    }


    public DatabaseReference sendData(final Context context, final ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        int id = new Random().nextInt();
        DatabaseReference reference = database.getReference("cattle/" + id);


        if(!cow.isBornHere() && numOfYears != null && numOfMonths != null){
            Date currDate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currDate);
            calendar.add(Calendar.YEAR,(-Integer.parseInt(numOfYears)));
            calendar.add(Calendar.MONTH,(-Integer.parseInt(numOfMonths)));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                cow.setAge(calendar.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString());
            }else{
                cow.setAge(calendar.getTime().toString());
            }
        }

        cow.setCharacteristic(characteristic.get());
        cow.setNameCode(nameId.get());
        cow.setId(id);
        cow.setColor(color.get());
        cow.setImage(encodeBitmap(image));

        reference.setValue(cow).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressBar.setVisibility(View.GONE);
                ((RegisterCattleActivity) context).finish();
            }
        });

        return reference;
    }

    private String encodeBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate date = LocalDate.of(year, monthOfYear, dayOfMonth);
            cow.setAge(date.toString());
        }else{
            Date currDate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currDate);
            calendar.add(Calendar.YEAR,(-year));
            calendar.add(Calendar.MONTH,(-monthOfYear));
            cow.setAge(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(calendar.getTime()));
        }

    }
}
