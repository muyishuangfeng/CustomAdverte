package com.yk.adverte.model.bean;

import java.util.ArrayList;
import java.util.List;

public class ChargeBean {

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ChargeBean(String title) {
        this.title = title;
    }

    public static List<ChargeBean> getData() {
        List<ChargeBean> mList = new ArrayList<>();
        mList.add(new ChargeBean("按天收费"));
        mList.add(new ChargeBean("按月收费"));
        mList.add(new ChargeBean("按年收费"));
        return mList;
    }

}
