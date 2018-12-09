package com.test.newshop1.data.database.customer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginData {

    @SerializedName("username")
    @Expose
    private String userName;

    @SerializedName("password")
    @Expose
    private String pass;

    @SerializedName("googleaccount")
    @Expose
    private boolean isGoogleLogin;

    public LoginData(String userName, String pass, boolean isGoogleLogin) {
        this.userName = userName;
        this.pass = pass;
        this.isGoogleLogin = isGoogleLogin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public boolean isGoogleLogin() {
        return isGoogleLogin;
    }

    public void setGoogleLogin(boolean googleLogin) {
        isGoogleLogin = googleLogin;
    }

    @Override
    public String toString() {
        return "LoginData{" +
                "userName='" + userName + '\'' +
                ", pass='" + pass + '\'' +
                ", isGoogleLogin=" + isGoogleLogin +
                '}';
    }
}
