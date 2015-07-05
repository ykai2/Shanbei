package com.zhy.shanbei.bll;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.zhy.shanbei.MainActivity;
import com.zhy.shanbei.bean.Txts;
import com.zhy.shanbei.util.TXTUtil;
import java.io.InputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import com.zhy.shanbei.R;
import com.zhy.shanbei.db.ShanbeiDB;
import com.zhy.shanbei.model.TXT;
import com.zhy.shanbei.model.textItem;
import com.zhy.shanbei.model.wrs_lvl;

/**
 * Created by ykai on 2015/7/1.
 */
public class textItemBll {




    /**
     *
     * @return list(title)
     */
    public List<String> queryTXT_titleS(Context context, ShanbeiDB shanbeiDB){

        List<textItem>L= shanbeiDB.loadText(); //getWDSList(inputStream);
        List<String >titlelist=new ArrayList<String>();
        int counter=0;
        if(L.size()>0)
        {  Log.e("aaa", "从数据库中读取"+L.size());

            for(textItem ll:L) {
//                textView.setText(textView.getText()+"\n"+(++counter)+"|"+ll.getTitle());
                titlelist.add(ll.getTitle());
            }
            return titlelist;
        }
        else  //数据库中没有则从文本中加载
        {
            Log.e("aaa", "从文本中加载");

            //从文本中读
           // Resources myResources = getResources();
           // InputStream myFile = myResources.openRawResource(R.raw.aa);
            InputStream inputStream = context.getResources().openRawResource(R.raw.aa);


            List<TXT>L2= TXTUtil.getTXTList(inputStream);
            //L2 读入到 L 中
            //存到数据库中

            for(int i=0;i<L2.size();i++)
            {// 12 lesson 一个单元
                textItem LL=new textItem();
                LL.setId(i+1);
                LL.setLessonId(i+1);
                LL.setUnitId(i/12+1);
                LL.setTitle(L2.get(i).getTitle());
                titlelist.add(LL.getTitle());
                //               Log.e("aaa",LL.getTitle());
                LL.setContent(L2.get(i).getContent());
                L.add(LL);

            }


            shanbeiDB.saveTextAll (L);
            Log.e("aaa", "存到数据库中"+L2.size());
            return titlelist;
        }
    }


    public List<textItem> queryTXTS(Context context,ShanbeiDB shanbeiDB){

        List<textItem>L= shanbeiDB.loadText(); //getWDSList(inputStream);
        int counter=0;
        if(L.size()>0)
        {  Log.e("aaa", "从数据库中读取"+L.size());

            // for(textItem ll:L)
            {
                //     textView.setText(textView.getText()+"\n"+(++counter)+"|"+ll.getTitle());
            }
            return L;
        }
        else  //数据库中没有则从文本中加载
        {
            Log.e("aaa", "从文本中加载");

            //从文本中读
            InputStream inputStream =context.getResources().openRawResource(R.raw.aa);


            List<TXT>L2= getTXTList(inputStream);
            //L2 读入到 L 中
            //存到数据库中

            for(int i=0;i<L2.size();i++)
            {// 12 lesson 一个单元
                textItem LL=new textItem();
                LL.setId(i+1);
                LL.setLessonId(i+1);
                LL.setUnitId(i/12+1);
                LL.setTitle(L2.get(i).getTitle());
                //               Log.e("aaa",LL.getTitle());
                LL.setContent(L2.get(i).getContent());
                L.add(LL);

            }


            shanbeiDB.saveTextAll (L);
            Log.e("aaa", "存到数据库中"+L2.size());
            return L;
        }
    }



    /**
     *
     * @param inputStream
     * @return list(title content)
     */
    public static List<TXT> getTXTList(InputStream inputStream) {
        List<TXT> list = new ArrayList<TXT>();
        List<String>listTLT=new ArrayList<String>();
        List<String >listCT=new ArrayList<String>();

        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "gbk");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        boolean flag=false; //改行是否包含 Lesson

        int txtCounter=0;
        String line;
        try {
            while ((line = reader.readLine()) != null)
            {


                if(flag==true)
                {
                    listTLT.add(line);
                }

                if(line.length()>=6 && (line.indexOf("Lesson")>=0)  )
                {
                    if (sb.length()>0)
                    {
                        listCT.add(sb.toString());
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
            listCT.add(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();

        }
        for(int i=0;i<listTLT.size()&&i<listCT.size();i++)
        {
            TXT txt1=new TXT();
            txt1.setTitle(listTLT.get(i));
            txt1.setContent(listCT.get(i));

            list.add(txt1);
        }

        //    return sb.toString();
        return list;
    }


    public List<Txts> getOneText(ShanbeiDB shanbeiDB,int id)
    {

        List<Txts> txt=new ArrayList<Txts>();
        Txts t=new Txts();
        t.setContent(shanbeiDB.loadOneText(id).getContent());
       // items.add(shanbeiDB.loadOneText(id));
        t.setType(2);
        txt.add(t);
        return txt;






       // return items;
    }
}
