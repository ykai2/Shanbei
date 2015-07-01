package com.zhy.shanbei.model;

/**
 * Created by ykai on 2015/7/1.
 */
public class textItem {
    private int id;        //课文唯一Id
    private int unitId;    //课文单元Id
    private int lessonId;  //课文课时Id
    private String title;  //课文标题
    private String content;//课文内容

    //  .......             //课文翻译，重点词等暂归入课文内容中

    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return id;
    }

    public void setUnitId(int unitId){
        this.unitId=unitId;
    }
    public int getUnitId(){
        return unitId;
    }

    public void setLessonId(int lessonId){
        this.lessonId=lessonId;
    }
    public int getLessonId(){
        return lessonId;
    }

    public void setTitle(String title){
        this.title=title;
    }
    public String getTitle(){
        return title;
    }

    public void setContent(String content){
        this.content=content;
    }
    public String getContent(){
        return content;
    }
}
