package com.atguigu.tiankuo.im0224.common;

import android.content.Context;

import com.atguigu.tiankuo.im0224.model.bean.UserInfo;
import com.atguigu.tiankuo.im0224.model.dao.AccoutDAO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 作者：田阔
 * 邮箱：1226147264@qq.com
 * Created by Administrator on 2017/7/1 0001.
 */

public class Model {

    private AccoutDAO accoutDAO;

    private Context context;

    private Model() {
    }

    private static Model model = new Model();

    public static Model getInstance(){
        return model;
    }

    public void init(Context context){
        this.context = context;
        accoutDAO = new AccoutDAO(context);
    }

    private ExecutorService service = Executors.newCachedThreadPool();

    public ExecutorService getGlobalThread(){
        return service;
    }

    //登录成功之后保存用户数据
    public void loginSuccess(UserInfo userInfo){
        //添加用户
        accoutDAO.addAccout(userInfo);
    }

    public AccoutDAO getAccoutDAO(){
        return accoutDAO;
    }
}
