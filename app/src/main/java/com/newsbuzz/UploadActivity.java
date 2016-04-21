package com.newsbuzz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import net.steamcrafted.loadtoast.LoadToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UploadActivity extends AppCompatActivity {
    private static final String LIST ="list" ;
    private RecyclerView recyclerView;
private  UploadAdapter adapter;
    private LoadToast loadToast;
    private ArrayList<Upload_item> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        loadToast=new LoadToast(this);
        loadToast.setTranslationY((int) Utils.convertDpToPixel(70));
        list=new ArrayList<>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Uploaded News");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView= (RecyclerView) findViewById(R.id.list_upload);
        adapter=new UploadAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if(savedInstanceState!=null){
            list=savedInstanceState.getParcelableArrayList(LIST);
            adapter.refresh(list);
        }
        else {
            if(new Connection(this).isInternet()){
                loadToast.show();
                sendRequest(getUrl());
            }
            else {
                Toast.makeText(this,"No Internet Connection",Toast.LENGTH_LONG).show();
            }
        }


    }
    private void sendRequest(String url){
        final JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray=response.getJSONArray("feed");
                    for(int i=0;i<jsonArray.length();i++){
                        String title="",description="",image="";
                        JSONObject feedObject=jsonArray.getJSONObject(i);
                        if(feedObject.has("title")&&!feedObject.isNull("title")){
                            title=feedObject.getString("title");
                        }
                        if(feedObject.has("description")&&!feedObject.isNull("description")){
                            description=feedObject.getString("description");
                        }
                        if(feedObject.has("image")&&!feedObject.isNull("image")){
                            image=feedObject.getString("image");
                        }
                  list.add(new Upload_item(title,description,image));
                    }
                    adapter.refresh(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                loadToast.success();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadToast.error();
                error.printStackTrace();
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(UploadActivity.this).addToRequestQueue(jsonObjectRequest);
    }
private String getUrl(){
    return "http://www.newsbuzz.890m.com/ShowAllJson.php";
}

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(LIST,list);
    }
}
