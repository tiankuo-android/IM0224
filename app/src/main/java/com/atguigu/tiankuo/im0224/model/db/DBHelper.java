package com.atguigu.tiankuo.im0224.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.atguigu.tiankuo.im0224.model.table.ContactTable;
import com.atguigu.tiankuo.im0224.model.table.InvitationTable;

/**
 * 作者：田阔
 * 邮箱：1226147264@qq.com
 * Created by Administrator on 2017/7/3 0003.
 */

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context, String name) {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //联系人表
        db.execSQL(ContactTable.CREATE_TABLE);
        //邀请联系人表
        db.execSQL(InvitationTable.CREAT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
