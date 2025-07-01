package com.example.eventtracker;

import java.time.LocalDate;

public class Event {
    int id;
    String title;
    String user_email;
    String details;

    String date;
    public Event() {
        super();
    }
    // constructors
    public Event(int id, String title, String details, String date) {
        super();
        this.id = id;
        this.title = title;
        this.user_email = user_email;
        this.details = details;
        this.date = date;
    }

    public Event(String title, String details, String date) {
        this.title = title;
        this.user_email = user_email;
        this.details = details;
        this.date = date;
    }

    // getters and setters for Event
    public int getId() {
        return  id;
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


    public String  getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser_email() {
        return user_email;
    }
    public void setUser_email(String userEmail){
        this.user_email = userEmail;
    }
}
