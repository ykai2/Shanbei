package com.zhy.shanbei.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zhy.shanbei.model.Texts;
import com.zhy.shanbei.model.Words;
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
        SqlHelper sqlHelper=new SqlHelper(context,DB_NAME,null,VERSION);
        db=sqlHelper.getWritableDatabase();
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
    public void saveText(Texts text){  //add
        if(text!=null){
            ContentValues contentValues=new ContentValues();
            contentValues.put("text_title",text.getTitle());
            contentValues.put("text_content",text.getContent());
            contentValues.put("lesson_id",text.getLessonId());
            contentValues.put("unit_id",text.getUnitId());
            db.insert("MyText",null,contentValues);
        }
    }

    public void saveTextAll(List<Texts> texts) {
        if (texts != null) {
            for (Texts t : texts) {
                saveText(t);
            }
        }
    }


    /**
     * 从数据库获取所有 texts
     */
    public List<Texts> loadText(){ //list
        List<Texts>list=new ArrayList<Texts>();
        Cursor cursor=db.query("MyText",null,null,null,null,null,null);
        if(cursor.moveToNext()){
            do {
                Texts text=new Texts();
                text.setId(cursor.getInt(cursor.getColumnIndex("id")));
                text.setContent(cursor.getString(cursor.getColumnIndex("text_content")));
                text.setTitle(cursor.getString(cursor.getColumnIndex("text_title")));
                text.setUnitId(cursor.getInt(cursor.getColumnIndex("unit_id")));
                text.setLessonId(cursor.getInt(cursor.getColumnIndex("lesson_id")));
                list.add(text);
            }while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * 从数据库获取制定单元（type）的 texts
     */
    public List<Texts> loadTextByUnit(int type){ //list
        List<Texts>list=new ArrayList<Texts>();

        String selection = "unit_id=?";    // 查找单元Id号
        String[] selectionArgs = new String[]{type+""};  // Id号等于 type

        Cursor cursor=db.query("MyText",null,selection,selectionArgs,null,null,null,null);
        if(cursor.moveToNext()){
            do {
                Texts text=new Texts();
                text.setId(cursor.getInt(cursor.getColumnIndex("id")));
                text.setContent(cursor.getString(cursor.getColumnIndex("text_content")));
                text.setTitle(cursor.getString(cursor.getColumnIndex("text_title")));
                text.setUnitId(cursor.getInt(cursor.getColumnIndex("unit_id")));
                text.setLessonId(cursor.getInt(cursor.getColumnIndex("lesson_id")));
                list.add(text);
            }while (cursor.moveToNext());
        }
        return list;
    }


    /**
     * 根据课文Id，获取课文信息
     * @param id
     * @return
     */
    public Texts loadOneText(int id){

        Texts text=new Texts();
        String selection = "id=?";
        String[] selectionArgs = new String[]{id+""};
        Cursor cursor=db.query("MyText",null,selection,selectionArgs,null,null,null,null);
        if(cursor.moveToNext()){
            text.setId(cursor.getInt(cursor.getColumnIndex("id")));
            text.setContent(cursor.getString(cursor.getColumnIndex("text_content")));
            text.setTitle(cursor.getString(cursor.getColumnIndex("text_title")));
            text.setUnitId(cursor.getInt(cursor.getColumnIndex("unit_id")));
            text.setLessonId(cursor.getInt(cursor.getColumnIndex("lesson_id")));

        }
        return text;
    }


    /**
     *从数据库中加载单词表
     * @return
     */
    public List<Words> loadWords(){

        List<Words> list=new ArrayList<Words>();
        Cursor cursor=db.query("MyWDL",null,null,null,null,null,null);

        while (cursor.moveToNext()){
            Words item=new Words();
            item.setLevel(cursor.getInt(cursor.getColumnIndex("lel")));
            item.setWd(cursor.getString(cursor.getColumnIndex("wds")));
            list.add(item);
        }
        return list;
    }


    /**
     * 把单词表存入数据库
     */
    public void saveWDAll(List<Words> words){
        if(words!=null){
            for(Words w:words) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("wds", w.getWd());
                contentValues.put("lel", w.getLevel());
                db.insert("MyWDL", null, contentValues);
            }
        }
    }
}
