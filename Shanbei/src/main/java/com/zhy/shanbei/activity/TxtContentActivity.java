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

    private ProgressBar progressBar;
    private TxtContentAdapter contentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content);
        itemBll=new textItemBll();

        Bundle extras =getIntent().getExtras();
        txtId=extras.getInt("url");
        contentAdapter=        new TxtContentAdapter(this);

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
                Txts t1=new Txts();
                t1.setType(1);
                t1.setContent("1111144444444441111");
                t1.setTitle("Finding fossil man");
                Txts t2=new Txts();
                t2.setType(2);
                String str="  Lesson 1 \n" +
                        "      Finding fossil man 发现化石人\n" +
                        "\n" +
                        "First listen and then answer the following question.\n" +
                        "听录音，然后回答以下问题。\n" +
                        "\n" +
                        "Why are legends handed down by storytellers useful?\n" +
                        "\n" +
                        "    We can read of things that happened 5,000 years ago in the Near East, where people first learned to write. But there are some parts of the word where even now people cannot write. The only way that they can preserve their history is to recount it as sagas -- legends handed down from one generation of another. These legends are useful because they can tell us something about migrations of people who lived long ago, but none could write down what they did. Anthropologists wondered where the remote ancestors of the Polynesian peoples now living in the Pacific Islands came from. The sagas of these people explain that some of them came from Indonesia about 2,000 years ago.\n" +
                        "    But the first people who were like ourselves lived so long ago that even their sagas, if they had any, are forgotten. So archaeologists have neither history nor legends to help them to find out where the first 'modern men' came from.\n" +
                        "    Fortunately, however, ancient men made tools of stone, especially flint, because this is easier to shape than other kinds. They may also have used wood and skins, but these have rotted away. Stone does not decay, and so the tools of long ago have remained when even the bones of the men who made them have disappeared without trace.\n" +
                        "                              ROBIN PLACE Finding fossil man\n" +
                        "\n" +
                        "\n" +
                        "New words and expressions 生词和短语\n" +
                        "\n" +
                        "    fossil man (title)\n" +
                        "adj. 化石人\n" +
                        "    recount\n" +
                        "v.  叙述\n" +
                        "    saga\n" +
                        "n.  英雄故事\n" +
                        "    legend\n" +
                        "n.  传说，传奇\n" +
                        "    migration \n" +
                        "n.  迁移，移居\n" +
                        "    anthropologist \n" +
                        "n.  人类学家\n" +
                        "    archaeologist \n" +
                        "n.  考古学家\n" +
                        "    ancestor \n" +
                        "n.  祖先\n" +
                        "    Polynesian\n" +
                        "adj.波利尼西亚（中太平洋之一群岛）的\n" +
                        "    Indonesia \n" +
                        "n.  印度尼西亚\n" +
                        "    flint \n" +
                        "n.  燧石\n" +
                        "    rot \n" +
                        "n.  烂掉\n" +
                        "\n" +
                        "参考译文\n" +
                        "    我们从书籍中可读到5,000 年前近东发生的事情，那里的人最早学会了写字。但直到现在,世界上有些地方，人们还不会书写。 他们保存历史的唯一办法是将历史当作传说讲述，由讲述人一代接一代地将史实描述为传奇故事口传下来。人类学家过去不清楚如今生活在太平洋诸岛上的波利尼西亚人的祖先来自何方，当地人的传说却告诉人们：其中一部分是约在2,000年前从印度尼西亚迁来的。\n" +
                        "    但是，和我们相似的原始人生活的年代太久远了，因此，有关他们的传说既使有如今也失传了。于是，考古学家们既缺乏历史记载，又无口头传说来帮助他们弄清最早的“现代人”是从哪里来的。\n" +
                        "    然而， 幸运的是，远古人用石头制作了工具，特别是用燧石，因为燧石较之其他石头更容易成形。他们也可能用过木头和兽皮，但这类东西早已腐烂殆尽。石头是不会腐烂的。因此，尽管制造这些工具的人的骨头早已荡然无存，但远古时代的石头工具却保存了下来。\n";



                t2.setContent(str);
                t2.setTitle("enha");
                List<Txts>ta=new ArrayList<Txts>();
               ta.add(t1);
                ta.add(t2);
                mDatas =ta;// itemBll.getTextItem(1);
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
