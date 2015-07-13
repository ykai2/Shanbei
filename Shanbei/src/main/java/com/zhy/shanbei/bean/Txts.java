package com.zhy.shanbei.bean;

import java.util.List;

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
     * 内容
     */
    private String content;
    /**
     * 类型
     */
    private int type;
    private List<Integer>  idOfHigh; // 储存高亮单词下表，list中个数为偶数个，每两个为一个单词的开始和结束

    public List<Integer> getIdOfHigh() {
        return  idOfHigh;
    }

    public void setIdOfHigh(List<Integer> idOfHigh) {
        this.idOfHigh = idOfHigh;
    }

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
