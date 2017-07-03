package com.atguigu.tiankuo.im0224.model.bean;

/**
 * 作者：田阔
 * 邮箱：1226147264@qq.com
 * Created by Administrator on 2017/7/1 0001.
 */

public class UserInfo {
    private String username;
    private String hxid;

    public UserInfo() {

    }

    public UserInfo(String username, String hxid) {
        this.username = username;
        this.hxid = hxid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHxid() {
        return hxid;
    }

    public void setHxid(String hxid) {
        this.hxid = hxid;
    }
}
