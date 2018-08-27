package com.example.choi.test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "listDB(3).db";
    public static final String TABLE_NAME = "list";
    public static final String LIST_COLUMN_ID = "id";
    public static final String LIST_COLUMN_NAME = "name";
    public static final String LIST_COLUMN_YEAR = "year";
    public static final String LIST_COLUMN_RATING = "rating";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table list " + "(id integer primary key, name text, year text, rating text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS list");
        onCreate(db);
    }


    public boolean insertList(String name, String year, String rating){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);
        contentValues.put("year", year);
        contentValues.put("rating", rating);

        db.insert(TABLE_NAME, null,contentValues);
        return true;
    }


    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from list where id="+id+"",null);

        return res;
    }

    /*
        public int numberOfRow() {
            SQLiteDatabase db = this.getReadableDatabase();

            int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);

            return numRows;
        }
    */
    public boolean updateList(Integer id, String name, String year, String rating){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);
        contentValues.put("year", year);
        contentValues.put("rating", rating);

        db.update(TABLE_NAME, contentValues, "id = ?", new String[] { Integer.toString(id)});

        return true;
    }

    public Integer deletList(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "id = ?" ,new String[] {Integer.toString(id)});
    }

    public ArrayList getAllList(){

        ArrayList arrayList = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from list",null);

        cursor.moveToFirst();

        while (cursor.isAfterLast() == false){
            arrayList.add(cursor.getString(cursor.getColumnIndex(LIST_COLUMN_ID)) + " "+
                    cursor.getString(cursor.getColumnIndex(LIST_COLUMN_NAME)));

            cursor.moveToNext();
        }

        return arrayList;
    }

}
