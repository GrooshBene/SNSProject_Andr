package com.edcan.grooshbene.soundeffector;

import java.util.List;

/**
 * Created by GrooshBeneMac on 2015-11-19.
 */
public class User {
    public int id;
    public String user_id;
    public String user_pw;
    public String user_name;

    public User(int id, String user_id, String user_pw, String user_name) {
        this.id = id;
        this.user_id = user_id;
        this.user_pw = user_pw;
        this.user_name = user_name;
    }
}
