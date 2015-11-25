package com.edcan.grooshbene.soundeffector;

import java.util.List;

/**
 * Created by GrooshBene on 2015. 11. 23..
 */
public class Article {
    public int id;
        public String user_article;
        public String user_name;
        public String article_time;
    public Article(int id, String user_article, String user_name, String article_time){
        this.id = id;
        this.user_article = user_article;
        this.user_name = user_name;
        this.article_time = article_time;
    }
}
