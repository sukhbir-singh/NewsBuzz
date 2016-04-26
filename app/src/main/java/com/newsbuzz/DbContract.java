package com.newsbuzz;


import android.net.Uri;
import android.provider.BaseColumns;

public class DbContract {
    public static final String AUTHORITY = "com.newsbuzz";
    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);
    public  static final  String TABLE_NEWS="news";
    public static final String INSERT ="insert" ;
    public static final String READ ="read" ;
    public static  class  NEWS_TABLE implements BaseColumns{
        public static final String TITLE="title";
        public static final String LINK_MORE="link_more";
        public static final String PUBDATE="created_on";
        public static final String DESCRIPTION="description";
        public static final String LINK_IMAGE="link_image";
        public static final String CATEGORY="category";
        public static final String RELATED_LINK="relatedLinks";
    }
    public static Uri getNewsByCategory(){
        return BASE_URI.buildUpon().appendPath(READ).build();
    }
    public static Uri insertNews(){
        return BASE_URI.buildUpon().appendPath(INSERT).build();
    }
}
