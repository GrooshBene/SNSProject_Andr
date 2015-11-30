package com.edcan.grooshbene.soundeffector;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by GrooshBeneMac on 2015-11-09.
 */
public class MainActivity extends ActionBarActivity {

    Button mButton;
    LinearLayout mTextView;
    ProgressDialog mProgressDialog;
    Retrofit retrofit;
    EditText mIdEdit, mPwEdit;
    SharedPreferences pref;
    SharedPreferences.Editor edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (LinearLayout)findViewById(R.id.mTextView);
        mButton = (Button)findViewById(R.id.mButton);
        mProgressDialog = new ProgressDialog(MainActivity.this);
        mIdEdit = (EditText)findViewById(R.id.mIdEdit);
        mPwEdit = (EditText)findViewById(R.id.mPwEdit);

        retrofit = new Retrofit.Builder().baseUrl("http://grooshbene.milkgun.kr").addConverterFactory(GsonConverterFactory.create()).build();

        final JSONService service = retrofit.create(JSONService.class);

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SigninActivity.class);
                startActivity(intent);
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog = new ProgressDialog( MainActivity.this );
                mProgressDialog.setMessage("잠시만 기다려주세요.....");
                mProgressDialog.show();

                final Call<User> call = service.login(mIdEdit.getText().toString(),mPwEdit.getText().toString());
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Response<User> response) {
                        mProgressDialog.dismiss();
                        if(response.code()==200){
                            User user = response.body();
                            Log.e("asdf",user.user_name);
//                            List<User.Article> articles = response.body().article;
//                            for(User.Article article : articles){
//                                Log.e("sex", article.title);
//                            }
                            if(user != null){
                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                savePreferences(user.user_name);
                                startActivity(intent);
                                finish();
                            }
                        }
                        else{
                            mProgressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"아이디나 비밀번호를 확인해 주세요!",Toast.LENGTH_SHORT).show();

                            Log.e("asdf", "error");
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        mProgressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"요청을 전송할 수 없습니다.",Toast.LENGTH_SHORT).show();
                        Log.e("Error", "Server Error!");
                        Log.e("ErrorMessage", String.valueOf(t));
                    }
                });
            }
        });
    }

    public void getPreferences(){
        pref = getSharedPreferences("pref", 1);
        pref.getString("user_name", "");
    }

    public void savePreferences(String user_name){
        pref = getSharedPreferences("pref", 1);
        edit = pref.edit();
        edit.putString("user_name", user_name);
        edit.commit();
    }

    public void removePreferences(){
        pref = getSharedPreferences("pref", 1);
        edit = pref.edit();
        edit.remove("user_name");
        edit.commit();
    }
}