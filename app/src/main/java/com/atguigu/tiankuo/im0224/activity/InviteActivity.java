package com.atguigu.tiankuo.im0224.activity;

import android.widget.ListView;

import com.atguigu.tiankuo.im0224.R;
import com.atguigu.tiankuo.im0224.base.BaseActivity;
import com.atguigu.tiankuo.im0224.model.Model;
import com.atguigu.tiankuo.im0224.model.bean.InvitationInfo;
import com.atguigu.tiankuo.im0224.utils.SPUtils;
import com.atguigu.tiankuo.im0224.utils.UIUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

import butterknife.Bind;

public class InviteActivity extends BaseActivity {

    @Bind(R.id.lv_invite)
    ListView lvInvite;
    private InviteAdapter adapter;

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        //设置小红点的状态
        SPUtils.getSpUtils().save(SPUtils.NEW_INVITE, false);
        adapter = new InviteAdapter(this, onInviteListener);
        lvInvite.setAdapter(adapter);
        refreshData();
    }

    public void refreshData() {
        //获取数据
        List<InvitationInfo> invitationInfos = Model.getInstance().getHelperManager().getInvitationDAO().getInvitations();
        if (invitationInfos != null) {
            adapter.refresh(invitationInfos);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_invite;
    }

    /*
    * inviteAdapter的回调方法
    *
    * */
    InviteAdapter.OnInviteListener onInviteListener = new InviteAdapter.OnInviteListener() {
        @Override
        public void invitedSuccess(final InvitationInfo info) {
            //接受邀请
            Model.getInstance().getGlobalThread().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        //网络
                        String hxid = info.getUserInfo().getHxid();
                        EMClient.getInstance().contactManager().acceptInvitation(hxid);
                        //本地
                        Model.getInstance().getHelperManager().getInvitationDAO()
                                .updateInvitationStatus(InvitationInfo.InvitationStatus.INVITE_ACCEPT, hxid);
                        //内存
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refreshData();
                            }
                        });
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        UIUtils.showToast(e.getMessage());
                    }

                }
            });

        }

        @Override
        public void invitedReject(final InvitationInfo info) {
            //拒绝邀请
            Model.getInstance().getGlobalThread().execute(new Runnable() {
                @Override
                public void run() {
                    String hxid = info.getUserInfo().getHxid();

                    try {
                        //网络
                        EMClient.getInstance().contactManager().declineInvitation(hxid);

                        //本地
                        Model.getInstance().getHelperManager().getInvitationDAO()
                                .removeInvitation(hxid);

                        //内存和页面
                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               refreshData();
                           }
                       });

                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        UIUtils.showToast(e.getMessage());
                    }

                }
            });
        }
    };
}
