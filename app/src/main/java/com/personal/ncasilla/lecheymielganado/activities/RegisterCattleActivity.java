package com.personal.ncasilla.lecheymielganado.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.textfield.TextInputEditText;
import com.personal.ncasilla.lecheymielganado.R;
import com.personal.ncasilla.lecheymielganado.databinding.ActivityRegisterCattleBinding;
import com.personal.ncasilla.lecheymielganado.viewmodels.RegisterCattleViewModel;

import java.io.IOException;
import java.util.ArrayList;


public class RegisterCattleActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton imageButton;
    private RegisterCattleViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityRegisterCattleBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_register_cattle);

        setTitle("Añadir");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Spinner spinner = findViewById(R.id.cow_gender_spinner);
        imageButton = findViewById(R.id.cattel_image);
        Button addCow = findViewById(R.id.add_cow_button);

        Spinner yearSpinner = findViewById(R.id.cow_year_spinner);
        Spinner monthSpinner = findViewById(R.id.cow_months_spinner);
        ArrayList<String> year = new ArrayList<>();
        ArrayList<String> month = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            year.add(String.valueOf(i));
        }
        for (int i = 0; i < 12; i++) {
            month.add(String.valueOf(i));
        }
        ArrayAdapter<String> yearAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, year);
        ArrayAdapter<String> monthAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, month);
        yearSpinner.setAdapter(yearAdapter);
        monthSpinner.setAdapter(monthAdapter);

        TextInputEditText nameText = findViewById(R.id.cattel_name);
        nameText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        nameText.setSingleLine(true);
//        TextInputEditText ageText = findViewById(R.id.cattel_age);
//        ageText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
//        ageText.setSingleLine(true);
        TextInputEditText colorText = findViewById(R.id.cattel_color);
        colorText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        colorText.setSingleLine(true);
        TextInputEditText extraText = findViewById(R.id.cattel_extra_data);
        extraText.setImeOptions(EditorInfo.IME_ACTION_DONE);


        viewModel = new RegisterCattleViewModel(spinner, this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                imageButton.setEnabled(false);
                this.requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            }
        }

        addCow.setOnClickListener(this);

        imageButton.setOnClickListener(this);

        binding.setViewModel(viewModel);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                imageButton.setEnabled(true);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Bitmap imageBitmap = null;
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data != null ? data.getExtras() : null;
            imageBitmap = (Bitmap) (extras != null ? extras.get("data") : null);
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            Uri contentURI = data != null ? data.getData() : null;
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (imageBitmap != null) {
            imageButton.setImageBitmap(imageBitmap);
            viewModel.image = imageBitmap;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_cow_button:
                viewModel.sendData(this, (ProgressBar) findViewById(R.id.progress_bar_register));
                break;
            case R.id.cattel_image:
                showDialog();
                break;
        }
    }

    public void showDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (galleryIntent.resolveActivity(this.getPackageManager()) != null) {
            startActivityForResult(galleryIntent, 2);
        }

    }

    public void takePhotoFromCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
            this.startActivityForResult(takePictureIntent, 1);
        }
    }
}
