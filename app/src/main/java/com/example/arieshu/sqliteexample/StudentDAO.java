package com.example.arieshu.sqliteexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by foint on 2017/6/25.
 */

// student data access object
// add insert, update, delete, getAll, get

// 資料功能類別
public class StudentDAO {
    // 表格名稱
    public static final String TABLE_NAME = "student";

    // 編號表格欄位名稱，固定不變
    public static final String KEY_ID = "_id";

    // 其它表格欄位名稱
    public static final String NAME_COLUMN  = "name";
    public static final String SEX_COLUMN   = "sex";
    public static final String SCORE_COLUMN = "score";

    // 使用上面宣告的變數建立表格的SQL指令
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME_COLUMN  + " TEXT, " +
                    SEX_COLUMN   + " TEXT, " +
                    SCORE_COLUMN + " INTEGER)";

    // 資料庫物件
    private SQLiteDatabase db;

    // 建構子，一般的應用都不需要修改
    public StudentDAO(Context context) {
        db = MyDBHelper.getDatabase(context);
    }

    // 關閉資料庫，一般的應用都不需要修改
    public void close() {
        db.close();
    }

    // 新增參數指定的物件
    public StudentProfile insert(StudentProfile stuPro) {
        // 建立準備新增資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的新增資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(NAME_COLUMN,  stuPro.getName());
        cv.put(SEX_COLUMN,   stuPro.getSex());
        cv.put(SCORE_COLUMN, stuPro.getScore());

        // 新增一筆資料並取得編號
        // 第一個參數是表格名稱
        // 第二個參數是沒有指定欄位值的預設值
        // 第三個參數是包裝新增資料的ContentValues物件
        long nID = db.insert(TABLE_NAME, null, cv);

        // 設定編號
        stuPro.setID(nID);
        // 回傳結果
        return stuPro;
    }

    // 修改參數指定的物件
    public boolean update(StudentProfile stuPro) {
        // 建立準備修改資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的修改資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(NAME_COLUMN,  stuPro.getName());
        cv.put(SEX_COLUMN,   stuPro.getSex());
        cv.put(SCORE_COLUMN, stuPro.getScore());

        // 設定修改資料的條件為編號
        // 格式為「欄位名稱＝資料」
        String where = KEY_ID + "=" + stuPro.getID();

        // 執行修改資料並回傳修改的資料數量是否成功
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    // 刪除參數指定編號的資料
    public boolean delete(long id){
        // 設定條件為編號，格式為「欄位名稱=資料」
        String where = KEY_ID + "=" + id;
        // 刪除指定編號資料並回傳刪除是否成功
        return db.delete(TABLE_NAME, where , null) > 0;
    }

    // 讀取所有記事資料
    public List<StudentProfile> getAll() {
        List<StudentProfile> result = new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    // 取得指定編號的資料物件
    public StudentProfile get(long id) {
        // 準備回傳結果用的物件
        StudentProfile item = null;
        // 使用編號為查詢條件
        String where = KEY_ID + "=" + id;
        // 執行查詢
        Cursor cursor = db.query(TABLE_NAME, null, where, null, null, null, null, null);

        // 如果有查詢結果
        if (cursor.moveToFirst()) {
            // 讀取包裝一筆資料的物件
            item = getRecord(cursor);
        }

        // 關閉Cursor物件
        cursor.close();
        // 回傳結果
        return item;
    }

    // 把Cursor目前的資料包裝為物件
    public StudentProfile getRecord(Cursor cursor) {
        // 準備回傳結果用的物件
        StudentProfile result = new StudentProfile();

        result.setID(cursor.getLong(0));
        result.setName(cursor.getString(1));
        result.setSex(cursor.getString(2));
        result.setScore(cursor.getInt(3));

        // 回傳結果
        return result;
    }

    // 取得資料數量
    public int getCount() {
        int result = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);

        if (cursor.moveToNext()) {
            result = cursor.getInt(0);
        }

        return result;
    }

    // 建立範例資料
    public void sample() {
        StudentProfile stuDAO1 = new StudentProfile( 0, "Wolf",   "Male",   100 );
        StudentProfile stuDAO2 = new StudentProfile( 1, "Phoebe", "Female", 99  );
        StudentProfile stuDAO3 = new StudentProfile( 2, "Aries",  "Male",   89  );

        insert(stuDAO1);
        insert(stuDAO2);
        insert(stuDAO3);
    }

}
