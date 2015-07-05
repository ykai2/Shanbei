package com.zhy.shanbei.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import com.zhy.shanbei.R;
import com.zhy.shanbei.adapter.TxtContentAdapter;
import com.zhy.shanbei.bean.Txts;
import com.zhy.shanbei.bll.textItemBll;
import com.zhy.shanbei.db.ShanbeiDB;

import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.XListView;

/**
 * Created by ykai on 2015/7/3.
 */
public class TxtContentActivity extends Activity implements IXListViewLoadMore{
    private XListView xListView;
    /**
     * 该课文的唯一 id
     */
    private int txtId;
    private textItemBll itemBll;
    private List<Txts>mDatas;
    ShanbeiDB shanbeiDB;
    private ProgressBar progressBar;
    private TxtContentAdapter contentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content);
        itemBll=new textItemBll();

        Bundle extras =getIntent().getExtras();
        txtId=extras.getInt("url");// 当前文章ID
        contentAdapter=        new TxtContentAdapter(this);
        shanbeiDB=ShanbeiDB.getInstance(this);
        xListView=(XListView)findViewById(R.id.id_listview);
        progressBar=(ProgressBar)findViewById(R.id.id_newsContentPro);

        xListView.setAdapter(contentAdapter);
        xListView.disablePullRefreash();
        xListView.disablePullLoad();

        xListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Txts txts=mDatas.get(position-1);
                //String imageLink
             //   Intent intent=new Intent(TxtContentActivity.this)

            }
        });
        progressBar.setVisibility(View.VISIBLE);
        new LoadDataTask().execute();

    }

    @Override
    public void onLoadMore() {
        //
    }

    class LoadDataTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            try {
                //根据Id传入课文
                // mDatas = mNewsItemBiz.getNews(url).getNewses();
                mDatas = itemBll.getOneText(shanbeiDB,txtId);// .getTextItem(1);
            }catch (Exception e)
            {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(mDatas==null)
                return;
            contentAdapter.addList(mDatas);
            contentAdapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void back(View view){
        finish();
    }
}
