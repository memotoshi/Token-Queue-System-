package konradotwinowskiapp.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Konrad on 2017-07-08.
 */

public class DB_Controller extends SQLiteOpenHelper {

    private static final String TAG = "DB_Controller";

//    private static final String TABLE_NAME = "CASHES";
    private static final String COL1 = "ID";
//    private static final String COL2 = "CASHNAME";
//    private static final String COL3 = "NUMBERNAME";


    public DB_Controller(Context context) {
        super(context, "CASHES", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 + " TEXT UNIQUE, " + COL3 + " TEXT)";
        sqLiteDatabase.execSQL("CREATE TABLE CASHES( ID INTEGER PRIMARY KEY AUTOINCREMENT, CASHNAME TEXT UNIQUE, NUMBERNAME TEXT);");
        //sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS CASHES");
        onCreate(sqLiteDatabase);
    }

    public void insert_cash(String cashname, String numbername){
        //SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("CASHNAME", cashname);
        contentValues.put("NUMBERNAME", numbername);
        this.getWritableDatabase().insertOrThrow("CASHES", "", contentValues);
        //Log.d(TAG, "addData: Adding " + cashname + numbername + " to " + TABLE_NAME);
        //long result = db.insert(TABLE_NAME, null, contentValues);

//        if (result == -1) {
//            return false;
//        } else {
//            return true;
//        }
       // this.getWritableDatabase().insertOrThrow("CASHES", "", contentValues);
    }

    public void delete_cash(String cashname){
        this.getWritableDatabase().delete("CASHES", "CASHNAME='" + cashname + "'", null);
    }

    public void update_cash(String old_numbername, String new_numbername){
        this.getWritableDatabase().execSQL("UPDATE CASHES SET NUMBERNAME='" + new_numbername + "' WHERE CASHNAME='" + old_numbername + "'");
    }

    public void list_all_cashes(TextView textView, TextView textView2){
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM CASHES", null);
        textView.setText("");
        textView2.setText("");
        while (cursor.moveToNext()){
            //textView.append(cursor.getString(1) + " " + cursor.getString(2) + "\n");
            textView.append(cursor.getString(1) + " " + "\n");
            textView2.append(cursor.getString(2) + " " + "\n");
        }
    }

    public void delete_all() {
        SQLiteDatabase db = this.getWritableDatabase();
        //db.delete(TABLE_NAME, null, null);
        db.execSQL("DELETE FROM CASHES");
        db.close();
        //db.delete(TABLE_NAME, null, null);
        //this.getWritableDatabase().delete(TABLE_NAME, COL1, COL2, null);
    }

    public long getCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long cnt  = DatabaseUtils.queryNumEntries(db, "CASHES");
        db.close();
        return cnt;
    }
}
