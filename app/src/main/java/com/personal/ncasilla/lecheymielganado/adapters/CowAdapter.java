package com.personal.ncasilla.lecheymielganado.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.personal.ncasilla.lecheymielganado.R;
import com.personal.ncasilla.lecheymielganado.models.Cow;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CowAdapter extends ArrayAdapter<Cow> {

    private final ArrayList<Cow> cows;

    public CowAdapter(@NonNull Context context, ArrayList<Cow> cowsArrayList) {
        super(context, 0);
        this.cows = cowsArrayList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Cow cow = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cattle_item, parent, false);
        }

        TextView cowCode = convertView.findViewById(R.id.codeName);
        TextView cowGender = convertView.findViewById(R.id.cow_gender);
        TextView cowAge = convertView.findViewById(R.id.cow_age);
        TextView cowCharac = convertView.findViewById(R.id.cow_charac);
        TextView cowColor = convertView.findViewById(R.id.cow_color);
        ImageView cowImage = convertView.findViewById(R.id.cattle_imageView);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate date = LocalDate.parse(cow.getAge());
            LocalDate now = LocalDate.now();
            String fecha = Period.between(date, now).getYears() != 0 ? Period.between(date, now).getYears() + " año(s) y " : "";
            fecha += Period.between(date, now).getMonths() != 0 ? Period.between(date, now).getMonths() + " mes(es)" : "";
            cowAge.setText(fecha);
        } else {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            try {
                Date date = format.parse(cow.getAge());

                Calendar thenCalendar = new GregorianCalendar();
                Calendar todayCalendar = new GregorianCalendar();
                thenCalendar.setTime(date);
                todayCalendar.setTime(new Date());
                int yearsInBetween = todayCalendar.get(Calendar.YEAR) - thenCalendar.get(Calendar.YEAR);
                int monthDiff = todayCalendar.get(Calendar.MONTH) - (thenCalendar.get(Calendar.DAY_OF_MONTH) >= 15 ?
                        thenCalendar.get(Calendar.MONTH)+1 : thenCalendar.get(Calendar.MONTH));

                String fecha = yearsInBetween != 0 ? yearsInBetween + " año(s) y " : "";
                fecha += monthDiff != 0 ? monthDiff + " mes(es)" : "";
                cowAge.setText(fecha);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        cowCode.setText(cow.getNameCode());
        cowGender.setText(cow.getGender());
        cowCharac.setText(cow.getCharacteristic());
        cowColor.setText(cow.getColor());
        cowImage.setImageBitmap(decodeFromFirebaseBase64(cow.getImage()));
        return convertView;
    }

    private Bitmap decodeFromFirebaseBase64(String image) {
        byte[] decodedBytes = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    @Override
    public int getCount() {
        return cows.size();
    }

    @Nullable
    @Override
    public Cow getItem(int position) {
        return cows.get(position);
    }
}
