package com.newsbuzz;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import net.steamcrafted.loadtoast.LoadToast;

import java.util.Hashtable;
import java.util.Map;

public class EnterActivity extends AppCompatActivity {
    private EditText title, description;
    private TextInputLayout titleTextInputLayout, descriptionTextInputLayout;
    private boolean isTitle, isDescription;
    private Button submit;
    private LoadToast loadToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadToast=new LoadToast(this);
        submit= (Button) findViewById(R.id.submit_enter);
        title = (EditText) findViewById(R.id.title_edittext);
        description = (EditText) findViewById(R.id.description_edittext);
        titleTextInputLayout = (TextInputLayout) findViewById(R.id.title_textinputlayout);
        descriptionTextInputLayout = (TextInputLayout) findViewById(R.id.description_textinputlayout);
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!title.getText().toString().isEmpty() && title.getText().toString().length() != 0) {
                    isTitle = true;
                    titleTextInputLayout.setErrorEnabled(false);
                } else {
                    isTitle = false;
                    titleTextInputLayout.setError("Please Enter The Title");
                    titleTextInputLayout.setErrorEnabled(true);
                }
            }
        });
        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!description.getText().toString().isEmpty() && description.getText().toString().length() != 0) {
                    isDescription = false;
                    descriptionTextInputLayout.setErrorEnabled(false);
                } else {
                    isDescription = true;
                    descriptionTextInputLayout.setErrorEnabled(true);
                    descriptionTextInputLayout.setError("Please Enter The Description");
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(new Connection(EnterActivity.this).isInternet()){
                    if(!isDescription&&isTitle){
                        submit.setEnabled(false);
loadToast.show();
                        sendRequest(title.getText().toString(),description.getText().toString());
                    }
                    else {
                        Toast.makeText(EnterActivity.this,"Please Enter the Required Field",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(EnterActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
private  void sendRequest(final String title, final String description){
    StringRequest stringRequest=new StringRequest(Request.Method.POST,getUrl(),new Response.Listener<String>() {
        @Override
        public void onResponse(String s) {
            submit.setEnabled(true);
            loadToast.success();
            Toast.makeText(EnterActivity.this,"UPLOADED SUCCESSFULLY",Toast.LENGTH_SHORT).show();
        }
    },new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            submit.setEnabled(true);
            loadToast.error();
            Toast.makeText(EnterActivity.this,"Error Occur Please Try Again",Toast.LENGTH_SHORT).show();
        }
    }){
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String,String> hMap=new Hashtable<>();
           hMap.put("title",title);
            hMap.put("description",description);
            return hMap;
        }
    };
    stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    MySingleton.getInstance(MyApplication.getAppContext()).addToRequestQueue(stringRequest);
}

    private String getUrl() {
        return "http://www.newsbuzz.16mb.com/mobileupload.php";
    }
}
