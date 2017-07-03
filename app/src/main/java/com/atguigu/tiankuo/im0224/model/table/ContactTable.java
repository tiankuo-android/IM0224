package com.atguigu.tiankuo.im0224.model.table;

/**
 * 作者：田阔
 * 邮箱：1226147264@qq.com
 * Created by Administrator on 2017/7/3 0003.
 */

public class ContactTable {
    //注意：
    //    1.sql语句一定要确定没有问题
    //    2.数据库创建有问题的时候，一定要卸载原来的应用才能重新创建数据库

    public static final String TABLE_NAME = "contact";
    public static final String COL_USER_HXID = "userhxid";
    public static final String COL_USER_NAME = "username";

    public static final String COL_IS_CONTACT = "contact";

    public static final String CREATE_TABLE = "creat table " + TABLE_NAME + "("
            + COL_USER_NAME + " text primary key,"
            +COL_USER_HXID + " text,"
            +COL_IS_CONTACT + " integer)";
}
