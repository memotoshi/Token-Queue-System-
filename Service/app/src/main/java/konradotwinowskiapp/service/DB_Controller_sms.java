package konradotwinowskiapp.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Konrad on 2017-07-13.
 */

public class DB_Controller_sms extends SQLiteOpenHelper {

    private static final String TAG = "DB_Controller_sms";


    public DB_Controller_sms(Context context) {
        super(context, "SMSES", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE SMSES( ID INTEGER PRIMARY KEY AUTOINCREMENT, SMSNAME TEXT UNIQUE, NUMBERNAME TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS SMSES");
        onCreate(sqLiteDatabase);
    }

    public void insert_sms(String smsname, String numbername) {
        //SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("SMSNAME", smsname);
        contentValues.put("NUMBERNAME", numbername);
        this.getWritableDatabase().insertOrThrow("SMSES", "", contentValues);
    }

    public void delete_sms(String numbername){
        this.getWritableDatabase().delete("SMSES", "NUMBERNAME='" + numbername + "'", null);
    }

    public void delete_all() {
        SQLiteDatabase db = this.getWritableDatabase();
        //db.delete(TABLE_NAME, null, null);
        db.execSQL("DELETE FROM SMSES");
        db.close();
    }
}
