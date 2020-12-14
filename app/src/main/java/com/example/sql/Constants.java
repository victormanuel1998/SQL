package com.example.sql;

public class Constants {

    // nombre de la base de datos
    public static final String DB_NAME = "BD01";
    // db version

    public static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "PRODUCTS";

    // Definir campos de la tabla
    public static final String C_ID = "ID";
    public static final String C_PRODUCT_NAME = "PRODUCT_NAME";
    public static final String C_IMAGE = "IMAGE";
    public static final String C_BRAND = "BRAND";
    public static final String C_MODEL = "MODEL";
    public static final String C_SERIALNUMBER = "SERIALNUMBER";
    public static final String C_PRICE = "PRICE";
    public static final String C_DESCRIPTION = "DESCRIPTION";
    public static final String C_TELEFONO = "TELEFONO";

    // Crear consultas de tabla
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + C_PRODUCT_NAME + " TEXT,"
            + C_IMAGE + " TEXT,"
            + C_BRAND + " TEXT,"
            + C_MODEL + " TEXT,"
            + C_SERIALNUMBER + " TEXT,"
            + C_PRICE + " TEXT,"
            + C_DESCRIPTION + " TEXT"
            + C_TELEFONO + " TEXT"
            + ")";

    // CREATE TABLE PRODUCTS(
    //      ID INT NOT NULL AUTO_INCREMENT,
    //        ...
    //        PRIMARY KEY(ID)
    // );
}
