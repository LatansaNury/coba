package com.example.coba.Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public Date getDate() {
        try {
            return new SimpleDateFormat("dd / MM / yyyy").parse(day);
        } catch (ParseException e) {
            return null;
        }
    }
}
