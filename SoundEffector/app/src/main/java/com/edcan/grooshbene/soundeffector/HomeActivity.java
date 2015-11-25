package com.edcan.grooshbene.soundeffector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by GrooshBene on 2015. 11. 23..
 */
public class HomeActivity extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener {

    ListView mListView;
    Retrofit retrofit;
    ArrayList<CData> dataArr;
    ArrayAdapter<CData> mAdapter;
    SwipeRefreshLayout mBaseLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mBaseLayout = (SwipeRefreshLayout)findViewById(R.id.mBaseLayout);
        mBaseLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mListView = (ListView)findViewById(R.id.mListView);
        retrofit = new Retrofit.Builder().baseUrl("http://grooshbene.milkgun.kr").addConverterFactory(GsonConverterFactory.create()).build();
        dataArr = new ArrayList<CData>();
        mAdapter = new DataAdapter(HomeActivity.this, dataArr);
        mListView.setAdapter(mAdapter);
        mBaseLayout.setOnRefreshListener(this);
        mBaseLayout.post(new Runnable() {
            @Override
            public void run() {
                mBaseLayout.setRefreshing(true);

                mAdapter.clear();
                loadArticle();

                mBaseLayout.setRefreshing(false);
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        Log.e("itemId", item.getItemId()+"");
        switch(item.getItemId()){
            case R.id.menu_write:
                Intent intent = new Intent(HomeActivity.this, WriteActivity.class);
                startActivity(intent);
                Log.e("case1", "true");
                break;
            case R.id.menu_refresh:
                mBaseLayout.setRefreshing(true);
                loadArticle();
                mBaseLayout.setRefreshing(false);
                Log.e("case1","true");
                break;
            default:
                break;
        }
        return true;
    }

    public void initList(String article, String writer,String time){

        mAdapter.add(new CData(getApplicationContext(), article, writer,time));
        mAdapter.notifyDataSetChanged();
    }

    public void loadArticle(){
        final JSONService loadArticle = retrofit.create(JSONService.class);
        Call<List<Article>> call = loadArticle.loadArticle();
        call.enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Response<List<Article>> response) {
                Log.e("asdf", "asdf");
                if (response.code() == 200) {
                    List<Article> articles = response.body();
                    for (Article article : articles) {
                        Log.e("article", article.user_article + "." + article.user_name);
                        String[] return_temp = article.article_time.substring(0,article.article_time.length()-8).split("T");
                        String return_val = return_temp[0] + " " + return_temp[1];
                        initList(article.user_article + "", article.user_name, return_val);
                    }
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Toast.makeText(getApplicationContext(), "알수없는 에러에요..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRefresh() {
        mAdapter.clear();
        loadArticle();
        mBaseLayout.setRefreshing(false);
    }
}
