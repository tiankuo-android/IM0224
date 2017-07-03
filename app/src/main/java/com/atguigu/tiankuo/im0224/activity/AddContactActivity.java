package com.atguigu.tiankuo.im0224.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atguigu.tiankuo.im0224.R;
import com.atguigu.tiankuo.im0224.base.BaseActivity;
import com.atguigu.tiankuo.im0224.common.Model;
import com.atguigu.tiankuo.im0224.utils.UIUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import butterknife.Bind;

public class AddContactActivity extends BaseActivity {

    @Bind(R.id.btn_search)
    Button btnSearch;
    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.tv_username)
    TextView tvUsername;
    @Bind(R.id.btn_add)
    Button btnAdd;
    @Bind(R.id.ll_item)
    LinearLayout llItem;
    @Bind(R.id.activity_add_contact)
    LinearLayout activityAddContact;
    private String username;

    @Override
    public void initListener() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入的用户名
                username = etSearch.getText().toString().trim();
                if(TextUtils.isEmpty(username)) {
                    UIUtils.showToast("用户名不能为空");
                    return;
                }
                Model.getInstance().getGlobalThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        //去本地服务器查看联系人
                        if(getUser()) {
                            //服务器查看联系人
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    llItem.setVisibility(View.VISIBLE);
                                    tvUsername.setText(username);
                                }
                            });
                        }else{
                            //服务器查询不到此人
                            UIUtils.showToast("查无此人");
                        }
                    }
                });
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //访问环信服务器添加联系人
                //第一个参数是环信ID 第二个参数是添加的原因
                try {
                    EMClient.getInstance().contactManager().addContact(username,"缘来");
                    UIUtils.showToast("添加联系人成功");
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    UIUtils.showToast("添加联系人失败");
                }
            }
        });
    }

    private boolean getUser() {
        return true;
    }

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_contact;
    }
}
