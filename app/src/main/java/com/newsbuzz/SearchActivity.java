package com.newsbuzz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import net.steamcrafted.loadtoast.LoadToast;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
private RecyclerView recyclerView;
private  CategoryAdapter adapter;
private LoadToast loadToast;
private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        loadToast=new LoadToast(this);
        loadToast.setTranslationY((int) Utils.convertDpToPixel(70));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_search);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        searchView= (SearchView) findViewById(R.id.search_news);
        recyclerView= (RecyclerView) findViewById(R.id.list_category);
        adapter=new CategoryAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                if(new Connection(SearchActivity.this).isInternet()){
                    loadToast.show();
                sendRequest(getUrl(query));}
                else {
                    Toast.makeText(SearchActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    private void sendRequest(String url) {
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadToast.success();
                RssExtractor rssExtractor = new RssExtractor(response);
                ArrayList<NewsItem> list = rssExtractor.getNewsItems();
                Log.d("title",""+list.get(0).title);
                if(list.size()>0){
                    adapter.refresh(list);
                }
                else {
                    Toast.makeText(SearchActivity.this,"No News Found",Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                loadToast.error();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(MyApplication.getAppContext()).addToRequestQueue(stringRequest);
    }
    private String getUrl(String query){
        try {
            URI uri=new URI("https","news.google.co.in","/news/feeds","pz=1&cf=all&ned=in&hl=en&q="+query+"&output=rss",null);
            Log.d("url",uri.toASCIIString());
            return uri.toASCIIString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return "";
    }
}
