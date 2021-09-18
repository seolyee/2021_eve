package com.example.HelluApp.DailyStamp;

import android.net.Uri;
import java.util.HashMap;
import java.util.Map;

public class Post {

    public String uid;
    public String author;
    public String title;
    public String content;
    public String image_path;
    public String date;


    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post(String uid, String author, String title, String content, String image_path, String date) {
        this.uid = uid; // uid
        this.author = author; //글 저자
        this.title = title; //글 제목
        this.content = content; //글 내용
        this.image_path = image_path; //글 이미지 경로
        this.date = date; //저장 날짜

    }

    public Map<String, Object> posttomap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("title", title);
        result.put("content", content);
        result.put("image_path", image_path);
        result.put("date", date);

        return result;
    }
}
