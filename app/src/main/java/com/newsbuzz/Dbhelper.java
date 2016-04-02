package com.newsbuzz;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.newsbuzz.DbContract.NEWS_TABLE.CATEGORY;
import static com.newsbuzz.DbContract.NEWS_TABLE.DESCRIPTION;
import static com.newsbuzz.DbContract.NEWS_TABLE.LINK_IMAGE;
import static com.newsbuzz.DbContract.NEWS_TABLE.LINK_MORE;
import static com.newsbuzz.DbContract.NEWS_TABLE.PUBDATE;
import static com.newsbuzz.DbContract.NEWS_TABLE.RELATED_LINK;
import static com.newsbuzz.DbContract.NEWS_TABLE.TITLE;
import static com.newsbuzz.DbContract.NEWS_TABLE._ID;
import static com.newsbuzz.DbContract.TABLE_NEWS;

public class Dbhelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "News";
    private static final int DATABASE_VERSION = 7;
    private final String CREATE_TABLE = "create table " + TABLE_NEWS + "( " + _ID + " integer auto_increment primary key , " + TITLE + " text not null Unique, " + LINK_MORE + " text Unique," + PUBDATE + " text not null Unique ," + DESCRIPTION + " text not null Unique," + CATEGORY + " text not null," + LINK_IMAGE + " text not null Unique," + RELATED_LINK + " text Unique);";

    public Dbhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_NEWS);
        onCreate(sqLiteDatabase);
    }
}
