package com.atguigu.tiankuo.im0224.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.atguigu.tiankuo.im0224.R;
import com.atguigu.tiankuo.im0224.activity.AddContactActivity;
import com.atguigu.tiankuo.im0224.activity.InviteActivity;
import com.atguigu.tiankuo.im0224.common.Constant;
import com.atguigu.tiankuo.im0224.model.Model;
import com.atguigu.tiankuo.im0224.model.bean.UserInfo;
import com.atguigu.tiankuo.im0224.utils.SPUtils;
import com.atguigu.tiankuo.im0224.utils.UIUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：田阔
 * 邮箱：1226147264@qq.com
 * Created by Administrator on 2017/7/3 0003.
 */
public class ContactFragment extends EaseContactListFragment {

    private ImageView redView;

    private BroadcastReceiver inviteReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            isShowRedView();
        }
    };

    private BroadcastReceiver contactReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //添加或者删除联系人后调用
        }
    };

    @Override
    protected void setUpView() {
        super.setUpView();

        //初始化ListView头布局
        initHeadView();

        isShowRedView();

        titleBar.setRightImageResource(R.drawable.ease_blue_add);
        titleBar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddContactActivity.class));
            }
        });
        //注册监听
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getActivity());
        //邀请信息发生改变
        manager.registerReceiver(inviteReceiver, new IntentFilter(Constant.NEW_INVITE_CHANGE));
        //联系人发生改变
        manager.registerReceiver(contactReceiver, new IntentFilter(Constant.CONTACT_CHANGE));

        //展示联系人
        showContact();

    }


    //判断小红点是否显示
    private void isShowRedView() {
        //获取小红点以后的状态
        Boolean bolValue = SPUtils.getSpUtils().getValue(SPUtils.NEW_INVITE);
        //是否显示小红点
        redView.setVisibility(bolValue ? View.VISIBLE : View.GONE);
    }

    protected void initView() {
        super.initView();
    }

    private void showContact() {
        //判断是否是第一次进入应用，第一次进入应用需要从服务器获取联系人列表 否则从数据库调用
        refreshServier();
    }

    //从服务器获取好友列表
    private void refreshServier() {
        Model.getInstance().getGlobalThread().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //网络
                    List<String> contacts = EMClient.getInstance().contactManager().getAllContactsFromServer();

                    //本地 数据转换
                    List<UserInfo> userInfos = new ArrayList<UserInfo>();
                    for (String contacs:contacts){
                        UserInfo userInfo = new UserInfo(contacs,contacs);
                        userInfos.add(userInfo);
                    }

                    //保存从服务器获取的联系人
                    Model.getInstance().getHelperManager().getContactDAO().saveContacts(userInfos,true);

                    //内存和页面
                    if(getActivity() == null) {
                        return;
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            refreshData();
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //从本地获取联系人数据
    private void refreshData() {
        //从数据库获取联系人数据
        List<UserInfo> contacts = Model.getInstance().getHelperManager().getContactDAO().getContacts();
        //校验
        if(contacts != null) {
            //添加数据
            Map<String , EaseUser> map = new HashMap<>();
            //数据类型转换
            for (UserInfo info:contacts){
                map.put(info.getHxid(),new EaseUser(info.getUsername()));
            }

            setContactsMap(map);
            //获取数据
            getContactList();
            //刷新数据
            contactListLayout.refresh();
        }
    }

    public void initHeadView() {

        View headView = View.inflate(getContext(), R.layout.head_view, null);
        LinearLayout groups = (LinearLayout) headView.findViewById(R.id.ll_groups);
        LinearLayout friends = (LinearLayout) headView.findViewById(R.id.ll_friends);
        redView = (ImageView) headView.findViewById(R.id.iv_invite);

        listView.addHeaderView(headView);

        groups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.showToast("friends");
            }
        });
        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                UIUtils.showToast("friends");
                startActivity(new Intent(getActivity(), InviteActivity.class));
            }
        });
    }

    //当界面再次展示的时候回调

    @Override
    public void onResume() {
        super.onResume();
        isShowRedView();
    }
}
