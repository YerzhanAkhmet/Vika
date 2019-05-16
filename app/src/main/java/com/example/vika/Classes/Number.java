package com.example.vika.Classes;

public class Number {
    private String status;
    private int status_code;
    private String sms_id;
    private String status_text;

    public Number(){}

    public Number(String status, int status_code, String sms_id, String status_text){
        this.status = status;
        this.status_code = status_code;
        this.sms_id = sms_id;
        this.status_text = status_text;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setSms_id(String sms_id) {
        this.sms_id = sms_id;
    }

    public String getSms_id() {
        return sms_id;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getStatus_text() {
        return status_text;
    }
}
