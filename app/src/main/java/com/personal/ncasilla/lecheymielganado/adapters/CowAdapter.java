package com.personal.ncasilla.lecheymielganado.adapters;

import android.content.Context;
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

import java.util.ArrayList;

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
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cattle_item,parent,false);
        }

        TextView cowCode = convertView.findViewById(R.id.codeName);
        TextView cowGender = convertView.findViewById(R.id.cow_gender);
        TextView cowAge = convertView.findViewById(R.id.cow_age);
        TextView cowCharac = convertView.findViewById(R.id.cow_charac);
        TextView cowColor = convertView.findViewById(R.id.cow_color);
        ImageView cowImage = convertView.findViewById(R.id.cattle_imageView);

        cowCode.setText(cow.getNameCode());
        cowGender.setText(cow.getGender());
        cowAge.setText( String.valueOf(cow.getAge()) );
        cowCharac.setText(cow.getCharacteristic());
        cowColor.setText(cow.getColor());
        cowImage.setImageBitmap(cow.getImage());
        return convertView;
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
