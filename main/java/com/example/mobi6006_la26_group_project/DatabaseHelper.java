package com.example.mobi6006_la26_group_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.mobi6006_la26_group_project.Object.User;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "MovieTimeDB";
    public static final int DB_VERSION = 1;

    public static final String TABLE_NAME_USER = "User";
    public static final String FIELD_USER_ID = "ID";
    public static final String FIELD_USER_USERNAME = "username";
    public static final String FIELD_USER_DOB = "dob";
    public static final String FIELD_USER_GENDER = "gender";
    public static final String FIELD_USER_EMAIL = "email";
    public static final String FIELD_USER_PHONE_NUMBER = "phoneNumber";
    public static final String FIELD_USER_ADDRESS = "address";
    public static final String FIELD_USER_PASSWORD = "password";

    public static final String createUser =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_USER + " (" +
                    FIELD_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FIELD_USER_USERNAME + " TEXT," +
                    FIELD_USER_DOB + " TEXT," +
                    FIELD_USER_GENDER + " TEXT," +
                    FIELD_USER_EMAIL + " TEXT," +
                    FIELD_USER_PHONE_NUMBER + " TEXT," +
                    FIELD_USER_ADDRESS + " TEXT," +
                    FIELD_USER_PASSWORD + " TEXT)";

    public static final String dropUser =
            "DROP TABLE IF EXISTS " + TABLE_NAME_USER;

    public boolean dbInsertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(FIELD_USER_USERNAME, user.username);
        cv.put(FIELD_USER_DOB, user.dob);
        cv.put(FIELD_USER_GENDER, user.gender);
        cv.put(FIELD_USER_EMAIL, user.email);
        cv.put(FIELD_USER_PHONE_NUMBER, user.phoneNumber);
        cv.put(FIELD_USER_ADDRESS, user.address);
        cv.put(FIELD_USER_PASSWORD, user.password);
        long id = db.insert(TABLE_NAME_USER,null, cv);
        db.close();
        if (id == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public void dbUpdateUser(int id, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(FIELD_USER_ID, id);
        cv.put(FIELD_USER_PASSWORD, password);
        db.update(TABLE_NAME_USER, cv, "id = ?", new String[] {""+id});
    }

    public Cursor getUserData() {
        String sql = "SELECT * FROM " + TABLE_NAME_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(sql, null);
    }

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createUser);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(dropUser);
        onCreate(db);
    }
}