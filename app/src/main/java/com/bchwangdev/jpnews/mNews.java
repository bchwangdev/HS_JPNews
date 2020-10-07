package com.bchwangdev.jpnews;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "news")
public class mNews {
    @PrimaryKey(autoGenerate = true)
    private Integer Id;
    private String Image ;
    private String Title;
    private String Content;
    private String Company;
    private String Date;
    private String DetailUrl;

    public mNews() {
    }

    //Main화면에서 사용
    public mNews(String image, String title, String company, String date, String detailUrl) {
        Image = image;
        Title = title;
        Company = company;
        Date = date;
        DetailUrl = detailUrl;
    }

    //Detail화면에서 사용
    public mNews(String image, String title, String content, String company, String date, String detailUrl) {
        Image = image;
        Title = title;
        Content = content;
        Company = company;
        Date = date;
        DetailUrl = detailUrl;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getDetailUrl() {
        return DetailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        DetailUrl = detailUrl;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
