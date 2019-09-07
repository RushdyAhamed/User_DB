package com.example.db_sqlite.sampledata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UserInfo.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + UserMaster.Users.TABLE_NAME + " (" +
                        UserMaster.Users._ID + " INTEGER PRIMARY KEY," +
                        UserMaster.Users.COLUMN_NAME_USERNAME + " TEXT," +
                        UserMaster.Users.COLUMN_NAME_PASSWORD + " TEXT)";
        // Use the details from the UsersMaster and Users classes we created. Specify the primary key from the BaseColumns interface.

        db.execSQL(SQL_CREATE_ENTRIES);  // This will execute the contents of SQL_CREATE_ENTRIES
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

    public boolean addInfo(String userName, String password) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names the keys
        ContentValues values = new ContentValues();
        values.put(UserMaster.Users.COLUMN_NAME_USERNAME, userName);
        values.put(UserMaster.Users.COLUMN_NAME_PASSWORD, password);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(UserMaster.Users.TABLE_NAME, null, values);
        if (newRowId >= 1)
            return true;
        else
            return false;
    }

    // select all users
    public List readAllInfo()
    {
        SQLiteDatabase db = getReadableDatabase();
        // define a projection that specifieswhich columns from the database
        // you will actually use after this query
        String[] projection = {
                UserMaster.Users._ID,
                UserMaster.Users.COLUMN_NAME_USERNAME,
                UserMaster.Users.COLUMN_NAME_PASSWORD
        };
        //Filter results WHERE "userName" = 'SLIIT USER'
        // String selection = Users.COLUMN_NAME_USERNAME + " = ?";
        //String[] selectionArgs = {""};

        // How you want the results sorted in the resulting cursor
        String sortOrder = UserMaster.Users.COLUMN_NAME_USERNAME + " DESC";

        Cursor cursor = db.query(
                UserMaster.Users.TABLE_NAME,           // the table to query
                projection,                 // the columns to return
                null,               // the columns for the WHERE clause
                null,            // the values for the WHERE clause
                null,               // don't group the rows
                null,                // don't filter by row groups
                sortOrder                  // the sort order
        );
        List userNames = new ArrayList<>();
        List passwords = new ArrayList<>();

        while(cursor.moveToNext()){
            String username = cursor.getString( cursor.getColumnIndexOrThrow(UserMaster.Users.COLUMN_NAME_USERNAME));
            String password = cursor.getString( cursor.getColumnIndexOrThrow(UserMaster.Users.COLUMN_NAME_PASSWORD));
            userNames.add(username);
            passwords.add(password);
        }
        cursor.close();
        return userNames;
    }

    // search the given user
    public boolean readInfo(String uName, String pwd)
    {
        SQLiteDatabase db = getReadableDatabase();

        // define a projection that specifieswhich columns from the database
        // you will actually use after this query
        String[] projection = {
                UserMaster.Users._ID,
                UserMaster.Users.COLUMN_NAME_USERNAME,
                UserMaster.Users.COLUMN_NAME_PASSWORD
        };

        //Filter results WHERE "userName" = 'SLIIT USER'
        String selection = UserMaster.Users.COLUMN_NAME_USERNAME + " = ?" + " AND " + UserMaster.Users.COLUMN_NAME_PASSWORD + " = ?";
        String[] selectionArgs = {uName, pwd};

        // How you want the results sorted in the resulting cursor
        String sortOrder = UserMaster.Users.COLUMN_NAME_USERNAME + " DESC";

        Cursor cursor = db.query(
                UserMaster.Users.TABLE_NAME,           // the table to query
                projection,                 // the columns to return
                selection,               // the columns for the WHERE clause
                selectionArgs,            // the values for the WHERE clause
                null,               // don't group the rows
                null,                // don't filter by row groups
                sortOrder                  // the sort order
        );

        List userNames = new ArrayList<>();
        List passwords = new ArrayList<>();

        while(cursor.moveToNext()){
            String username = cursor.getString( cursor.getColumnIndexOrThrow(UserMaster.Users.COLUMN_NAME_USERNAME));
            String password = cursor.getString( cursor.getColumnIndexOrThrow(UserMaster.Users.COLUMN_NAME_PASSWORD));
            userNames.add(username);
            passwords.add(password);
        }
        if (cursor.getCount() == 0)
            return false;
        else
            return true;
        // cursor.close();

    }
    //This will delete a particular user from the table
    public void deleteInfo(String userName){
        SQLiteDatabase db = getReadableDatabase();
        //Define 'where' part of query
        String selection = UserMaster.Users.COLUMN_NAME_USERNAME + " LIKE ?";
        //Specify arguments n placeholder order
        String[] selectionArgs = { userName };
        //Issue SQL statement
        db.delete(UserMaster.Users.TABLE_NAME, selection, selectionArgs);

    }

    public boolean updateInfo(String userName, String password) {
        SQLiteDatabase db = getReadableDatabase();

        //New value for one column
        ContentValues values = new ContentValues();
        values.put(UserMaster.Users.COLUMN_NAME_PASSWORD, password);

        //Which row to update, based on the title
        String selection = UserMaster.Users.COLUMN_NAME_USERNAME + " LIKE ?";
        String[] selectionArgs = {userName};

        int count = db.update(
                UserMaster.Users.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );

        if(count >= 1)
            return true;
        else
            return false;
    }

}