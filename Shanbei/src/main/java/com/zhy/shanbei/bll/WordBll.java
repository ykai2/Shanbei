package com.zhy.shanbei.bll;

import android.content.Context;
import android.util.Log;

import com.zhy.shanbei.db.ShanbeiDB;
import com.zhy.shanbei.model.wrs_lvl;
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
    public void initWDS(Context context, ShanbeiDB shanbeiDB){


        List<wrs_lvl>L= shanbeiDB.loadWDS(); //getWDSList(inputStream);
        int counter=0;

        if(L.size()>0)// 存在就什么也不做
        {  Log.e("aaa", "存在什么也不做" + L.size());

            //  for(wrs_lvl ll:L) {
            //    textView.setText(textView.getText()+"\n"+(++counter)+"|"+ll.getLevel() + ":" + ll.getWd());
            // }
          //  return L;
        }
        else  //数据库中没有则从文本中加载
        {
            Log.e("aaa", "从文本中加载");

            //从文本中读
            InputStream inputStream = context.getResources().openRawResource(R.raw.a);


            List<wrs_lvl>L2= getWDSList(inputStream);

            //存到数据库中
            shanbeiDB.saveWDAll(L2);
            Log.e("aaa", "存到数据库中"+L2.size());
  //          return L2;
        }
    }





    /**
     * 读取list（word） 先从数据库中读取，如果没有则从文本中读并存入数据库
     * @return List(word)
     */
    public List<wrs_lvl> queryWDS(Context context, ShanbeiDB shanbeiDB){


        List<wrs_lvl>L= shanbeiDB.loadWDS(); //getWDSList(inputStream);
        int counter=0;

        if(L.size()>0)
        {  Log.e("aaa", "从数据库中读取" + L.size());

            //  for(wrs_lvl ll:L) {
            //    textView.setText(textView.getText()+"\n"+(++counter)+"|"+ll.getLevel() + ":" + ll.getWd());
            // }
            return L;
        }
        else  //数据库中没有则从文本中加载
        {
            Log.e("aaa", "从文本中加载");

            //从文本中读
            InputStream inputStream = context.getResources().openRawResource(R.raw.a);


            List<wrs_lvl>L2= getWDSList(inputStream);

            //存到数据库中
            shanbeiDB.saveWDAll(L2);
            Log.e("aaa", "存到数据库中"+L2.size());
            return L2;
        }
    }


    /**
     *   InputStream inputStream = getResources().openRawResource(R.raw.a);
     *    getWDSList(inputStream);
     * @param  inputStream
     * @return  list(wrs_lvl)
     */
    public static List<wrs_lvl> getWDSList(InputStream inputStream) {
        List<wrs_lvl> list = new ArrayList<wrs_lvl>();
        List<String>listWD=new ArrayList<String>();
        List<Integer> listL=new ArrayList<Integer>();
        wrs_lvl wrsLvl = new wrs_lvl();

        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "gbk");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        //    StringBuffer sb = new StringBuffer("");
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                /**
                 *
                 */
                String int1 = line.substring(0, line.indexOf("\t"));//获得第一个数字
                String int2 = line.substring(line.indexOf("\t") + 1, line.indexOf("\t") + 2);//第二个
                int i2 = Integer.parseInt(int2);//转为数字
                // Log.e("aaa", int1 + "  :  " + i2);
                //      wrsLvl.setWd(int1);
                //    wrsLvl.setLevel(i2);
                //  list.add(wrsLvl);
                listL.add(i2);
                listWD.add(int1);
                //               sb.append(line);
                //             sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        //    return sb.toString();
        for(int i=0;i<listL.size()&&i<listWD.size();i++)
        {
            wrs_lvl wL=new wrs_lvl();
            wL.setLevel(listL.get(i));
            wL.setWd(listWD.get(i));
            list.add(wL);
        }
        return list;
    }

}
