package com.example.sql;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.blogspot.atifsoftwares.circularimageview.CircularImageView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddUpdate extends AppCompatActivity {

    CircularImageView civImage;
    private EditText etProductName, etBrand, etModel, etSerialNumber, etPrice, etDescription,etTELEFONO;
    private FloatingActionButton fabSave;
    private ActionBar actionBar;

    private String id;
    private Uri image;
    private String product_name;
    private String brand;
    private String model;
    private String serialnumber;
    private String price;
    private String description;
    private String TELEFONO;
    private HelperDB helperDB;

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;
    private static final int IMAGE_PICK_CAMERA_CODE = 102;
    private static final int IMAGE_PICK_GALLERY_CODE = 103;

    private String[] cameraPermissions;
    private String[] storagePermissions;
    private Uri imageUri;
    private boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update);

        civImage = findViewById(R.id.civImage);
        etProductName = findViewById(R.id.etProductName);
        etBrand = findViewById(R.id.etBrand);
        etModel = findViewById(R.id.etModel);
        etSerialNumber = findViewById(R.id.etSerialNumber);
        etPrice = findViewById(R.id.etPrice);
        etDescription = findViewById(R.id.etDescription);
        etTELEFONO = findViewById(R.id.etTELEFONO);
        fabSave = findViewById(R.id.fabSave);

        helperDB = new HelperDB(this);

        cameraPermissions = new String[] {
                Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        storagePermissions = new String[] {
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        actionBar = getSupportActionBar();
        actionBar.setTitle("Registrar producto");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        civImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePickDialog();
            }
        });


        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });

        Intent intent = getIntent();
        isEditMode = intent.getBooleanExtra("isEditMode", false);

        if (isEditMode) {

            actionBar.setTitle("Actualizar datos");

            id = intent.getStringExtra("ID");
            product_name = intent.getStringExtra("PRODUCT_NAME");
            image = Uri.parse(intent.getStringExtra("IMAGE"));
            brand = intent.getStringExtra("BRAND");
            model = intent.getStringExtra("MODEL");
            serialnumber = intent.getStringExtra("SERIALNUMBER");
            price = intent.getStringExtra("PRICE");
            description = intent.getStringExtra("DESCRIPTION");

            etProductName.setText(product_name);
            etBrand.setText(brand);
            etModel.setText(model);
            etSerialNumber.setText(serialnumber);
            etPrice.setText(price);
            etDescription.setText(description);

            if (imageUri.toString().equals("null")) {
                civImage.setImageResource(R.drawable.ic_image001);
            } else {
                civImage.setImageURI(imageUri);
            }

        } else {

        }
    }

    private void insertData() {
        product_name    = "" + etProductName.getText().toString().trim();
        brand           = "" + etBrand.getText().toString().trim();
        model           = "" + etModel.getText().toString().trim();
        serialnumber    = "" + etSerialNumber.getText().toString().trim();
        price           = "" + etPrice.getText().toString().trim();
        description     = "" + etDescription.getText().toString().trim();
        TELEFONO     = "" + etTELEFONO.getText().toString().trim();

        if (isEditMode) {
            helperDB.updateRecord(
                    "" + id,
                    "" + product_name,
                    "" + imageUri,
                    "" + brand,
                    "" + model,
                    "" + serialnumber,
                    "" + price,
                    "" + description,
                    "" + TELEFONO

            );

            Toast.makeText(this, "Registro actualizado con éxito, ID: " + id, Toast.LENGTH_SHORT).show();

        } else {


            long id = helperDB.insertRecord(

                    "" + product_name,
                    "" + imageUri,
                    "" + brand,
                    "" + model,
                    "" + serialnumber,
                    "" + price,
                    "" + description,
                    "" + TELEFONO

                    );

            Toast.makeText(this, "Registro almacenado con éxito, ID: " + id, Toast.LENGTH_SHORT).show();
        }
    }

    private void imagePickDialog() {
        String[] options = {"Cámara", "Galería"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Elegir imagen desde");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    if (!checkCameraPermissions()) {
                        requestCameraPermission();
                    } else {
                        pickImageFromCamera();
                    }
                } else if (which == 1) {
                    if (!checkStoragePermission()) {
                        requestStoragePermission();
                    } else {
                        pickImageFromGallery();
                    }
                }
            }
        });

        builder.create().show();
    }

    private void pickImageFromCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Título");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Descripción");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }

    private void pickImageFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY_CODE);
    }


    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermissions() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case CAMERA_REQUEST_CODE: {
                if (grantResults.length > 0) {

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted && storageAccepted) {
                        pickImageFromCamera();
                    } else {
                        Toast.makeText(this, "Necesita otorgar permisos para usar la cámara de su dispositivo", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;

            case STORAGE_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted) {
                        pickImageFromGallery();
                    } else {
                        Toast.makeText(this, "Necesita otorgar permisos para usar el almacenamiento externo de su dispositivo", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            } else if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    Uri resultUri = result.getUri();
                    imageUri = resultUri;
                    civImage.setImageURI(resultUri);
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                    Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}