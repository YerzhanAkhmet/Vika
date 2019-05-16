package com.example.vika.Classes;

public class Users {
    private String name;
    private String phone;
    private String status;
    private String quote;
    private String city;
    private String university;
    private String avatar;

    public Users() {}

    public Users(String name, String phone, String status, String quote, String city, String university, String avatar) {
        this.name = name;
        this.phone = phone;
        this.status = status;
        this.quote = quote;
        this.city = city;
        this.university = university;
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}

    public String getQuote() {return quote;}

    public void setQuote(String quote) {this.quote = quote;}

    public String getCity() {return city;}

    public void setCity(String city) {this.city = city;}

    public String getUniversity() {return university;}

    public void setUniversity(String university) {this.university = university;}

    public String getAvatar() {return avatar;}

    public void setAvatar(String avatar){this.avatar = avatar;}
}