package com.example.vika.Classes;

public class Message {
    String status;
    int status_code;
    SMS sms;
    Double balance;

    public Message (){}

    public Message(String status, int status_code, SMS sms, Double balance){
        this.status = status;
        this.status_code = status_code;
        this.sms = sms;
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void setSms(SMS sms) {
        this.sms = sms;
    }

    public int getStatus_code() {
        return status_code;
    }

    public Double getBalance() {
        return balance;
    }

    public SMS getSms() {
        return sms;
    }
}