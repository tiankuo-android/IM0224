package com.atguigu.tiankuo.im0224.activity;

import android.content.Intent;
import android.os.CountDownTimer;

import com.atguigu.tiankuo.im0224.R;
import com.atguigu.tiankuo.im0224.base.BaseActivity;

/**
 * 作者：田阔
 * 邮箱：1226147264@qq.com
 * Created by Administrator on 2017/7/1 0001.
 */

public class WelcomeActivity extends BaseActivity {
    private CountDownTimer countDownTimer;

    //第一个参数是倒计时的总时长，倒计时时间间隔
    //倒计时结束
    @Override
    public void initListener() {
        countDownTimer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                selectChangeActivity();
            }
        }.start();
    }

    private void selectChangeActivity() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                boolean isLogin = true;
                if(isLogin) {
                    startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                }else{
                    startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
                }
            }
        }.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        countDownTimer.cancel();
    }

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }
}
