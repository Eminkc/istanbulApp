package com.example.mabiluygulamaproje;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "IstanbulDB";
    private static final int DATABASE_VERSION = 9;

    // Tablo ve kolon isimleri
    private static final String TABLE_NAME = "istanbul_details";
    private static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_YOUTUBE_URL = "youtube_url";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_YOUTUBE_URL + " TEXT)";
        db.execSQL(createTable);

        // Örnek veri ekle
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, "İstanbul'un Tarihi");
        values.put(COLUMN_DESCRIPTION, "İstanbul, Türkiye'nin en kalabalık, ekonomik, tarihi ve sosyo-kültürel açıdan en önemli şehridir. Boğaziçi'nin muhteşem manzarası ve tarihi yapılarıyla dünyaca ünlü bir şehirdir.");
        values.put(COLUMN_YOUTUBE_URL, "eV6lTEY95yY");
        db.insert(TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor getIstanbulDetails() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }
}