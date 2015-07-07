package com.zhy.shanbei.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import com.zhy.shanbei.R;
import com.zhy.shanbei.adapter.TxtContentAdapter;
import com.zhy.shanbei.bean.Txts;
import com.zhy.shanbei.bll.textItemBll;
import com.zhy.shanbei.db.ShanbeiDB;
import com.zhy.shanbei.util.PopMenu;

import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.XListView;
import android.view.View.OnClickListener;
//import com.zhy.shanbei.util.PopMenu.OnItemClickListener2;
/**
 * Created by ykai on 2015/7/3.
 */
public class TxtContentActivity extends Activity implements IXListViewLoadMore, OnClickListener, PopMenu.OnItemClickListener {
    private XListView xListView;
    /**
     * 该课文的唯一 id
     */
    private View mview;
    private PopMenu popMenu;
    private int txtId;
    private int lel;  //高亮单词等级
    private textItemBll itemBll;
    private List<Txts>mDatas;
    ShanbeiDB shanbeiDB;
    private ProgressBar progressBar;
    private TxtContentAdapter contentAdapter;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content);
        itemBll=new textItemBll();
        lel=3; //默认为3
        context=this.getApplicationContext();
        Bundle extras =getIntent().getExtras();
        txtId=extras.getInt("url");// 当前文章ID
        Log.e("aaa","url:"+txtId);
        contentAdapter=        new TxtContentAdapter(this);
        shanbeiDB=ShanbeiDB.getInstance(this);
        xListView=(XListView)findViewById(R.id.id_listview);
        progressBar=(ProgressBar)findViewById(R.id.id_newsContentPro);
/**
 * 添加下拉菜单
 */
        findViewById(R.id.btn_title_popmenu).setOnClickListener(this);
        // 初始化弹出菜单
        popMenu = new PopMenu(this);
        popMenu.addItems(new String[]{"滤词：1","滤词：2","滤词：3","滤词：4","滤词：5","滤词：6" });
        popMenu.setOnItemClickListener2(this);
        mview=(View)findViewById(R.id.btn_title_popmenu);
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
    public void onClick(View v) {
        if(v.getId() == R.id.btn_title_popmenu){
            popMenu.showAsDropDown(v);
        }
    }

    @Override
    public void onItemClick(int index) {
       // Toast.makeText(this, "item clicked " + index + "!", Toast.LENGTH_SHORT).show();
        switch (index)
        {
            case 1:
                lel=2;
                C_lel1(mview);
                break;
            case 2:
                lel=3;
                C_lel1(mview);
                break;
            case 3:
                lel=4;
                C_lel1(mview);
                break;
            case 4:
                lel=5;
                C_lel1(mview);
                break;
            case 5:
                lel=6;
                C_lel1(mview);
                break;
            case 0:
                lel=1;
                C_lel1(mview);
                break;
            default:
                break;



        }
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

             //   mDatas = itemBll.getOneText(shanbeiDB,txtId);// .getTextItem(1);
                Log.e("aaa",""+lel);
                mDatas = itemBll.getOneText_level(context ,shanbeiDB,txtId,lel);// .getTextItem(1);
                Log.e("aaa",txtId+"xxx");
            }catch (Exception e)
            {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(mDatas==null)
                return;
//            contentAdapter
            contentAdapter.addList(mDatas);
            contentAdapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
        }
    }

    class LoadDataTask2 extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            try {
                //根据Id传入课文
                // mDatas = mNewsItemBiz.getNews(url).getNewses();
//                lel=params[0];

                Log.e("aaa",""+lel);

                mDatas = itemBll.getOneText_level(context,shanbeiDB,txtId,lel);// .getTextItem(1);

                Log.e("aaa",txtId+"xxx");
            }catch (Exception e)
            {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(mDatas==null)
                return;
//            contentAdapter
            contentAdapter.addList(mDatas);
            contentAdapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void back(View view){
        finish();
    }

    // 切换等级
    public void back2(View view){

        progressBar.setVisibility(View.VISIBLE);
        new LoadDataTask2().execute();
//        mDatas = itemBll.getOneText(shanbeiDB,2);// .getTextItem(1);
    }
    public void C_lel1(View view){

        progressBar.setVisibility(View.VISIBLE);
        Log.e("aaa","lel"+lel);
        new LoadDataTask2().execute();
//        mDatas = itemBll.getOneText(shanbeiDB,2);// .getTextItem(1);
    }

}
