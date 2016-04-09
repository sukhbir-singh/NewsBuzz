package com.newsbuzz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String CATEGORY_NAME ="Category" ;
    private RecyclerView recyclerView;
    private ArrayList<ItemStaggered> list;
    LinearLayoutManager staggeredGridLayoutManager;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pref = getSharedPreferences(Preferences.PREF_NAME, 0);
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,EnterActivity.class));
            }
        });

        recyclerView =(RecyclerView)findViewById(R.id.staggered_recycler);


        list=new ArrayList<>();

        list.add(new ItemStaggered("Top Stories",R.drawable.topstories));
        list.add(new ItemStaggered("Uploaded Stories",R.drawable.upload));
        if(pref.getBoolean(Preferences.KEY_USER_ID1,true)){
            list.add(new ItemStaggered("Entertainment",R.drawable.entertainment));
        }

        if(pref.getBoolean(Preferences.KEY_USER_ID2,true)){
            list.add(new ItemStaggered("Technology",R.drawable.development));
        }

        if(pref.getBoolean(Preferences.KEY_USER_ID3,true)){
            list.add(new ItemStaggered("Business",R.drawable.business));
        }

        if(pref.getBoolean(Preferences.KEY_USER_ID4,true)){
            list.add(new ItemStaggered("Sports",R.drawable.sports));
        }

        if(pref.getBoolean(Preferences.KEY_USER_ID5,true)){
            list.add(new ItemStaggered("Health",R.drawable.medical));
        }


        staggeredGridLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        StaggeredRecyclerViewAdapter adapter = new StaggeredRecyclerViewAdapter(MainActivity.this, list);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
             if(position==1){
                 Intent i = new Intent(MainActivity.this, UploadActivity.class);
                 startActivity(i);
             }
                else {
                 Intent i = new Intent(MainActivity.this, CategoryActivity.class);
                 i.putExtra(CATEGORY_NAME, list.get(position).getTitle());
                 startActivity(i);
             }
            }
        }));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(MainActivity.this,Settings.class);
            startActivity(i);
            return true;
        }
        else if (id == R.id.aboutus_menu) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(String.format("%1$s", getString(R.string.app_name)));
            builder.setMessage(getResources().getText(R.string.aboutus_text));
            builder.setPositiveButton("OK", null);
            //builder.setIcon(R.mipmap.nimbus16);
            AlertDialog welcomeAlert = builder.create();
            welcomeAlert.show();
            ((TextView) welcomeAlert.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());

            return true;
        }
        else if (id == R.id.openSource_licences) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(String.format("%1$s", getString(R.string.openSoure_name)));
            builder.setMessage(getResources().getText(R.string.openSourceLicence_text));
            builder.setPositiveButton("OK", null);
            //builder.setIcon(R.mipmap.nimbus16);
            AlertDialog welcomeAlert = builder.create();
            welcomeAlert.show();
            ((TextView) welcomeAlert.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());

            return true;
        }
        else if(id==R.id.search_news){
            startActivity(new Intent(MainActivity.this,SearchActivity.class));
            return  true;
        }



        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
