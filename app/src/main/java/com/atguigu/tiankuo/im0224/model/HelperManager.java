package com.atguigu.tiankuo.im0224.model;

import android.content.Context;

import com.atguigu.tiankuo.im0224.model.dao.ContactDAO;
import com.atguigu.tiankuo.im0224.model.dao.InvitationDAO;
import com.atguigu.tiankuo.im0224.model.db.DBHelper;

/**
 * 作者：田阔
 * 邮箱：1226147264@qq.com
 * Created by Administrator on 2017/7/4 0004.
 */

public class HelperManager {
    private final DBHelper dbHelper;
    private final ContactDAO contactDAO;
    private final InvitationDAO invitationDAO;

    public HelperManager(Context context, String name) {
        dbHelper = new DBHelper(context, name);
        contactDAO = new ContactDAO(dbHelper);
        invitationDAO = new InvitationDAO(dbHelper);
    }

    public DBHelper getDbHelper() {
        return dbHelper;
    }

    public void close() {
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    public ContactDAO getContactDAO() {
        return contactDAO;
    }

    public InvitationDAO getInvitationDAO() {
        return invitationDAO;
    }
}
