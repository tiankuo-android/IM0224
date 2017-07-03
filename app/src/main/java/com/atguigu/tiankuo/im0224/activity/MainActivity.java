package com.atguigu.tiankuo.im0224.activity;

import android.support.v4.app.Fragment;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.atguigu.tiankuo.im0224.R;
import com.atguigu.tiankuo.im0224.base.BaseActivity;
import com.atguigu.tiankuo.im0224.fragment.ContactActivity;
import com.atguigu.tiankuo.im0224.fragment.ConversationActivity;
import com.atguigu.tiankuo.im0224.fragment.SettingsActivity;

import butterknife.Bind;

public class MainActivity extends BaseActivity {

    @Bind(R.id.main_fl)
    FrameLayout mainFl;
    @Bind(R.id.rb_main_conversation)
    RadioButton rbMainConversation;
    @Bind(R.id.rb_main_contact)
    RadioButton rbMainContact;
    @Bind(R.id.rb_main_setting)
    RadioButton rbMainSetting;
    @Bind(R.id.main_rg)
    RadioGroup mainRg;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;

    @Override
    public void initListener() {
        mainRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switchFragment(checkedId);
            }
        });
    }

    private void switchFragment(int checkedId) {
        Fragment fragment = null;

        switch (checkedId) {
            case R.id.rb_main_contact :
                fragment = new ContactActivity();
                break;
            case R.id.rb_main_conversation :
                fragment = new ConversationActivity();
                break;
            case R.id.rb_main_setting :
                fragment = new SettingsActivity();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fl,fragment).commit();
    }

    @Override
    public void initData() {
        switchFragment(R.id.rb_main_conversation);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

}
