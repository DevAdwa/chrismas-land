package com.example.fecafootdemo.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/***
 * 单例模式实现数据库连接
 * @author bobo
 *
 */
public class Dbhelper extends SQLiteOpenHelper {

	private static Dbhelper dbhelper = null;

	public static Dbhelper getInstens(Context context) {
		if (dbhelper == null) {
			dbhelper = new Dbhelper(context);
		}
		return dbhelper;
	}

	private Dbhelper(Context context) {
		super(context, "finger_fbi.db", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		 //这张表采用二进制文件存储对象注意第二个字段我们将对象存取在这里面
		String sql_class_table="create table if not exists finger(_id integer primary key autoincrement,template text)";
		db.execSQL(sql_class_table);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
		// TODO Auto-generated method stub

	}

}
