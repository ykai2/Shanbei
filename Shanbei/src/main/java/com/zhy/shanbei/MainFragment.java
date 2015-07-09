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
	private int newsType = 1;// 当前单元Id
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

		mDatas =itemBll.queryTXTS(this.getActivity(),shanbeiDB)   ;//.getTextItem(1);
			Log.e("aaa","mainfragment");
		itemAdapter=new ItemAdapter(getActivity(),mDatas);
		/**
		 * 初始化
		 */
		xListView=(XListView)getView().findViewById(R.id.id_xlistView);
		xListView.setAdapter(itemAdapter);
		xListView.setPullRefreshEnable(this);
		xListView.setPullLoadEnable(this);
		xListView.setRefreshTime("just now");//AppUtil.getRefreashTime(getActivity(),newsType));
	//	xListView.disablePullRefreash();
	//	xListView.disablePullLoad();
		xListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				textItem item=mDatas.get((position-1)%7);
				Intent intent=new Intent(getActivity(),TxtContentActivity.class);
				Log.e("aaa",item.getId()+"||url:"+(            (              (item.getId()-1)         %7)         +1)  );
				intent.putExtra("url",(            (              (item.getId()-1)         %7)         +1)       );

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
	//	View view = inflater.inflate(R.layout.tab_item_fragment_main, null);
	//	TextView tip = (TextView) view.findViewById(R.id.id_tip);
	//	tip.setText(TabAdapter.TITLES[newsType]);
	//	return view;
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

		try{
			List<textItem>items=itemBll.queryTXTS(this.getActivity(),shanbeiDB);//    getTextItem(newsType);
			mDatas=items;
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	class LoadDataTask extends AsyncTask<Integer,Void,Integer> {
		@Override
		protected Integer doInBackground(Integer... params) {
/*			try{
				List<textItem>items=itemBll.getTextItem(newsType);
				mDatas=items;
			}catch (Exception e){
				e.printStackTrace();
			}
*/
			switch (params[0]) {
				case LOAD_MORE:
					loadMoreData();
					break;
				case LOAD_REFREASH:
					return refreashData();
			}
			return -1;
		}

		@Override
		protected void onPostExecute(Integer result) {
			switch (result) {
				//	case TIP_ERROR_NO_NETWORK:
				//	Toast
				// 没有网络
				//	case TIP_ERROR_SERVER:
				// 服务器错误
				default:
					break;

			}
			xListView.setRefreshTime("just now");//AppUtil.getRefreashTime(getActivity()), newsType);
			xListView.stopRefresh();
			xListView.stopLoadMore();
		}
	}

	/**
	 * 下拉刷新
	 */
	public Integer refreashData(){
//			if(NetNtil.checkNet(getActivity()))
//{ //从网络中读取  }
//			else
		{//从数据库中读取
			//		isConnNet=false;
			//			isLoadingDataFromNetWork=false;
			//
			//			List<textItem> items=shanbeiDB.list(newsType,currentPage);
			//			mDatas=items;
			//			return TIP_ERROR_NO_NETWORK;
		}
		return -1;

	}


	/**
	 * 上拉加载
	 * @return
	 */
	public void loadMoreData(){
//			if(isLoadingDataFromNetWork){
		//从网络中读取数据
//			}
//			else{
		//从数据库中加载
		// 多加载一页
		Log.e("aaa","loadMoreData");
		currentPage +=1;    //页码 + 1
			List<textItem>items=shanbeiDB.loadText();//newsType,currentPage); //传入单元号和页码
			itemAdapter.addAll(items);


//			}
	}


/**
 * 第一次运行初始化单词列表
 */

public void init_WD(View view){

//	progressBar.setVisibility(View.VISIBLE);
	new LoadDataTask_init().execute();
//        mDatas = itemBll.getOneText(shanbeiDB,2);// .getTextItem(1);
}
class LoadDataTask_init extends AsyncTask<Void,Void,Void>{

	@Override
	protected Void doInBackground(Void... params) {
		try {
			//根据Id传入课文
			// mDatas = mNewsItemBiz.getNews(url).getNewses();
//                lel=params[0];

			Log.e("aaa","开始加载单词");
			WordBll wordBll=new WordBll();
			wordBll.initWDS(getActivity(),shanbeiDB);

		//	mDatas = itemBll.getOneText_level(context,shanbeiDB,txtId,lel);// .getTextItem(1);

			Log.e("aaa","单词加载完成");
		}catch (Exception e)
		{

		}
		return null;
	}

	@Override
	protected void onPostExecute(Void aVoid) {
//		if(mDatas==null)
			return;
//            contentAdapter
//		contentAdapter.addList(mDatas);
//		contentAdapter.notifyDataSetChanged();
//		progressBar.setVisibility(View.GONE);
	}
}


}
