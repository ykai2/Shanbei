package com.zhy.shanbei.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.shanbei.bean.Txts;
import com.zhy.shanbei.R;
import com.zhy.shanbei.bean.Txts.TxtsType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ykai on 2015/7/3.
 */
public class TxtContentAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Txts> mDatas=new ArrayList<Txts>();

    public TxtContentAdapter(Context context){
        inflater=LayoutInflater.from(context);
    }
    public void addList(List<Txts>datas){
        mDatas.clear();
        mDatas.addAll(datas);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        switch (mDatas.get(position).getType())
        {
            case TxtsType.TITLE:
                return 0;
            case TxtsType.CONTENT:
                return 1;
        }
        return -1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public boolean isEnabled(int position) {
            return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Txts txts=mDatas.get(position);
        ViewHolder holder=new ViewHolder();
        if(null==convertView)
        {
            switch (txts.getType())
            {
                case TxtsType.TITLE:
                    convertView=inflater.inflate(R.layout.news_content_title_item,null);
                    holder.textView=(TextView)convertView.findViewById(R.id.text);
                    break;
                case TxtsType.CONTENT:
                    convertView=inflater.inflate(R.layout.news_content_item,null);
                    holder.textView=(TextView)convertView.findViewById(R.id.text);
                    break;
            }
            convertView.setTag(holder);
        }
        else {
            holder=(ViewHolder)convertView.getTag();
        }
        if(null!=txts)
        {
            switch (txts.getType()) // Type=1，显示title。Type=1，显示课文text
            {
                case TxtsType.TITLE:
                    holder.textView.setText(txts.getTitle());
                    break;
                case TxtsType.CONTENT:
                {
                    String str = txts.getContent();
                    SpannableStringBuilder style = new SpannableStringBuilder(str);

                    List<Integer> list=txts.getIdOfHigh();// list 里存放需高亮单词下标，每两个一组，为一个单词的开始和结束下标

                    for(int i=0;i<list.size();i+=2) {
                        style.setSpan(new ForegroundColorSpan(Color.argb(255,32,158,132)), txts.getIdOfHigh().get(i), txts.getIdOfHigh().get(i+1), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    }
                    holder.textView.setText(style);
                }
                break;
                default:
                    break;
            }
        }
        return convertView;

    }
    private final class ViewHolder {
        TextView textView; //课文
    }
}
