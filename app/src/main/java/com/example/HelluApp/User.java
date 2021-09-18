package com.example.HelluApp;

import java.util.HashMap;

public class User {

    public String Nickname;
    public String Email;
    public String Uid;
    public String ProfileUrl;

    public HashMap<String, Object> usermap = new HashMap<>();

    public User(){
        // Default constructor required for calls to DataSnapshot.getValue(user.class)
    }

    public User(String Nickname, String Email, String Uid, String ProfileUrl){
        this.Nickname = Nickname;
        this.Email = Email;
        this.Uid = Uid;
        this.ProfileUrl = ProfileUrl;
    }

    public HashMap<String, Object> usertomap(){
        HashMap<String, Object> user_result = new HashMap<>();
        user_result.put("Nickname", Nickname); //키, 값
        user_result.put("Email", Email);
        user_result.put("Uid", Uid);
        user_result.put("ProfileUrl", ProfileUrl);

        return user_result;
    }
}
