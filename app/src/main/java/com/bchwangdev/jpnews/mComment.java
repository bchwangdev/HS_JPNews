package com.bchwangdev.jpnews;

public class mComment {
    String Image;
    String NickName;
    String Date;
    String Content;
    String Good;
    String Bad;

    public mComment() {
    }

    public mComment(String image, String nickName, String date, String content, String good, String bad) {
        Image = image;
        NickName = nickName;
        Date = date;
        Content = content;
        Good = good;
        Bad = bad;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getGood() {
        return Good;
    }

    public void setGood(String good) {
        Good = good;
    }

    public String getBad() {
        return Bad;
    }

    public void setBad(String bad) {
        Bad = bad;
    }
}
