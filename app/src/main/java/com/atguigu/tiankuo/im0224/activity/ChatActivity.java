package com.atguigu.tiankuo.im0224.activity;

import android.widget.FrameLayout;

import com.atguigu.tiankuo.im0224.R;
import com.atguigu.tiankuo.im0224.base.BaseActivity;
import com.hyphenate.easeui.ui.EaseChatFragment;

import butterknife.Bind;

public class ChatActivity extends BaseActivity {
    @Bind(R.id.fl_chat)
    FrameLayout flChat;

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        EaseChatFragment easeChatFragment = new EaseChatFragment();
        easeChatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_chat,easeChatFragment).commit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_chat;
    }

}
