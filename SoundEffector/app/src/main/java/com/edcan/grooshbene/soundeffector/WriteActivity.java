package com.edcan.grooshbene.soundeffector;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.Date;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


/**
 * Created by GrooshBene on 2015. 11. 25..
 */
public class WriteActivity extends Activity {
    FloatingActionButton btn_add;
    EditText mEditText;
    RelativeLayout mLayout;
    SharedPreferences pref;
    SharedPreferences.Editor edit;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        setWindowManager();
        mEditText = (EditText)findViewById(R.id.mEditText);
        btn_add = (FloatingActionButton) findViewById(R.id.btn_add);
        retrofit = new Retrofit.Builder().baseUrl("http://grooshbene.milkgun.kr").addConverterFactory(GsonConverterFactory.create()).build();
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = String.valueOf(new Date());
                String user_name = getPreferences();
                uploadArticle(mEditText.getText().toString(), date,user_name);
            }
        });
   }

    public void setWindowManager() {
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.width = 900;
        lpWindow.height = 750;
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
    }

    public String getPreferences(){
        pref = getSharedPreferences("pref", MODE_PRIVATE);
        String ret = pref.getString("user_name", "");
        return ret;
    }

    public void uploadArticle(String text, String date, String user_name){
        final JSONService makeArticle = retrofit.create(JSONService.class);
        Call<Article> call = makeArticle.makeArticle(text, user_name, date);
        call.enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Response<Article> response) {
                if(response.code() == 200){
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"게시글을 올리지 못했어요!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplicationContext(),"게시글을 올리지 못했어요!", Toast.LENGTH_SHORT).show();

            }
        });

    }

}
