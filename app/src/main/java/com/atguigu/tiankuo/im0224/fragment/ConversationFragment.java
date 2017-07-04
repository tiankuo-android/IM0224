package com.atguigu.tiankuo.im0224.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 作者：田阔
 * 邮箱：1226147264@qq.com
 * Created by Administrator on 2017/7/3 0003.
 */
public class ConversationFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView =  new TextView(getActivity());
        textView.setText("1111");
        return textView;

    }
}
