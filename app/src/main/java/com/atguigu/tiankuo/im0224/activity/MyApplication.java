package com.atguigu.tiankuo.im0224.activity;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.atguigu.tiankuo.im0224.model.Model;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;

/**
 * 作者：田阔
 * 邮箱：1226147264@qq.com
 * Created by Administrator on 2017/7/1 0001.
 */

public class MyApplication extends Application {

    private static Context context;
    private static Handler handler;
    private static int pid;

    public static Context getContext() {
        return context;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static int getPid() {
        return pid;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化环信SDK
        initHXsdk();
    }

    private void initHXsdk() {
        EMOptions options = new EMOptions();
        //是否自动接受邀请
        options.setAcceptInvitationAlways(false);
        //是否自动接受群邀请
        options.setAutoAcceptGroupInvitation(false);
        //初始化EaseUI
        EaseUI.getInstance().init(this,options);

        //初始化modle
        Model.getInstance().init(this);

        handler = new Handler();
        pid = android.os.Process.myPid();
        context = this;
    }
}
