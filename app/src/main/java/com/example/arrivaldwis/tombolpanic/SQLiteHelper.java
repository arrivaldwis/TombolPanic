package com.example.arrivaldwis.tombolpanic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ArrivalDwiS on 3/1/2015.
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String LOGCAT = null;

    public SQLiteHelper(Context applicationcontext) {
        super(applicationcontext, "kontak.db", null, 4);
        Log.d(LOGCAT, "Created");
    }

    @Override
  /* kbuat tabel kontak
   */
    public void onCreate(SQLiteDatabase database) {
        String query;
        query = "CREATE TABLE kontak ( kontakid INTEGER PRIMARY KEY AUTOINCREMENT," +
                " nama TEXT,notelp TEXT)";
        database.execSQL(query);
        Log.d(LOGCAT,"kontak Created");
    }
    @Override
  /*upgrade tabelKontak
   */
    public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
        String query;
        query = "DROP TABLE IF EXISTS kontak";
        database.execSQL(query);
        onCreate(database);
    }
    /* kode untuk insert data ke tabel kontak
     *
     */
    public void addKontak(HashMap<String, String> queryValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nama", queryValues.get("nama"));
        values.put("notelp", queryValues.get("notelp"));
        database.insert("kontak", null, values);
        database.close();
    }

    public void hapusKontak(String id) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete("kontak", "kontakid = " + id, null);
        database.close();
    }



    /* getAllKontak
     * menampilkan data seluruh Kontak ke listview
     *
     */
    public ArrayList<HashMap<String, String>> getAllKontak() {
        ArrayList<HashMap<String, String>> kontakList;
        kontakList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM kontak";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", cursor.getString(0));
                map.put("nama", cursor.getString(1));
                map.put("notelp", cursor.getString(2));
                kontakList.add(map);
            } while (cursor.moveToNext());
        }

        // return contact list
        return kontakList;
    }
}
