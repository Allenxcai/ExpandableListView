package com.allenai.expandablelistview.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.allenai.expandablelistview.db.ChapterDbHelper;
import com.allenai.expandablelistview.entiy.Chapter;
import com.allenai.expandablelistview.entiy.ChapterItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:AXC 时间：2018/10/26
 * 项目名：ExpandableListView
 * 包名：com.allenai.expandablelistview.dao
 * 简述：
 */
public class ChapterDao {


    public List<Chapter> loadFromDb(Context context) {

        ChapterDbHelper dbHelper = ChapterDbHelper.getInstance(context);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        List<Chapter> chapters = new ArrayList<>();

        Chapter chapter = null;
        Cursor  cursor  = db.rawQuery("select * from " + Chapter.TABLE_NAME, null);

        //get Chapter
        while (cursor.moveToNext()) {
            chapter = new Chapter();
            int    id   = cursor.getInt(cursor.getColumnIndex(Chapter.COL_ID));
            String name = cursor.getString(cursor.getColumnIndex(Chapter.COL_NAME));
            chapter.setId(id);
            chapter.setName(name);
            chapters.add(chapter);
        }

        cursor.close();

        //get Chapter Child
        ChapterItem chapterItem = null;
        for (Chapter tmpChapter : chapters) {

            int pid = tmpChapter.getId();

            cursor = db.rawQuery("select * from " + ChapterItem.TABLE_NAME + " where " +
                    ChapterItem.COL_PID + " =? ", new String[]{pid + ""});

            while (cursor.moveToNext()) {
                chapterItem = new ChapterItem();
                int    id   = cursor.getInt(cursor.getColumnIndex(ChapterItem.COL_ID));
                String name = cursor.getString(cursor.getColumnIndex(ChapterItem.COL_NAME));
                chapterItem.setPid(pid);
                chapterItem.setId(id);
                chapterItem.setName(name);
                tmpChapter.addChild(chapterItem);

            }
            cursor.close();
        }

        return chapters;

    }

    public void insertToDb(Context context, List<Chapter> chapters) {
        if (chapters ==null || chapters.isEmpty()) {
            return;
        }

        ChapterDbHelper dbHelper = ChapterDbHelper.getInstance(context);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.beginTransaction();

        ContentValues cv = null;

        for (Chapter chapter : chapters) {
            cv = new ContentValues();
            cv.put(Chapter.COL_ID, chapter.getId());
            cv.put(Chapter.COL_NAME, chapter.getName());
            db.insertWithOnConflict(Chapter.TABLE_NAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            List<ChapterItem> chapterItems = chapter.getChildren();

            for (ChapterItem chapterItem : chapterItems) {
                cv = new ContentValues();
                cv.put(ChapterItem.COL_ID, chapterItem.getId());
                cv.put(ChapterItem.COL_NAME, chapterItem.getName());
                cv.put(ChapterItem.COL_PID, chapter.getId());
                db.insertWithOnConflict(ChapterItem.TABLE_NAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

            }


        }
        db.setTransactionSuccessful();
        db.endTransaction();


    }
}
