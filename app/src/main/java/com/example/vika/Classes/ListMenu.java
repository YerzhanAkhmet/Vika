package com.example.vika.Classes;

import android.graphics.drawable.Drawable;

public class ListMenu {
    private int position;
    private Drawable logo;
    private String desc;
    private String title;

    public void setLogo(Drawable logo) {
        this.logo = logo;
    }

    public void setDesc (String desc) {
        this.desc = desc;
    }

    public Drawable getLogo() {return logo;}

    public String getDesc() {
        return desc;
    }

    public ListMenu() {}

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {return position;}

    public ListMenu(Drawable logo, String desc, int position, String title) {
        this.logo = logo;
        this.desc = desc;
        this.title = title;
        this.position = position;
    }

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}
}