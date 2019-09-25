package com.personal.ncasilla.lecheymielganado.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.personal.ncasilla.lecheymielganado.R;
import com.personal.ncasilla.lecheymielganado.databinding.ActivityRegisterCattleBinding;
import com.personal.ncasilla.lecheymielganado.viewmodels.RegisterCattleViewModel;


public class RegisterCattleActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton imageButton;
    private RegisterCattleViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityRegisterCattleBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_register_cattle);

        setTitle("Añadir");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Spinner spinner = findViewById(R.id.cow_gender_spinner);
        imageButton = findViewById(R.id.cattel_image);
        Button addCow = findViewById(R.id.add_cow_button);


        viewModel = new RegisterCattleViewModel(spinner);

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
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data != null ? data.getExtras() : null;
            Bitmap imageBitmap = (Bitmap) (extras != null ? extras.get("data") : null);
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
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
                    this.startActivityForResult(takePictureIntent, 1);
                }
                break;
        }
    }
}
