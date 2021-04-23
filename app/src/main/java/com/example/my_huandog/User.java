package com.example.my_huandog;
public class User {

    public String userEmail;
    public String userPass;
    public String userName;
    public String userAddr;

    public User() {

    }

    public User(String userEmail, String userPass, String userName, String userAddr ){
        this.userEmail = userEmail;
        this.userPass = userPass;
        this.userName = userName;
        this.userAddr = userAddr;


    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAddr() {
        return userAddr;
    }

    public void setUserAddr(String userAddr) {
        this.userAddr = userAddr;
    }


}
