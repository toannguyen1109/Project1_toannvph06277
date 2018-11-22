package com.fpoly.dell.project1.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fpoly.dell.project1.dao.ChungLoaiDao;
import com.fpoly.dell.project1.dao.VatNuoiDao;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dbBookManager";
    private static final int VESION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VESION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ChungLoaiDao.SQL_CHUNGLOAI);
        db.execSQL(VatNuoiDao.SQL_VATNUOI);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("Drop table if exists "+ChungLoaiDao.TABLE_NAME);
        db.execSQL("Drop table if exists "+VatNuoiDao.TABLE_NAME);

    }
}
