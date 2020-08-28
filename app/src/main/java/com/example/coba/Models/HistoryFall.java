package com.example.coba.Models;

public class HistoryFall {

    String day;
    String hour;

    public HistoryFall() {
    }

    public HistoryFall(String day, String hour) {
        this.day = day;
        this.hour = hour;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }
}
