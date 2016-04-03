package com.newsbuzz;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MoreActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String TITLE_READ_MORE ="more_title" ;
    private static final int READ_CATEGORY = 1;
    private static final int MORE_READ = 15;
    private String title;
    private int position=0;
    private TextView titleView,descriptionView,pubView;
    private ImageView logo_view;
    ViewPager mViewPager;
    ArrayList<NewsItem> list;

private MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewPager=new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        list=new ArrayList<NewsItem>();

        Intent intent=getIntent();

        if(intent!=null){
            if(intent.hasExtra(TITLE_READ_MORE)){
                title=intent.getStringExtra(TITLE_READ_MORE);
            }
            if(intent.hasExtra("int")){
                position=intent.getIntExtra("int",0);
                Log.v("pos",position+"");
                mViewPager.setCurrentItem(position,false);
                
            }
        }

        getSupportLoaderManager().initLoader(MORE_READ,null,this);

        FragmentManager fm = getSupportFragmentManager();
        adapter=new MyAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int pos, float posOffset, int posOffsetPixels) {
            }

            public void onPageSelected(int pos) {
                NewsItem item = list.get(pos);
                // title
            }
        });

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String selectionArgs[]={title};
        return new CursorLoader(this,DbContract.getNewsByCategory(),null,null,selectionArgs,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
         if(data!=null){
             if(data.moveToFirst()){
             do{

                 list.add(new NewsItem(data.getString(data.getColumnIndex(DbContract.NEWS_TABLE.TITLE)),
                         data.getString(data.getColumnIndex(DbContract.NEWS_TABLE.LINK_MORE)),
                                 data.getString(data.getColumnIndex(DbContract.NEWS_TABLE.PUBDATE)),
                                         data.getString(data.getColumnIndex(DbContract.NEWS_TABLE.DESCRIPTION)),
                                                 data.getString(data.getColumnIndex(DbContract.NEWS_TABLE.LINK_IMAGE)),
                         data.getString(data.getColumnIndex(DbContract.NEWS_TABLE.CATEGORY)),
                         data.getString(data.getColumnIndex(DbContract.NEWS_TABLE.RELATED_LINK))));

             }
             while (data.moveToNext());
                 adapter.notifyDataSetChanged();

             }
         }

        mViewPager.setCurrentItem(position,false);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
    public class MyAdapter extends FragmentStatePagerAdapter{
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return NewsFragment.newInstance(list.get(position));
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }
}
