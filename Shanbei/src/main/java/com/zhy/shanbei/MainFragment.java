package com.zhy.shanbei;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import com.zhy.shanbei.activity.TxtContentActivity;
import com.zhy.shanbei.adapter.ItemAdapter;
import com.zhy.shanbei.bll.WordBll;
import com.zhy.shanbei.bll.textItemBll;
import com.zhy.shanbei.db.ShanbeiDB;
import com.zhy.shanbei.model.textItem;

import android.widget.AdapterView.OnItemClickListener;
import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.widget.Toast;

import me.maxwin.view.XListView;
import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
@SuppressLint("ValidFragment")
public class MainFragment extends Fragment implements IXListViewRefreshListener,IXListViewLoadMore
{

	private static final int LOAD_MORE=0x110;
	private static final int LOAD_REFREASH=0x111;

	private static final int TIP_ERROR_NO_NETWORK=0x112;
	private static final int TIP_ERROR_SERVER=0x113;
	/**
	 * 是否第一次进入
	 */
	private boolean isFirstIn=true;
	/**
	 * 是否连接网络
	 */
	private boolean isLoadingDataFromNetWork;

	/**
	 * 默认课文单元号
	 */
	private int newsType;// 当前单元Id
	private int currentPage=1;//当前页码 一页六条
	private textItemBll itemBll; //课文处理业务
	private ShanbeiDB shanbeiDB;
	private Context context;
	private XListView xListView;// 扩展的ListView
	private ItemAdapter itemAdapter; // 数据适配器
	List<textItem> mDatas = new ArrayList<textItem>();


	public MainFragment(int newsType)
	{

		this.newsType = newsType;
		//logger

		itemBll=new textItemBll();

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		/**
		 * 第一个列表的数据
		 */
		shanbeiDB=new ShanbeiDB(getActivity());

		mDatas =itemBll.queryTXTS(this.getActivity(),shanbeiDB,newsType)   ;//.getTextItem(1);

		itemAdapter=new ItemAdapter(getActivity(),mDatas);

//		List<textItem>items=shanbeiDB.loadTextCate(newsType);//newsType,currentPage); //传入单元号和页码
//		Log.e("aaa","|||"+newsType);
//		itemAdapter.addAll(items);



		/**
		 * 初始化
		 */
		xListView=(XListView)getView().findViewById(R.id.id_xlistView);
		xListView.setAdapter(itemAdapter);
		xListView.setPullRefreshEnable(this);
		xListView.setPullLoadEnable(this);
		xListView.setRefreshTime("第一次运行，正在配置数据...");//AppUtil.getRefreashTime(getActivity(),newsType));
	//	xListView.disablePullRefreash();
//		xListView.disablePullLoad();
		xListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				textItem item=mDatas.get(position-1);
				Log.e("aaa","item: "+position+"item: "+item.getId());
				Intent intent=new Intent(getActivity(),TxtContentActivity.class);

				intent.putExtra("url",item.getId()    );


				startActivity(intent);
			}
		});
		if(isFirstIn)
		{
			/**
			 * 进来时刷新
			 */
			init_WD(xListView);
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
					//loadMoreData();
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
	public Integer refreashData(){
		{
		}
		return -1;
	}


	/**
	 * 上拉加载
	 * @return
	 */
	public void loadMoreData(){
		List<textItem>items=shanbeiDB.loadTextCate(newsType);//newsType,currentPage); //传入单元号和页码

		itemAdapter.addAll(items);
	}


/**
 * 第一次运行初始化单词列表
 */

public void init_WD(View view){
	new LoadDataTask_init().execute();
}
class LoadDataTask_init extends AsyncTask<Void,Void,Void>{

	@Override
	protected Void doInBackground(Void... params) {
		try {
			WordBll wordBll=new WordBll();
			wordBll.initWDS(getActivity(),shanbeiDB);
		}catch (Exception e)
		{

		}
		return null;
	}

	@Override
	protected void onPostExecute(Void aVoid) {
			return;

	}
}


}
