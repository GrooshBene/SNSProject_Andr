package com.edcan.grooshbene.soundeffector;

import android.content.Context;

/**
 * Created by GrooshBene on 2015. 11. 23..
 */
public class CData {
    public String content_label;
    public String description;
    public String article;
    public int spCnt;
    public String writer;
    public String time;

    public CData(Context context,String article_, String writer_,String time_) {
        article = article_;
        writer = writer_;
        time = time_;
    }


    public String getContent_label() {
        return content_label;
    }

    public String getDescription() {
        return description;
    }

    public String getArticle() {
        return article;
    }

    public String getWriter(){
        return writer;
    }

    public String getTime(){
        return time;
    }

    public int getSpCnt(){return spCnt;}

}
