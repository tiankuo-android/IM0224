package com.atguigu.tiankuo.im0224.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.atguigu.tiankuo.im0224.R;
import com.atguigu.tiankuo.im0224.utils.UIUtils;
import com.hyphenate.easeui.ui.EaseContactListFragment;

/**
 * 作者：田阔
 * 邮箱：1226147264@qq.com
 * Created by Administrator on 2017/7/3 0003.
 */
public class ContactFragment extends EaseContactListFragment {

    protected void initView() {
        super.initView();
    }

    @Override
    protected void setUpView() {
        super.setUpView();

        //初始化ListView头布局
        initHeadView();

        titleBar.setRightImageResource(R.drawable.ease_blue_add);
        titleBar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),AddContactActivity.class));
            }
        });

    }

    private void initHeadView() {

        View headView = View.inflate(getContext(), R.layout.head_view, null);
        LinearLayout groups = (LinearLayout) headView.findViewById(R.id.ll_groups);
        LinearLayout friends = (LinearLayout) headView.findViewById(R.id.ll_friends);

        listView.addHeaderView(headView);

        groups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.showToast("groups");
            }
        });
        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.showToast("friends");
            }
        });
    }
}
