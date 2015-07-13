package com.zhy.shanbei.bll;

import android.content.Context;
import android.util.Log;

import com.zhy.shanbei.db.ShanbeiDB;
import com.zhy.shanbei.model.Words;
import com.zhy.shanbei.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ykai on 2015/7/5.
 */
public class WordBll {
    /**
     * 初始 单词表
     */
    public void initWords(Context context, ShanbeiDB shanbeiDB){
        List<Words>L= shanbeiDB.loadWords(); //getWDSList(inputStream);
        if(L.size()>0)// 存在就什么也不做
        {
        }
        else  //否则从文本中加载
        {
            //从文本中读
            InputStream inputStream = context.getResources().openRawResource(R.raw.a);
            List<Words>L2= getWordList(inputStream);
            //存到数据库中
            shanbeiDB.saveWDAll(L2);
        }
    }

    /**
     * 读取list（word） 先从数据库中读取，如果没有则从文本中读并存入数据库
     * @return List(word)
     */
    public List<Words> queryWords(Context context, ShanbeiDB shanbeiDB){
        List<Words>L= shanbeiDB.loadWords();
        if(L.size()>0)
        {
            return L;
        }
        else  //数据库中没有则从文本中加载
        {
            //从文本中读
            InputStream inputStream = context.getResources().openRawResource(R.raw.a);
            List<Words>L2= getWordList(inputStream);
            //存到数据库中
            shanbeiDB.saveWDAll(L2);
            return L2;
        }
    }

    /**
     *   InputStream inputStream = getResources().openRawResource(R.raw.a);
     *    getWDSList(inputStream);
     * @param  inputStream
     * @return  list(Words)
     */
    public static List<Words> getWordList(InputStream inputStream) {
        List<Words> list = new ArrayList<Words>();
        List<String>listWord=new ArrayList<String>();   // 储存单词
        List<Integer>listLevel=new ArrayList<Integer>();  // 储存单词等级
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "gbk");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                String word = line.substring(0, line.indexOf("\t"));//获得第一个数字
                String integer = line.substring(line.indexOf("\t") + 1, line.indexOf("\t") + 2);//第二个
                int level = Integer.parseInt(integer);//转为数字
                listLevel.add(level);
                listWord.add(word);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i=0;i<listLevel.size()&&i<listWord.size();i++)
        {
            Words wL=new Words();
            wL.setLevel(listLevel.get(i));
            wL.setWd(listWord.get(i));
            list.add(wL);
        }
        return list;
    }
}
