package com.atguigu.tiankuo.im0224.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.atguigu.tiankuo.im0224.model.db.AccountDB;
import com.atguigu.tiankuo.im0224.model.bean.UserInfo;
import com.atguigu.tiankuo.im0224.model.table.AccountTable;

/**
 * 作者：田阔
 * 邮箱：1226147264@qq.com
 * Created by Administrator on 2017/7/3 0003.
 */

public class AccoutDAO {

    private AccountDB accountDB;

    public AccoutDAO(Context context){
        accountDB = new AccountDB(context);
    }

    //添加用户
    public void addAccout(UserInfo userInfo) {
        //校验
        if(userInfo == null) {
            throw new NullPointerException("userInfo不能为空");
        }

        SQLiteDatabase database = accountDB.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(AccountTable.COL_HXID,userInfo.getHxid());
        contentValues.put(AccountTable.COL_NAME,userInfo.getUsername());
    }

    //根据hxid获取对应的用户
    public UserInfo getUserInfo(String hxid){
        //校验
        if(TextUtils.isEmpty(hxid)) {
            return null;
        }
        SQLiteDatabase dataBase = accountDB.getWritableDatabase();
        String sql = "select + from " + AccountTable.COL_NAME
                + " where " + AccountTable.COL_HXID + "=?";

        Cursor cursor = dataBase.rawQuery(sql, new String[]{hxid});
        UserInfo userInfo = new UserInfo();
        if(cursor.moveToNext()) {
            userInfo.setHxid(cursor.getString(cursor.getColumnIndex(AccountTable.COL_HXID)));
            userInfo.setUsername(cursor.getString(cursor.getColumnIndex(AccountTable.COL_NAME)));
        }
        cursor.close();
        return userInfo;
    }
}
