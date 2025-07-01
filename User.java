package com.example.eventtracker;

public class User {
    int id;
    String userName;
    String userPass;
    String userEmail;
    String userPhone;

    public User() {
        super();
    }

    public User(int id, String userName, String userPass, String userEmail, String userPhone) {
        super();
        this.id = id;;
        this.userName = userName;
        this.userPass = userPass;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
    }

    public User(String userName, String userPass, String userEmail, String userPhone) {
        this.userName = userName;
        this.userPass = userPass;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
    }
    // getter
    public int getId() {
        return id;
    }
    // setter
    public void setId(int id) {
        this.id = id;
    }

    // getter
    public String getUserName() {
        return userName;
    }
    // setter
    public void setUserName(String userName) {
        this.userName = userName;
    }

    // getter
    public String getUserPass() {
        return userPass;
    }
    // setter
    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    // getter
    public String getUserEmail() {
        return userEmail;
    }
    // setter
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    // getter
    public String getUserPhone() {
        return userPhone;
    }
    // setter
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}