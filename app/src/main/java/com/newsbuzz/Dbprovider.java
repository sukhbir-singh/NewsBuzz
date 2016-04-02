package com.newsbuzz;


import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

public class Dbprovider extends ContentProvider {
    private static final int INSERT_DATA = 1;
    private static final int READ_DATA = 2;
    private Dbhelper dbhelper;
    private SQLiteDatabase sqLiteDatabase;
    private static UriMatcher uriMatcher = uriMatcher();

    private static UriMatcher uriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(DbContract.AUTHORITY, DbContract.INSERT, INSERT_DATA);
        uriMatcher.addURI(DbContract.AUTHORITY, DbContract.READ, READ_DATA);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        dbhelper = new Dbhelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
      sqLiteDatabase=dbhelper.getReadableDatabase();
       Cursor cursor=null;
       if(uriMatcher.match(uri)==READ_DATA){
           cursor=sqLiteDatabase.query(DbContract.TABLE_NEWS,null,null,null,null,null,null,null);
           if(cursor!=null){
               Log.d("read","read");
               cursor.setNotificationUri(getContext().getContentResolver(),uri);
               return  cursor;
           }
       }
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+DbContract.BASE_URI;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        sqLiteDatabase=dbhelper.getWritableDatabase();
        long id=0;
        if(uriMatcher.match(uri)==INSERT_DATA){
            id = sqLiteDatabase.insert(DbContract.TABLE_NEWS, null, contentValues);
            if (id > 0) {
                Log.d("insert","insert");
                Uri _uri = ContentUris.withAppendedId(uri, id);
                getContext().getContentResolver().notifyChange(_uri, null);
                return _uri;
            }
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
