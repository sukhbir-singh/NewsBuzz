package com.newsbuzz;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import net.steamcrafted.loadtoast.LoadToast;

import java.io.ByteArrayOutputStream;
import java.util.Hashtable;
import java.util.Map;

public class EnterActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText title, description;
    private TextInputLayout titleTextInputLayout, descriptionTextInputLayout;
    private boolean isTitle, isDescription;
    private Button submit;
    private LoadToast loadToast;
    private String encodedString;
    private ImageButton imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadToast=new LoadToast(this);
        loadToast.setTranslationY((int) Utils.convertDpToPixel(70));
        imageView= (ImageButton) findViewById(R.id.imageView);
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
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createchooser();
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
            hMap.put("image",encodedString);
            return hMap;
        }
    };
    stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    MySingleton.getInstance(MyApplication.getAppContext()).addToRequestQueue(stringRequest);
}

    private String getUrl() {
        return "http://www.newsbuzz.890m.com/mobileupload.php";
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(filePath, filePathColumn, null, null, null);
            c.moveToFirst();
            String imgDecodableString = c.getString(c.getColumnIndex(filePathColumn[0]));
            c.close();
            Bitmap bitmap = BitmapFactory.decodeFile(imgDecodableString);
            imageView.setImageBitmap(bitmap);
            encodedString=decodeImage(bitmap);
        }
    }

    private String decodeImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        Log.d("DECODE IMAGE",encodedImage);
        return encodedImage;
    }

    private void createchooser(){
        Intent intent=new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "CHOOSE PHOTO"), PICK_IMAGE_REQUEST);
    }
}
