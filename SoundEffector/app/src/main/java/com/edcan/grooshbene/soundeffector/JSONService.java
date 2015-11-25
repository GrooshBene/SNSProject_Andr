package com.edcan.grooshbene.soundeffector;

import org.json.JSONObject;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by GrooshBeneMac on 2015-11-19.
 */
public interface JSONService {
    @FormUrlEncoded
    @POST("/login")
    Call<User> login(@Field("id") String username, @Field("pw") String password);

    @POST("/articlelist")
    Call<List<Article>> loadArticle();

    @FormUrlEncoded
    @POST("/articlemake")
    Call<Article> makeArticle(@Field("text") String text, @Field("user_name") String Username, @Field("article_time") String time);

}
