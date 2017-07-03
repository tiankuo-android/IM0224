package com.atguigu.tiankuo.im0224.model.bean;

/**
 * 作者：田阔
 * 邮箱：1226147264@qq.com
 * Created by Administrator on 2017/7/3 0003.
 */

public class InvitationInfo {
    private UserInfo userInfo;
    private String reason;
    private InvitationStatus status;

    public InvitationInfo() {

    }

    public InvitationInfo(UserInfo userInfo, String reason, InvitationStatus status) {
        this.userInfo = userInfo;
        this.status = status;
        this.reason = reason;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public InvitationStatus getStatus() {
        return status;
    }

    public void setStatus(InvitationStatus status) {
        this.status = status;
    }

    public enum InvitationStatus {
        NEW_INVITE,// 新邀请
        INVITE_ACCEPT,//接受邀请
        INVITE_ACCEPT_BY_PEER,// 邀请被接受
    }
}
