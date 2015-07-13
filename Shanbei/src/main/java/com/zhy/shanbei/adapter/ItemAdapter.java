package com.zhy.shanbei.adapter;

import com.zhy.shanbei.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.zhy.shanbei.model.Texts;

import java.util.List;

/**
 * Created by ykai on 2015/7/1.
 */
public class ItemAdapter extends BaseAdapter{
    private LayoutInflater layoutInflater;
    private List<Texts> mDatas;

    private ImageLoader imageLoader=ImageLoader.getInstance();
    private DisplayImageOptions options;

    public ItemAdapter(Context context,List<Texts>datas){
        this.mDatas =datas;
        layoutInflater=LayoutInflater.from(context);

        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options=new DisplayImageOptions.Builder().showImageOnFail(R.drawable.images)
                .showImageForEmptyUri(R.drawable.images).showStubImage(R.drawable.images).cacheInMemory()
                .cacheOnDisc().displayer(new RoundedBitmapDisplayer(20)).displayer(new FadeInBitmapDisplayer(300))
                .build();

    }

    public void addAll(List<Texts>mDatas){
        this.mDatas.addAll(mDatas);
    }

    @Override
    public int getCount(){
        return mDatas.size();
    }

    @Override
    public Object getItem(int position){
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position ,View convertView,ViewGroup parent){
        ViewHolder holder=new ViewHolder();
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.news_item_yidong,null) ;

            holder.content=(TextView)convertView.findViewById(R.id.id_content);
            holder.title=(TextView)convertView.findViewById(R.id.id_title);
            holder.lessonId=(TextView)convertView.findViewById(R.id.id_date);
            convertView.setTag(holder);
        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }
        Texts text=mDatas.get(position);
        holder.title.setText(text.getTitle());
        holder.content.setText("How does the writer like to treat young people?");
        holder.lessonId.setText("lesson "+text.getId());

        return convertView;
    }

    private final class ViewHolder{
        TextView title;
        TextView content;
        TextView lessonId;
    }

}
