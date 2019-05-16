package com.example.vika.Classes;

public class Post {
    private String date;
    private int id;
    private String avatar;
    private String photo;
    private String foto;
    private String title;
    private String desc;

    public Post() {}

    public Post(String date, int id, String title, String desc, String avatar, String photo, String foto) {
        this.date = date;
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.avatar = avatar;
        this.photo = photo;
        this.foto = foto;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAvatar() {return avatar;}

    public void setAvatar(String avatar) {this.avatar = avatar;}

    public String getPhoto() {return photo;}

    public void setPhoto(String photo) {this.photo = photo;}

    public String getFoto() {return foto;}

    public void setFoto(String foto) {this.foto = foto;}
}