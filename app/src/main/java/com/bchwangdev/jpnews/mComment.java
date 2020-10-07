package com.bchwangdev.jpnews;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "newsComment")
public class mComment {
    @PrimaryKey(autoGenerate = true)
    private Integer Id;
    private Integer ParentId;
    private String Image;
    private String NickName;
    private String Date;
    private String Content;
    private String Good;
    private String Bad;

    public mComment() {
    }

    public mComment(Integer parentId, String image, String nickName, String date, String content, String good, String bad) {
        ParentId = parentId;
        Image = image;
        NickName = nickName;
        Date = date;
        Content = content;
        Good = good;
        Bad = bad;
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
