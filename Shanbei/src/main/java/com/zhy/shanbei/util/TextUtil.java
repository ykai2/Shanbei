package com.zhy.shanbei.util;

import android.util.Log;

import com.zhy.shanbei.model.Texts;
import com.zhy.shanbei.model.Words;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ykai on 2015/7/4.
 */
public class TextUtil {

    /**
     *
     * @param inputStream
     * @return list(title content)
     */
    public static List<Texts> getTextList(InputStream inputStream) {
        List<Texts> list = new ArrayList<Texts>();
        List<String>listTitle=new ArrayList<String>();
        List<String >listContent=new ArrayList<String>();

        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "gbk");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        boolean flag=false; //是否包含 Lesson


        String line;
        try{
            while ((line = reader.readLine()) != null) {
                if(flag==true)
                {
                    listTitle.add(line);
                }

                if(line.length()>=6 && (line.indexOf("Lesson")>=0)  )
                {
                    if (sb.length()>0)
                    {
                        listContent.add(sb.toString());
                        sb.setLength(0);
                    }
                    flag=true;
                }
                else
                {
                    flag=false;
                    sb.append(line);
                    sb.append("\n");
                }
            }
            listContent.add(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i=0;i<listTitle.size()&&i<listContent.size();i++)
        {
            Texts txt1=new Texts();
            txt1.setTitle(listTitle.get(i));
            txt1.setContent(listContent.get(i));
            list.add(txt1);
        }
        return list;
    }



}
