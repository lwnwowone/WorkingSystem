package com.alanc.workingsystem.workingsystem.DataMeta;

public class UserMeta {

    //用户ID
    private long userID;

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    //用户名
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    //姓名
    private String realName;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    //当前token
    private String availableToken;

    public String getAvailableToken() {
        return availableToken;
    }

    public void setAvailableToken(String availableToken) {
        this.availableToken = availableToken;
    }
}
