package com.zhy.shanbei.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.zhy.shanbei.activity.TxtContentActivity;
import com.zhy.shanbei.adapter.ItemAdapter;
import com.zhy.shanbei.bll.TextBll;
import com.zhy.shanbei.bll.WordBll;
import com.zhy.shanbei.db.ShanbeiDB;
import com.zhy.shanbei.model.Texts;
import com.zhy.shanbei.R;

import android.widget.AdapterView.OnItemClickListener;
import java.util.ArrayList;
import java.util.List;

import me.maxwin.view.XListView;
import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
@SuppressLint("ValidFragment")
public class MainFragment extends Fragment implements IXListViewRefreshListener,IXListViewLoadMore
{

    private static final int LOAD_MORE=0x110;
    private static final int LOAD_REFREASH=0x111;

    /**
     * 是否第一次进入
     */
    private boolean isFirstIn=true;
    /**
     * 默认课文单元号
     */
    private int newsType;// 当前单元Id
    private TextBll textBll; //课文处理业务
    private ShanbeiDB shanbeiDB;
    private XListView xListView;// 扩展的ListView
    private ItemAdapter itemAdapter; // 数据适配器
    List<Texts> mDatas = new ArrayList<Texts>();


    public MainFragment(int newsType)
    {
        this.newsType = newsType;
        textBll=new TextBll();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        /**
         * 第一个列表的数据
         */
        shanbeiDB=new ShanbeiDB(getActivity());
        mDatas =textBll.queryTexts(this.getActivity(),shanbeiDB,newsType);
        itemAdapter=new ItemAdapter(getActivity(),mDatas);
        /**
         * 初始化
         */
        xListView=(XListView)getView().findViewById(R.id.id_xlistView);
        xListView.setAdapter(itemAdapter);
        xListView.setPullRefreshEnable(this);
        xListView.setPullLoadEnable(this);
        xListView.setRefreshTime("第一次运行，正在配置数据...");
        xListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Texts text=mDatas.get(position-1);
                Intent intent=new Intent(getActivity(),TxtContentActivity.class);
                intent.putExtra("lessonId",text.getId()    );
                startActivity(intent);
            }
        });
        if(isFirstIn)
        {
            /**
             * 进来时刷新
             */
            initWordList();// 解析单词表
            xListView.startRefresh();
            isFirstIn=false;
        }
        else
        {
            xListView.NotRefreshAtBegin();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return 		inflater.inflate(R.layout.tab_item_fragment_main, null);
    }

    @Override
    public void onRefresh(){
        new LoadDataTask().execute(LOAD_REFREASH);
    }
    @Override
    public void onLoadMore(){
        //TODO Auto-generated method stup
        new LoadDataTask().execute(LOAD_MORE);
    }

    class LoadDataTask extends AsyncTask<Integer,Void,Integer> {
        @Override
        protected Integer doInBackground(Integer... params) {

            switch (params[0]) {
                case LOAD_MORE:
                    break;
                case LOAD_REFREASH:
                    return refreashData();
            }
            return -1;
        }

        @Override
        protected void onPostExecute(Integer result) {
            switch (result) {
                default:
                    break;
            }
            xListView.setRefreshTime("ShanBei");
            xListView.stopRefresh();
            xListView.stopLoadMore();
        }
    }

    /**
     * 下拉刷新
     */
    public Integer refreashData(){ // 空的方法
        return -1;
    }

    /**
     * 第一次运行初始化单词列表
     */
    public void initWordList(){
        new LoadWordTask().execute();
    }
    // 异步解析加载单词表
    class LoadWordTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            try {
                WordBll wordBll=new WordBll();
                wordBll.initWords(getActivity(),shanbeiDB);
            }catch (Exception e){

            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            return;
        }
    }


}
