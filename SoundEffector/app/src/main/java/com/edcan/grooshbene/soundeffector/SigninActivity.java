package com.edcan.grooshbene.soundeffector;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by GrooshBene on 2015. 11. 30..
 */
public class SigninActivity extends Activity{
    EditText mIdEdit, mPwEdit, mNameEdit;
    ProgressDialog mProgressDialog;
    Button mButton;
    Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        mIdEdit = (EditText)findViewById(R.id.mIdEdit);
        mPwEdit = (EditText)findViewById(R.id.mPwEdit);
        mNameEdit = (EditText)findViewById(R.id.mNameEdit);
        mButton = (Button)findViewById(R.id.mButton);
        retrofit = new Retrofit.Builder().baseUrl("http://grooshbene.milkgun.kr").addConverterFactory(GsonConverterFactory.create()).build();

        final JSONService service = retrofit.create(JSONService.class);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<User> call = service.signin(mIdEdit.getText().toString(), mPwEdit.getText().toString(), mNameEdit.getText().toString());
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Response<User> response) {
                        if(response.code() == 200){
                            User user = response.body();
                            Log.e("Signin", "Success");
                            if(user != null){
                                Toast.makeText(getApplicationContext(),"회원가입이 완료되었습니다",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                        if(response.code()==401){
                            Toast.makeText(getApplicationContext(),"중복되는 아이디 입니다. 다시 설정해 주세요!",Toast.LENGTH_SHORT).show();
                            Log.e("Signin","Multi");
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                            Toast.makeText(getApplicationContext(),"요청을 전송할 수 없습니다.",Toast.LENGTH_SHORT).show();
                        Log.e("Signin", "Network Error!");
                    }
                });
            }
        });
    }


}
