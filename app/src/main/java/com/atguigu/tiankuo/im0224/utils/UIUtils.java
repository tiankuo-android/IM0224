package com.atguigu.tiankuo.im0224.utils;

import android.content.Context;
import android.widget.Toast;

import com.atguigu.tiankuo.im0224.activity.MyApplication;

/**
 * 作者：田阔
 * 邮箱：1226147264@qq.com
 * Created by Administrator on 2017/7/3 0003.
 */

public class UIUtils {

    public static Context getContext() {
        return MyApplication.getContext();
    }

    //保证runnable在主线程中进行
    public static void UIThread(Runnable runnable){
        if(MyApplication.getPid() == android.os.Process.myTid()) {
            runnable.run();
        }else{
            MyApplication.getHandler().post(runnable);
        }
    }

    //显示吐司
    public static void showToast(final String message){
        UIThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(),message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
