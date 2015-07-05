package com.zhy.shanbei;

import android.util.Log;

import com.zhy.shanbei.bll.WordBll;
import com.zhy.shanbei.model.wrs_lvl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("D:\\main.txt"));  //读入文件
        StringBuffer buffer = new StringBuffer();
        String line = null;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);   //  读入 StringBuffer 中
        }
        reader.close();
        String string = buffer.toString();  // 文章 读入 String

        WordBll wordBll=new WordBll();
//        InputStream inputStream = context.getResources().openRawResource(R.raw.a);
        List<wrs_lvl> listDic=new ArrayList<wrs_lvl>();
        wrs_lvl wrsLvl=new wrs_lvl();
        wrsLvl.setWd("the");
        wrsLvl.setLevel(2);
        listDic.add(wrsLvl);

        List<Integer> list = T_2_W(string,listDic,3);
        for (int i=0;i<list.size();i+=2)
        {
            System.out.println(list.get(i)+"||"+list.get(i+1));

        }

    }


    public static List<Integer> T_2_W(String string ,List<wrs_lvl> listDic,int level)
    {
        List<Integer> list=new ArrayList<Integer>();

        Pattern expression = Pattern.compile("[a-zA-Z]+");// 定义正则表达式匹配单词
        Map<String, Integer> map = new TreeMap<String, Integer>();  //新生成 Map
        Map<String,Integer> mapDic=new TreeMap<String, Integer>();
        for(int i=0;i<listDic.size();i++)
        {
            /**
             * 把list 装入map
             * key：value ||   word：level
             */
            mapDic.put(listDic.get(i).getWd(), listDic.get(i).getLevel());
        }




        Matcher matcher = expression.matcher(string);//


        String word = "";
        int times = 0;
        while (matcher.find()) {// 是否匹配单词
            word = matcher.group();// 得到一个单词-树映射的键
           if(mapDic.containsKey(word)&&mapDic.get(word)<=level)  // 该单词在在词典中,且高于
           {
               if (map.containsKey(word)) {// 如果包含该键，单词出现过
                   times = map.get(word);// 得到单词出现的次数
                   map.put(word, times + 1);//记录次数用于查找位置

                   list.add(findSubStringAtInt(string, word, times + 1));
                   list.add(findSubStringAtInt(string, word, times + 1) - 1 + word.length());
        //           System.out.println(times+"]"+word + ":" + findSubStringAtInt(string, word, times + 1)+"||"+(findSubStringAtInt(string, word, times + 1)-1+word.length()));
               } else {
                   map.put(word, 1);// 否则单词第一次出现，添加到映射中
              //     System.out.println(times+"]"+word + ":" + findSubStringAtInt(string, word, times + 1)+"||"+(findSubStringAtInt(string, word, times + 1)-1+word.length()));
                   list.add(findSubStringAtInt(string, word, times + 1));
                   list.add( findSubStringAtInt(string, word, times + 1)-1+word.length()  );
               }
           }
        }
        return list;
    }

    public static int findSubStringAtInt(String string,String substring,int times)
    {
        int id=1;
        int tms=0;
        while (tms<times &&  string.indexOf(substring,id)>=0) {
            id = string.indexOf(substring, id) + substring.length();
            tms++;
        }
        return id-substring.length();

    }



    public void xx(String string)
    {



        /**
         * 传入 String
         */
        Pattern expression = Pattern.compile("[a-zA-Z]+");// 定义正则表达式匹配单词

        Matcher matcher = expression.matcher(string);//
        Map<String, Integer> map = new TreeMap<String, Integer>();  //新生成 Map

        String word = "";
        int times = 0;
        while (matcher.find()) {// 是否匹配单词
            word = matcher.group();// 得到一个单词-树映射的键
            if (map.containsKey(word)) {// 如果包含该键，单词出现过
                times = map.get(word);// 得到单词出现的次数
                map.put(word, times + 1);
            } else {
                map.put(word, 1);// 否则单词第一次出现，添加到映射中
            }
        }

        /*
         * 核心：如何按照TreeMap 的value排序而不是key排序.将Map.Entry放在集合里， 重写比较器，在用
         * Collections.sort(list, comparator);进行 排序
         */

        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(
                map.entrySet());
        /*
         * 重写比较器
         * 取出单词个数（value）比较
         */
        Comparator<Map.Entry<String, Integer>> comparator = new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> left,
                               Map.Entry<String, Integer> right) {
                return (left.getValue()).compareTo(right.getValue());
            }
        };
        Collections.sort(list, comparator);// 排序
        // 打印最多五个
        int last = list.size() - 1;
        for (int i = last; i > last - 5; i--) {
            String key = list.get(i).getKey();
            Integer value = list.get(i).getValue();
            System.out.println(key + " :" + value);
        }

    }

}