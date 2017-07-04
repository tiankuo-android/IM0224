package com.atguigu.tiankuo.im0224.common;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.atguigu.tiankuo.im0224.model.Model;
import com.atguigu.tiankuo.im0224.model.bean.InvitationInfo;
import com.atguigu.tiankuo.im0224.model.bean.UserInfo;
import com.atguigu.tiankuo.im0224.utils.SPUtils;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;

/**
 * 作者：田阔
 * 邮箱：1226147264@qq.com
 * Created by Administrator on 2017/7/3 0003.
 */

public class GlobalListener {

    private final LocalBroadcastManager manager;

    public GlobalListener(Context context){
        EMClient.getInstance().contactManager().setContactListener(emContactListener);

        //本地广播 只有本应用可以收到
        //全局广播 所有应用都可以收到
        manager = LocalBroadcastManager.getInstance(context);
    }

    private final EMContactListener emContactListener = new EMContactListener() {
        //收到好友邀请  别人加你
        @Override
        public void onContactInvited(String username, String reason) {
            InvitationInfo invitationInfo = new InvitationInfo();
            invitationInfo.setReason(reason);
            invitationInfo.setUserInfo(new UserInfo(username,username));
            //添加Invitation
            Model.getInstance().getHelperManager()
                    .getInvitationDAO().addInvitation(invitationInfo);

            //保存小红点状态
            SPUtils.getSpUtils().save(SPUtils.NEW_INVITE,true);
            //发送广播
            manager.sendBroadcast(new Intent(Constant.NEW_INVITE_CHANGE));
        }

        //好友请求被同意  你加别人的时候 别人同意了
        @Override
        public void onContactAgreed(String username) {
            //添加好友信息
            InvitationInfo invitationInfo = new InvitationInfo();
            invitationInfo.setUserInfo(new UserInfo(username,username));
            invitationInfo.setReason("邀请被接受");
            invitationInfo.setStatus(InvitationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER);
            Model.getInstance().getHelperManager().getInvitationDAO().addInvitation(invitationInfo);
            //保存小红点
            SPUtils.getSpUtils().save(SPUtils.NEW_INVITE,true);
            //发送广播
            manager.sendBroadcast(new Intent(Constant.NEW_INVITE_CHANGE));

        }

        //被删除时回调此方法
        @Override
        public void onContactDeleted(String username) {
            //删除邀请信息
            Model.getInstance().getHelperManager().getInvitationDAO().removeInvitation(username);
            //删除联系人
            Model.getInstance().getHelperManager().getContactDAO().deleteContactByHxId(username);
            //发送广播
            manager.sendBroadcast(new Intent(Constant.CONTACT_CHANGE));
        }


        //增加了联系人时回调此方法  当你同意添加好友
        @Override
        public void onContactAdded(String username) {
            //增加联系人
            Model.getInstance().getHelperManager().getContactDAO().saveContact(new UserInfo(username,username),true);
            //发送广播
            manager.sendBroadcast(new Intent(Constant.CONTACT_CHANGE));
        }

        //好友请求被拒绝  你加别人 别人拒绝了
        @Override
        public void onContactRefused(String username) {
            //保存小红点
            SPUtils.getSpUtils().save(SPUtils.NEW_INVITE,true);
            //发送广播
            manager.sendBroadcast(new Intent(Constant.NEW_INVITE_CHANGE));
        }
    };
}




