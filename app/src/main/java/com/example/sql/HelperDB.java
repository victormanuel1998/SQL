package com.example.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class HelperDB extends SQLiteOpenHelper {

    public HelperDB(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        onCreate(db);
    }

    public long insertRecord(String product_name, String image, String TELEFONO, String brand, String model, String serialnumber, String price, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Constants.C_PRODUCT_NAME, product_name);
        values.put(Constants.C_IMAGE, image);
        values.put(Constants.C_BRAND, brand);
        values.put(Constants.C_MODEL, model);
        values.put(Constants.C_SERIALNUMBER, serialnumber);
        values.put(Constants.C_PRICE, price);
        values.put(Constants.C_DESCRIPTION, description);
        values.put(Constants.C_TELEFONO, TELEFONO);



        long id = db.insert(Constants.TABLE_NAME, null, values);
        // INSERT INTO PRODUCTS VALUES(...);

        db.close();
        return id;
    }

    public void updateRecord(String id, String product_name, String image, String brand, String model, String serialnumber, String price, String description,String TELEFONO) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Constants.C_PRODUCT_NAME, product_name);
        values.put(Constants.C_IMAGE, image);
        values.put(Constants.C_BRAND, brand);
        values.put(Constants.C_MODEL, model);
        values.put(Constants.C_SERIALNUMBER, serialnumber);
        values.put(Constants.C_PRICE, price);
        values.put(Constants.C_DESCRIPTION, description);
        values.put(Constants.C_TELEFONO, TELEFONO);
        db.update(Constants.TABLE_NAME, values, Constants.C_ID + " = ?", new String[] {id});
        // INSERT INTO PRODUCTS VALUES(...);

        db.close();
        // return id;
    }

    public ArrayList<ModelRecord> getAllRecords(String orderBy) {
        ArrayList<ModelRecord> recordArrayList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME + " ORDER BY " + orderBy;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ModelRecord modelRecord = new ModelRecord(
                        "" + cursor.getInt(cursor.getColumnIndex(Constants.C_ID)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_PRODUCT_NAME)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_BRAND)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_MODEL)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_SERIALNUMBER)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_PRICE)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_DESCRIPTION)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_TELEFONO))

                );
                recordArrayList.add(modelRecord);
            } while (cursor.moveToNext());
        }

        db.close();
        return recordArrayList;
    }


    public ArrayList<ModelRecord> searchRecords(String query) {
        ArrayList<ModelRecord> recordArrayList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME + " WHERE " + Constants.C_PRODUCT_NAME + " LIKE '%" + query + "%'";


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ModelRecord modelRecord = new ModelRecord(
                        "" + cursor.getInt(cursor.getColumnIndex(Constants.C_ID)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_PRODUCT_NAME)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_BRAND)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_MODEL)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_SERIALNUMBER)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_PRICE)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_DESCRIPTION)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_TELEFONO))
                );
                recordArrayList.add(modelRecord);
            } while (cursor.moveToNext());
        }

        db.close();
        return recordArrayList;
    }

    public int countRecords() {
        String countQuery = "SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();
        return count;
    }



}
