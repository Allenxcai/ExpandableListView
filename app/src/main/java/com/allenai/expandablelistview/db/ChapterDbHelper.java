package com.allenai.expandablelistview.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.allenai.expandablelistview.entiy.Chapter;
import com.allenai.expandablelistview.entiy.ChapterItem;

/**
 * @Author:AXC 时间：2018/10/26
 * 项目名：ExpandableListView
 * 包名：com.allenai.expandablelistview.db
 * 简述：
 */
public class ChapterDbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "db_chapter.db";
    public static final int    VERSION = 1;

    private static ChapterDbHelper sInstance;

    public ChapterDbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    public static synchronized ChapterDbHelper getInstance(Context context) {
        if (sInstance == null) {

            sInstance = new ChapterDbHelper(context.getApplicationContext());

        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS " + Chapter.TABLE_NAME + " ("
                + Chapter.COL_ID + " INTEGER PRIMARY KEY, "
                + Chapter.COL_NAME + " VARCHAR"
                + ")"
        );
        db.execSQL("CREATE TABLE IF NOT EXISTS " + ChapterItem.TABLE_NAME + " ("
                + ChapterItem.COL_ID + " INTEGER PRIMARY KEY, "
                + ChapterItem.COL_NAME + " VARCHAR,"
                + ChapterItem.COL_PID + " INTEGER"
                + ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
