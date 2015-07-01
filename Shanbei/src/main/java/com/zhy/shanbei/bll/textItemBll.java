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
        List<textItem> textItems=new ArrayList<textItem>();

        // 加入数据用于测试
        textItem text1=new textItem();
        text1.setId(1);
        text1.setUnitId(1);
        text1.setLessonId(1);
        text1.setTitle("First");
        text1.setContent("blbalbla");
        textItems.add(text1);
        textItem text2=new textItem();
        text2.setId(2);
        text2.setUnitId(1);
        text2.setLessonId(2);
        text2.setTitle("Second");
        text2.setContent("blbalbla");
        textItems.add(text2);


        return textItems;
    }
}
