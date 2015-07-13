package com.zhy.shanbei.activity;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import com.zhy.shanbei.R;
import com.zhy.shanbei.adapter.TxtContentAdapter;
import com.zhy.shanbei.bean.Txts;
import com.zhy.shanbei.bll.TextBll;
import com.zhy.shanbei.db.ShanbeiDB;
import com.zhy.shanbei.util.PopMenu;

import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

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
    private TextBll textBll;
    private TextView textViewBack;
    private List<Txts>mDatas;
    private ShanbeiDB shanbeiDB;
    private ProgressBar progressBar;
    private TxtContentAdapter contentAdapter;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content);

        textBll=new TextBll();
        lel=3; //默认为3
        context=this.getApplicationContext();
        Bundle extras =getIntent().getExtras();
        txtId=extras.getInt("lessonId");// 当前文章ID
        textViewBack=(TextView)findViewById(R.id.headT);

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

    /**
     * 点击下弹菜单子项方法
     * @param index
     */
    @Override
    public void onItemClick(int index) {
        switch (index)
        {
            case 0:
                lel=1;  // 设置高亮单词等级
                changeWordLevel(mview);
                break;
            case 1:
                lel=2;
                changeWordLevel(mview);
                break;
            case 2:
                lel=3;
                changeWordLevel(mview);
                break;
            case 3:
                lel=4;
                changeWordLevel(mview);
                break;
            case 4:
                lel=5;
                changeWordLevel(mview);
                break;
            case 5:
                lel=6;
                changeWordLevel(mview);
                break;
            default:
                break;
        }
    }
    @Override
    public void onLoadMore() {
    }

    public void back(View view){
        finish();
    }

    /**
     *   根据当前 lel（需高亮单词等级）重新加载课文
     * @param view
     */
    public void changeWordLevel(View view){
        progressBar.setVisibility(View.VISIBLE);
        new LoadDataTask().execute();
    }

    /**
     * 异步加载课文
     */
    class LoadDataTask extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... params) {
            try {
                mDatas = textBll.getOneText(context,shanbeiDB,txtId,lel);
            }catch (Exception e) {

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


}
