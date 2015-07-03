package com.zhy.shanbei.bean;

/**
 * Created by ykai on 2015/7/3.
 */
public class Txts {
    public static interface TxtsType{
        public static final int TITLE = 1;
        public static final int CONTENT=2;
    }

    /**
     * 标题
     */
    private String title;
    /**
     * 摘要
     */
    private String content;
    /**
     * 类型
     */
    private int type;
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title=title;
    }

    public String getContent(){
        return content;
    }
    public void setContent(String content){
        this.content=content;
    }
    public int getType(){
        return type;
    }
    public void setType(int type){
        this.type=type;
    }






}
