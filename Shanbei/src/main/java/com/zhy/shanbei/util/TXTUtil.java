package com.zhy.shanbei.util;

import android.util.Log;

import com.zhy.shanbei.model.TXT;
import com.zhy.shanbei.model.wrs_lvl;

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
public class TXTUtil {

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
            Log.e("aaa", "xxxx");
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
            Log.e("aaa", "xxxx");
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
