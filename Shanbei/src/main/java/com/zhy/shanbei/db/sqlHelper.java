package com.zhy.shanbei.db;

/**
 * Created by ykai on 2015/7/2.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 text:
 create table text (
 id integer primary key autoincrement,
 text_title text,
 text_content text,
 lesson_id integer,
 unit_id integer
 )

 */

public class sqlHelper extends SQLiteOpenHelper{
 /**
  text 建表语句
  */
 public static  final String Create_TEXT="create table MyText (" +
         " id integer primary key autoincrement," +
         " text_title text," +
         " text_content text," +
         " lesson_id integer," +
         " unit_id integer" +
         " )";

 public sqlHelper(Context context,String name,CursorFactory factory,int version){
  super(context,name,factory,version);
 }

 @Override
 public void onCreate(SQLiteDatabase db) {
  db.execSQL(Create_TEXT); // 创建text表
 }

 @Override
 public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

 }
}
