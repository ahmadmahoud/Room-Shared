package com.example.localdatabaseroom.Shared;

public class SharedUser {
    String name,lasname,phone;

    public SharedUser(String name, String lasname, String phone) {
        this.name = name;
        this.lasname = lasname;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLasname() {
        return lasname;
    }

    public void setLasname(String lasname) {
        this.lasname = lasname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
