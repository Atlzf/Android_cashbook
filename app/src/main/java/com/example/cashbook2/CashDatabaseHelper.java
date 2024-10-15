package com.example.cashbook2;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;
import android.app.Activity;

public class CashDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cash.db";
    private static final String TABLE_NAME = "cash";
    private static final int DATABASE_VERSION = 2; // 增加数据库版本号

    public String getTableName() {
        return TABLE_NAME;
    }
    public CashDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id TEXT, " + // 添加user_id列
                "date TEXT, " +
                "name TEXT, " +
                "type TEXT, " +
                "amount REAL, " +
                "description TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public Cursor getAllData(String userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where user_id = ?", new String[]{userId});
        return res;
    }

    // 获取指定用户的总收入
    public float getTotalIncome(String userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(amount) AS total FROM " + TABLE_NAME + " WHERE user_id = ? AND type = '收入'", new String[]{userId});
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("total");
            if (columnIndex != -1) {
                return cursor.getFloat(columnIndex);
            } else {
                // "total" 列不存在，返回 0 或者抛出一个异常
                return 0;
            }
        } else {
            return 0;
        }
    }

            // 获取指定用户的总支出
            public float getTotalExpense(String userId) {
                SQLiteDatabase db = this.getWritableDatabase();
                Cursor cursor = db.rawQuery("SELECT SUM(amount) AS total FROM " + TABLE_NAME + " WHERE user_id = ? AND type = '支出'", new String[]{userId});
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex("total");
                    if (columnIndex >= 0) {
                        return cursor.getFloat(columnIndex);
                    } else {
                        // "total" 列不存在，返回 0 或者抛出一个异常
                        return 0;
                    }
                } else {
                    return 0;
        }
    }
}