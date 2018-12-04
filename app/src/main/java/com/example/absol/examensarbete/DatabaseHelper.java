package com.example.absol.examensarbete;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private String TABLE_NAME;
    private static final String COL0 = "id";
    private static final String COL1 = "gender";
    private static final String COL2 = "name";
    private static final String COL3 = "mIndex";

    DatabaseHelper(Context context, String tablename) {
        super(context, tablename, null, 1);
        this.TABLE_NAME = tablename;
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
        updateIndexAfterDelete(itemIndex);
    }

    void updateIndexAfterDelete(int itemIndex) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL3 + " = " +
                COL3 + "-1 " + "WHERE " + COL3 + " > " + itemIndex;

        db.execSQL(query);
    }

    void moveItems(int currentPosition, int newPosition) {

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABLE_NAME + " SET " + COL3 + " = CASE "+
                COL3 + " WHEN " + newPosition + " THEN " + currentPosition +
                " WHEN " + currentPosition + " THEN " + newPosition + " END "+
                " WHERE " + COL3 + " IN (" + currentPosition + ", " + newPosition+")";

        Log.d(TAG, "moveItems: " + query);

        db.execSQL(query);
    }

    boolean checkIfTableExists (String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+tableName+"'", null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    void createTable(String tableName) {
        Log.d(TAG, "createTable: " + tableName);

        SQLiteDatabase db = this.getWritableDatabase();
        String createTable = "CREATE TABLE " +tableName+ " ( " +COL0+ " INTEGER PRIMARY KEY, " +
                COL1+ " TEXT, " +COL2+ " TEXT, " +COL3+ " TEXT)";
        db.execSQL(createTable);
    }

    void changeTable(String tableName) {
        this.TABLE_NAME = tableName;
    }

    void deleteTable(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS " + tableName);
    }

}
