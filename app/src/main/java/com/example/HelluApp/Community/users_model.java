package com.example.HelluApp.Community;

public class users_model {
    public static String useremail;
    public static String usernm;
    public static String userphoto;
    public static String uid;

    public users_model(){ }

    public users_model(String useremail, String usernm, String userphoto, String uid){
        this.useremail = useremail;
        this.usernm = usernm;
        this.userphoto = userphoto;
        this.uid = uid;
    }

    public static String getUseremail() {
        return useremail;
    }
    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public static String getUsernm() {
        return usernm;
    }

    public void setUsernm(String usernm) {
        this.usernm = usernm;
    }

    public static String getUserphoto() {
        return userphoto;
    }

    public void setUserphoto(String userphoto) {
        this.userphoto = userphoto;
    }

    public static String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setUsernm() {
    }
}
