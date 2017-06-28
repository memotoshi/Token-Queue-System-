package konradotwinowskiapp.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Konrad on 2017-06-26.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Cash.db";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_QUERY = "CREATE TABLE " + KasaDb.NewKasaInfo.KASA_NAME + "TEXT,";
//    public static final String TABLE_NAME = "cash_table";
//    public static final String COL_1 = "ID";
//    public static final String COL_2 = "KASA";
//    public static final String COL_3 = "CURRENTNUMBER";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.e("DATABASE OPERATIONS", "Database created!!!!!!!!!!!");
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        Log.e("DATABASE OPERATIONS", "Table created!!!!!!!!!!!!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public void insertData (String kasa, SQLiteDatabase db) { //String currentnumber
        //SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KasaDb.NewKasaInfo.KASA_NAME, kasa);
        db.insert(KasaDb.NewKasaInfo.TABLE_NAME, null, contentValues);
        Log.e("DATABASE OPERATIONS", "Row inserted!!!!!!!!!!!!");
        //contentValues.put(COL_3, currentnumber);
//        long result = db.insert(TABLE_NAME, null, contentValues);
//        if(result == -1)
//            return false;
//        else
//            return true;
    }

//    public Cursor getAllData(SQLiteDatabase db) {
//        Cursor cursor;
//        String[] projections = {KasaDb.NewKasaInfo.KASA_NAME};
//        cursor = db.query(KasaDb.NewKasaInfo.TABLE_NAME, projections, null, null, null, null, null);
//        return cursor;
//    }
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + KasaDb.NewKasaInfo.TABLE_NAME, null);
        return res;
    }
}
