package com.atguigu.tiankuo.im0224.model.table;

/**
 * 作者：田阔
 * 邮箱：1226147264@qq.com
 * Created by Administrator on 2017/7/3 0003.
 */

public class InvitationTable {

    public static final String TABLE_NAME = "invitation";
    public static final String COL_USER_NAME = "username";
    public static final String COL_USER_HXID = "userhxid";
    public static final String COL_REASON = "reason";
    public static final String COL_STATE = "state";

    public static final String CREAT_TABLE = "create table " + TABLE_NAME +"("
            + COL_USER_NAME + " text primary key,"
            + COL_USER_HXID + " text,"
            + COL_REASON + " text,"
            + COL_STATE + " integer)";
}
