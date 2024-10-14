package fvtc.edu.databasedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TAG = "DatabaseHelper";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper(@Nullable Context context,
                          @Nullable String name,
                          @Nullable SQLiteDatabase.CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;

        sql = "CREATE TABLE IF NOT EXISTS tblItems(Id integer primary key autoincrement, FirstName text," + "LastName text);";
        Log.d(TAG, "onCreate: " + sql);
        //create table
        db.execSQL(sql);
        //insert item
        sql = "INSERT INTO tblItems VALUES(1,'Jennifer', 'Aniston');";
        db.execSQL(sql);

        sql = "CREATE TABLE IF NOT EXISTS tblResults(Id integer primary key autoincrement NOT NULL, ChangeDate TEXT NOT NULL," + "Correct integer NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: ");
    }
}
