package com.zhy.shanbei.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zhy.shanbei.model.textItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ykai on 2015/7/2.
 */
public class ShanbeiDB {
    /**
     * 数据库名
     */
    public static final String DB_NAME="shanbei";
    /**
     * 数据库版本
     */
    public static final int VERSION=1;

    private static ShanbeiDB shanbeiDB;
    private SQLiteDatabase db;
    /**
     * 将构造方法私有化
     */
    public ShanbeiDB(Context context){
        sqlHelper dbHelper=new sqlHelper(context,DB_NAME,null,VERSION);
        db=dbHelper.getWritableDatabase();
    }
    /**
     * 获取ShanbeiDB 的实例
     */
    public synchronized static ShanbeiDB getInstance(Context context){
        if(shanbeiDB==null){
            shanbeiDB=new ShanbeiDB(context);
        }
        return shanbeiDB;
    }
    /**
     * 将 text 实例储存到数据库 
     */
    public void saveText(textItem item){  //add
        if(item!=null){
            ContentValues contentValues=new ContentValues();
            contentValues.put("text_title",item.getTitle());
            contentValues.put("text_content",item.getContent());
            contentValues.put("lesson_id",item.getLessonId());
            contentValues.put("unit_id",item.getUnitId());
            db.insert("MyText",null,contentValues);
        }
    }
    /**
     * 从数据库获取所有 items
     */
    public List<textItem> loadText(){ //list
        List<textItem>list=new ArrayList<textItem>();
        Cursor cursor=db.query("MyText",null,null,null,null,null,null);
        if(cursor.moveToNext()){
            do {
                textItem item=new textItem();
                item.setId(cursor.getInt(cursor.getColumnIndex("id")));
                item.setContent(cursor.getString(cursor.getColumnIndex("text_content")));
                item.setTitle(cursor.getString(cursor.getColumnIndex("text_content")));
                item.setUnitId(cursor.getInt(cursor.getColumnIndex("unit_id")));
                item.setLessonId(cursor.getInt(cursor.getColumnIndex("lesson_id")));
                list.add(item);
            }while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * 删除
     */
    public void deleteALL(int newsTypt)//删除某一单元所有课文
    {

    }

}
