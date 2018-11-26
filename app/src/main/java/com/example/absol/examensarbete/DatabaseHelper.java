package com.example.absol.examensarbete;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "names_table";
    private static final String COL0 = "id";
    private static final String COL1 = "gender";
    private static final String COL2 = "name";
    private static final String COL3 = "mIndex";

    DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " +TABLE_NAME+ " ( " +COL0+ " INTEGER PRIMARY KEY, " +
                COL1+ " TEXT, " +COL2+ " TEXT, " +COL3+ " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    boolean addData(String name, String gender, int index){
        Cursor data = getData();
        while(data.moveToNext()){
            String n = data.getString(2);
            if(n.equals(name))
                return false;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL1, gender);
        contentValues.put(COL2, name);
        contentValues.put(COL3, index);

        Log.d(TAG, "addData: Adding " +name+ "(" +gender+ ") in " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if data is inserted incorrectly it should return -1
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME +
                " ORDER BY " + COL3;
        return db.rawQuery(query, null);
    }

    Cursor getItemID(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL0 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + name + "'";
        return db.rawQuery(query,null);
    }


    Cursor getItemIndex(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL3 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + name + "'";
        return db.rawQuery(query,null);
    }


    void deleteName(int id, String name, int itemIndex) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " +
                COL0 + " = '" + id + "'" + " AND " +
                COL2 + " = '" + name + "'";
        db.execSQL(query);
        query = "UPDATE " + TABLE_NAME + " SET " + COL3 + " = " +
                COL3 + "-1 " + "WHERE " + COL3 + " > " + itemIndex;

        db.execSQL(query);
    }

    void moveItems(int newPosition, int currentPosition) {
        Log.d(TAG, "moveItems: " +newPosition + " " + currentPosition);
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABLE_NAME + " SET " + COL3 + " = CASE "+
                COL3 + " WHEN " + newPosition + " THEN " + currentPosition +
                " WHEN " + currentPosition + " THEN " + newPosition + " END "+
                " WHERE " + COL3 + " IN (" + currentPosition + ", " + newPosition+")";

        Log.d(TAG, "moveItems: " + query);

        db.execSQL(query);
    }
}
