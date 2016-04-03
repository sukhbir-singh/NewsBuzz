package com.newsbuzz;


import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

public class DataObserver extends ContentObserver{
    public DataObserver(Handler handler) {
        super(handler);
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
        Log.d("change","change");
    }
}
