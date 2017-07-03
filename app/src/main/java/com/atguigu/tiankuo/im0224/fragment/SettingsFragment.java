package com.atguigu.tiankuo.im0224.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.atguigu.tiankuo.im0224.R;
import com.atguigu.tiankuo.im0224.activity.LoginActivity;
import com.atguigu.tiankuo.im0224.utils.UIUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：田阔
 * 邮箱：1226147264@qq.com
 * Created by Administrator on 2017/7/3 0003.
 */
public class SettingsFragment extends Fragment {
    @Bind(R.id.btn_back)
    Button btnBack;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_setting, null);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //获取当前用户  1.数据库  2.环信ID
        String currentUser = EMClient.getInstance().getCurrentUser();
        btnBack.setText("退出登录(" + currentUser + ")");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //第一个参数  如果集成了GCM第三方推送，方法里的第一个参数需要设为true
                EMClient.getInstance().logout(false, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        //清除个人数据
                        UIUtils.showToast("退出成功");
                        //跳转到登录界面
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        //把当前界面finish
                        getActivity().finish();
                    }

                    @Override
                    public void onError(int i, String s) {
                        UIUtils.showToast(s);
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
