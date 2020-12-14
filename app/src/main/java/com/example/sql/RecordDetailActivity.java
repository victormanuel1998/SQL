package com.example.sql;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.blogspot.atifsoftwares.circularimageview.CircularImageView;

public class RecordDetailActivity extends AppCompatActivity {

    private CircularImageView civImage3;
    private TextView tvProduct, tvDescription, tvBrand, tvPrice;
    private ActionBar actionBar;
    private String recordID;
    private HelperDB helperDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Detalles del producto");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        recordID = intent.getStringExtra("RECORD_ID");

        helperDB = new HelperDB(this);

        civImage3 = findViewById(R.id.civImage3);
        tvProduct = findViewById(R.id.tvProduct);
        tvDescription = findViewById(R.id.tvDescription);
        tvBrand = findViewById(R.id.tvBrand);
        tvPrice = findViewById(R.id.tvPrice);

        showRecordDetails();

    }

    private void showRecordDetails() {
        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME + " WHERE " + Constants.C_ID + " =\"" + recordID + "\"";

        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String id = "" + cursor.getInt(cursor.getColumnIndex(Constants.C_ID));
                String product_name = "" + cursor.getString(cursor.getColumnIndex(Constants.C_PRODUCT_NAME));
                String image = "" + cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE));
                String brand = "" + cursor.getString(cursor.getColumnIndex(Constants.C_BRAND));
                String model = "" + cursor.getString(cursor.getColumnIndex(Constants.C_MODEL));
                String serialNumber = "" + cursor.getString(cursor.getColumnIndex(Constants.C_SERIALNUMBER));
                String price = "" + cursor.getString(cursor.getColumnIndex(Constants.C_PRICE));
                String description = "" + cursor.getString(cursor.getColumnIndex(Constants.C_DESCRIPTION));
                String TELEFONO = "" + cursor.getString(cursor.getColumnIndex(Constants.C_TELEFONO));

                if (image.equals("null")) {
                    civImage3.setImageResource(R.drawable.ic_launcher_foreground);
                } else {
                    civImage3.setImageURI(Uri.parse(image));
                }

                tvProduct.setText(product_name);
                tvDescription.setText(description);
                tvBrand.setText(brand);
                tvPrice.setText(price);

            } while (cursor.moveToNext());
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}