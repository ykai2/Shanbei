package com.zhy.shanbei.bll;

import java.util.ArrayList;
import java.util.List;

import com.zhy.shanbei.model.textItem;

/**
 * Created by ykai on 2015/7/1.
 */
public class textItemBll {
    public List<textItem>getTextItem(int unitId)
    {
        List<textItem> items=new ArrayList<textItem>();

        // 加入数据用于测试
        textItem text1=new textItem();
        text1.setId(1);
        text1.setUnitId(1);
        text1.setLessonId(1);
        text1.setTitle("Finding fossil man");
        text1.setContent("Why are legends handed down by storytellers useful?");
        items.add(text1);
        items.add(text1);
        items.add(text1);
        items.add(text1);
        items.add(text1);
        items.add(text1);





        return items;
    }
}
