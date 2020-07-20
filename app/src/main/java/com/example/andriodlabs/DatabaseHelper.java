package com.example.andriodlabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "MessagesDB";
    private static final String DB_TABLE = "Messages_Table";

    //columns
    private static final String COL_MESSAGE = "Message";
    private static final String COL_ISSEND = "IsSend";
    private static final String COL_MESSAGEID = "MessageID";

    //queries
    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE + " (" + COL_MESSAGEID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_MESSAGE + " TEXT, " + COL_ISSEND + " BIT);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        onCreate(db);
    }

    public void onDowngrada() {

    }

//    public void printCursor( Cursor c) {
//
//        int column = c.getColumnCount();
//
//        String [] columnName = c.getColumnNames();
//        for (String printcolName: columnName){
//             Log.v("Column Names", printcolName);
//        }
//        int row = c.getCount();
//
//    }

    public boolean insertData(String message, boolean isSend) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_MESSAGE, message);
        if (isSend)
            contentValues.put(COL_ISSEND, 0);
        else
            contentValues.put(COL_ISSEND, 1);

        long result = db.insert(DB_TABLE, null, contentValues);

        return result != -1; //if result = -1 data doesn't insert
    }

    //view data
    public Cursor viewData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from "+DB_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToNext();

        Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(cursor));
        return cursor;
    }
}