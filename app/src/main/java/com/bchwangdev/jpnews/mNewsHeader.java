package com.bchwangdev.jpnews;

public class mNewsHeader {
    private String Image ;
    private String Title;
    private String Content;
    private String Company;
    private String Date;
    private String DetailUrl;

    //Main화면에서 사용
    public mNewsHeader(String image, String title, String company, String date, String detailUrl) {
        Image = image;
        Title = title;
        Company = company;
        Date = date;
        DetailUrl = detailUrl;
    }

    //Detail화면에서 사용
    public mNewsHeader(String image, String title, String content, String company, String date, String detailUrl) {
        Image = image;
        Title = title;
        Content = content;
        Company = company;
        Date = date;
        DetailUrl = detailUrl;
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
