package com.zhy.shanbei.bll;

/**
 * Created by ykai on 2015/7/13.
 */

import android.content.Context;

import com.zhy.shanbei.R;
import com.zhy.shanbei.bean.Txts;
import com.zhy.shanbei.db.ShanbeiDB;
import com.zhy.shanbei.model.Texts;
import com.zhy.shanbei.model.Words;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zhy.shanbei.util.TextUtil;

/**
 * Created by ykai on 2015/7/1.
 */
public class TextBll {


    //type 表示课文单元Id
    public List<Texts> queryTexts(Context context,ShanbeiDB shanbeiDB,int type){
        List<Texts>texts= shanbeiDB.loadTextByUnit(type); //getWDSList(inputStream);
        if(texts.size()>0)
        {
            return texts;
        }
        else  //数据库中没有则从文本中加载
        {
            InputStream inputStream =context.getResources().openRawResource(R.raw.aa);
            List<Texts>L2= TextUtil.getTextList(inputStream);
            //L2 读入到 L 中
            //存到数据库中
            for(int i=0;i<L2.size();i++)
            {
                Texts text=new Texts();
                text.setId(i+1);
                text.setLessonId(i+1);
                text.setUnitId(i/12+1);
                text.setTitle(L2.get(i).getTitle());
                text.setContent(L2.get(i).getContent());
                texts.add(text);
            }
            shanbeiDB.saveTextAll (texts);
            texts= shanbeiDB.loadTextByUnit(type);
            return texts;
        }
    }

    public List<Txts> getOneText(Context context,ShanbeiDB shanbeiDB,int id,int lel){
        List<Txts> txts=new ArrayList<Txts>();
        Txts txt=new Txts();
        txt.setContent(shanbeiDB.loadOneText(id).getContent());
        WordBll wordBll=new WordBll();
        List<Integer> LI = new ArrayList<Integer>();
        List<Words> listDic=wordBll.queryWords(context,shanbeiDB );
        LI=getTextHighLightIndex(shanbeiDB.loadOneText(id).getContent(),listDic,lel);
        txt.setIdOfHigh(LI);
        txt.setType(2);// 2表示全文
        txts.add(txt);
        return txts;
    }

    /**
     *
     * @param string 表示一篇文章
     * @param listDic 一个词典 list
     * @param level 词典的等级
     * @return  这篇文章中出现在词典中（等级小于等于给定等级）的单词下标。每两个是一组，一组表示一个单词
     */

    public static List<Integer> getTextHighLightIndex(String string ,List<Words> listDic,int level) {
        List<Integer> list=new ArrayList<Integer>();
        Pattern expression = Pattern.compile("[a-zA-Z]+");// 定义正则表达式匹配单词
        Map<String, Integer> map = new TreeMap<String, Integer>();  //新生成 Map
        Map<String,Integer> mapDic=new TreeMap<String, Integer>();
        for(int i=0;i<listDic.size();i++)
        {
            /**
             * 把词典装入 mapDic
             *  map( word , level )
             */
            mapDic.put(listDic.get(i).getWd(), listDic.get(i).getLevel());
        }

        Matcher matcher = expression.matcher(string);
        String word = "";
        int times = 0;
        while (matcher.find()) {// 从课文中取出一个单词
            word = matcher.group();// 得到一个单词-树映射的键
            if(mapDic.containsKey(word)&&mapDic.get(word)<=level)  // 该单词在在词典中,且该单词等级需要高亮
            {
                if (map.containsKey(word)) {// 如果包含该键，单词出现过
                    times = map.get(word);// 得到单词出现的次数
                    map.put(word, times + 1);//记录次数用于查找位置
                    list.add(findSubStringAtTimes(string, word, times + 1));
                    list.add(findSubStringAtTimes(string, word, times + 1) + word.length());

                } else {
                    map.put(word, 1);// 否则单词第一次出现，添加到映射中
                    list.add(findSubStringAtTimes(string, word, times + 1));
                    list.add( findSubStringAtTimes(string, word, times + 1)+word.length()  );
                }
            }
        }
        return list;
    }

    /**
     * 查找子串第 n 次出现的位置下标
     * @param string
     * @param substring
     * @param times
     * @return
     */
    public static int findSubStringAtTimes(String string,String substring,int times) {
        int id=1;
        int tms=0;
        while (tms<times &&  string.indexOf(substring,id)>=0) {
            id = string.indexOf(substring, id) + substring.length();
            tms++;
        }
        return id-substring.length();
    }

}
