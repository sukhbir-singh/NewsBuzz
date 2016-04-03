package com.newsbuzz;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.unbescape.html.HtmlEscape;

public class MoreActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String TITLE_READ_MORE ="more_title" ;
    private static final int MORE_READ = 15;
    private String title;
    private TextView titleView,descriptionView,pubView;
    private ImageView logo_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        Intent intent=getIntent();

        if(intent!=null){
            if(intent.hasExtra(TITLE_READ_MORE)){
                title=intent.getStringExtra(TITLE_READ_MORE);
            }
        }
        titleView= (TextView) findViewById(R.id.title_more_activity);
        descriptionView= (TextView) findViewById(R.id.description_more_activity);
        pubView= (TextView) findViewById(R.id.pubDate_more_activity);
        logo_view= (ImageView) findViewById(R.id.image_more_activity);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            logo_view.setTransitionName(getString(R.string.transitionName));
        }

        getSupportLoaderManager().initLoader(MORE_READ,null,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String selectionArgs[]={title};
        return new CursorLoader(this,DbContract.getMoreNews(),null,null,selectionArgs,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
         if(data!=null){
             if(data.moveToFirst()){
               titleView.setText(data.getString(data.getColumnIndex(DbContract.NEWS_TABLE.TITLE)));
                 descriptionView.setText(HtmlEscape.unescapeHtml(data.getString(data.getColumnIndex(DbContract.NEWS_TABLE.DESCRIPTION))));
                 pubView.setText(data.getString(data.getColumnIndex(DbContract.NEWS_TABLE.PUBDATE)));

               Glide.with(MoreActivity.this).load(data.getString(data.getColumnIndex(DbContract.NEWS_TABLE.LINK_IMAGE))).error(R.drawable.ic_error).into(logo_view);
             }
         }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
