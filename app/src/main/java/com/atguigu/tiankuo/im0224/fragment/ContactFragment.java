package com.atguigu.tiankuo.im0224.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.atguigu.tiankuo.im0224.R;
import com.atguigu.tiankuo.im0224.activity.AddContactActivity;
import com.atguigu.tiankuo.im0224.activity.ChatActivity;
import com.atguigu.tiankuo.im0224.activity.InviteActivity;
import com.atguigu.tiankuo.im0224.common.Constant;
import com.atguigu.tiankuo.im0224.model.Model;
import com.atguigu.tiankuo.im0224.model.bean.UserInfo;
import com.atguigu.tiankuo.im0224.utils.SPUtils;
import com.atguigu.tiankuo.im0224.utils.UIUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
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

    private BroadcastReceiver inviteReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            isShowRedView();
        }
    };

    private ImageView redView;
    private BroadcastReceiver contactReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //添加或者删除联系人后调用
            refreshData();
        }
    };

    private List<UserInfo> contacts;

    protected void initView() {
        super.initView();

        setContactListItemClickListener(new EaseContactListItemClickListener() {
            @Override
            public void onListItemClicked(EaseUser user) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID,user.getUsername());
                startActivity(intent);
            }
        });
    }

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
        //删除联系人
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    return false;
                }
                //弹警告
                showDialog(position);
                return true;
            }
        });
    }

    private void showDialog(final int position) {
        new AlertDialog.Builder(getActivity()).setMessage("确定要删除吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Model.getInstance().getGlobalThread().execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    //网络
                                    UserInfo userInfo = contacts.get(position - 1);
                                    EMClient.getInstance().contactManager()
                                            .deleteContact(userInfo.getHxid());

                                    //本地
                                    Model.getInstance().getHelperManager().getContactDAO()
                                            .deleteContactByHxId(userInfo.getHxid());

                                    //内存和页面
                                    if (getActivity() != null) {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                refreshData();
                                            }
                                        });
                                    }
                                } catch (HyphenateException e) {
                                    e.printStackTrace();
                                    UIUtils.showToast(e.getMessage());
                                }
                            }
                        });
                    }
                }).setNegativeButton("取消", null)
                .show();
    }

    private void showContact() {
        //判断是否是第一次进入应用，第一次进入应用需要从服务器获取联系人列表 否则从数据库调用
        refreshServier();
    }

    //从服务器获取好友列表
    public void refreshServier() {
        Model.getInstance().getGlobalThread().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //网络
                    List<String> contacts = EMClient.getInstance().contactManager().getAllContactsFromServer();

                    //本地 数据转换
                    List<UserInfo> userInfos = new ArrayList<UserInfo>();
                    for (String contacs : contacts) {
                        UserInfo userInfo = new UserInfo(contacs, contacs);
                        userInfos.add(userInfo);
                    }

                    //保存从服务器获取的联系人
                    Model.getInstance().getHelperManager().getContactDAO().saveContacts(userInfos, true);

                    //内存和页面
                    if (getActivity() == null) {
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
    public void refreshData() {
        //从数据库获取联系人数据
        contacts = Model.getInstance().getHelperManager().getContactDAO().getContacts();
        //校验
        if (contacts != null) {
            //添加数据
            Map<String, EaseUser> map = new HashMap<>();
            //数据类型转换
            for (UserInfo info : contacts) {
                map.put(info.getHxid(), new EaseUser(info.getUsername()));
            }

            setContactsMap(map);
            //获取数据
            getContactList();
            //刷新数据
            contactListLayout.refresh();
        }
    }

    //判断小红点是否显示
    private void isShowRedView() {
        //获取小红点以后的状态
        Boolean bolValue = SPUtils.getSpUtils().getValue(SPUtils.NEW_INVITE);
        //是否显示小红点
        redView.setVisibility(bolValue ? View.VISIBLE : View.GONE);
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
