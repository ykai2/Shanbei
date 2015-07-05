package com.zhy.shanbei.adapter;

import com.zhy.shanbei.R;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.format.DateUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.zhy.shanbei.model.textItem;

import java.util.Date;
import java.util.List;

/**
 * Created by ykai on 2015/7/1.
 */
public class ItemAdapter extends BaseAdapter{
    private LayoutInflater layoutInflater;
    private List<textItem> mdatas;

    private ImageLoader imageLoader=ImageLoader.getInstance();
    private DisplayImageOptions options;

    public ItemAdapter(Context context,List<textItem>datas){
        this.mdatas =datas;
        layoutInflater=LayoutInflater.from(context);

        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options=new DisplayImageOptions.Builder().showImageOnFail(R.drawable.images)
                .showImageForEmptyUri(R.drawable.images).showStubImage(R.drawable.images).cacheInMemory()
                .cacheOnDisc().displayer(new RoundedBitmapDisplayer(20)).displayer(new FadeInBitmapDisplayer(300))
                .build();

    }

    public void addAll(List<textItem>mDatas){
        this.mdatas.addAll(mDatas);
    }

    @Override
    public int getCount(){
        return mdatas.size();
    }

    @Override
    public Object getItem(int position){
        return mdatas.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position ,View convertView,ViewGroup parent){
        ViewHolder holder=new ViewHolder();// null;
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.news_item_yidong,null) ;
           // holder=new ViewHolder();

      //      holder.mContent=(TextView)convertView.findViewById(R.id.id_content);
            holder.mTitle=(TextView)convertView.findViewById(R.id.id_title);
            holder.mDate=(TextView)convertView.findViewById(R.id.id_date);
          //  holder.mImg=(TextView)convertView.findViewById(R.id.id_newsImg);
            convertView.setTag(holder);
        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }
        textItem item=mdatas.get(position);
        holder.mTitle.setText(item.getTitle());
 //       String str =item.getContent();
    //   SpannableStringBuilder style=new SpannableStringBuilder(str);
      //  style.setSpan(new BackgroundColorSpan(Color.RED),3,5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
      //  style.setSpan(new ForegroundColorSpan(Color.RED),3,5, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

     //   holder.mContent.setText("How does the writer like to treat young people?");
        holder.mDate.setText("lesson "+item.getId());
       //翻页
       /*
       if(){

        }
        else{

        }*/
        return convertView;
    }

    private final class ViewHolder{
        TextView mTitle;
   //     TextView mContent;
   //     ImageView mImg;
        TextView mDate;
    }

}
