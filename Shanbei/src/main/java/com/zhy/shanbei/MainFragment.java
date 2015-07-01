package com.zhy.shanbei;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhy.shanbei.adapter.ItemAdapter;
import com.zhy.shanbei.bll.textItemBll;
import com.zhy.shanbei.model.textItem;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import me.maxwin.view.XListView;
import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
@SuppressLint("ValidFragment")
public class MainFragment extends Fragment implements IXListViewRefreshListener,IXListViewLoadMore
{

	private int newsType = 1;// 当前单元Id
	private textItemBll itemBll; //课文处理业务
	private XListView xListView;// 扩展的ListView
	private ItemAdapter itemAdapter; // 数据适配器
	List<textItem> mDatas = new ArrayList<textItem>();


	public MainFragment(int newsType)
	{
		this.newsType = newsType;
		itemBll=new textItemBll();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
	//	mDatas =itemBll.getTextItem(1);
		itemAdapter=new ItemAdapter(getActivity(),mDatas);

		xListView=(XListView)getView().findViewById(R.id.id_xlistView);
		xListView.setAdapter(itemAdapter);
	//	xListView.setPullRefreshEnable(this);
		xListView.setPullLoadEnable(this);
		xListView.disablePullRefreash();
		xListView.disablePullLoad();
	//	xListView.startRefresh();

//		xListView

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
		new LoadDataTask().execute();
	}
	@Override
	public void onLoadMore(){
		//TODO Auto-generated method stup
		try{
			List<textItem>items=itemBll.getTextItem(newsType);
			mDatas=items;
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	class LoadDataTask extends AsyncTask<Void,Void,Void>{
		@Override
		protected Void doInBackground(Void... params){
			try{
				List<textItem>items=itemBll.getTextItem(newsType);
				mDatas=items;
			}catch (Exception e){
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected  void onPostExecute(Void result){
			itemAdapter.addAll(mDatas);
			itemAdapter.notifyDataSetChanged();
			xListView.stopRefresh();
		}
	}


}
