package com.zhy.shanbei;

import com.zhy.shanbei.bll.textItemBll;
import com.zhy.shanbei.model.textItem;

import java.util.List;

/**
 * Created by ykai on 2015/7/1.
 */
public class test {
    public static void main(String[] args) {

        textItemBll itemBll=new textItemBll();
        int unitid=1;
try {
    List<textItem> items = itemBll.getTextItem(unitid);
    for (textItem item : items) {
        System.out.println(item.getTitle()+item.getContent());
    }
}
catch (Exception e)
{
    e.printStackTrace();

}




//        System.out.println("频频袅袅十三余，豆蔻梢头二月初");
    }
}
