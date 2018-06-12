package com.yk.adverte.widget.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yk.adverte.R;
import com.yk.adverte.model.bean.ChargeBean;

import java.util.List;

public class ChargeAdapter extends BaseQuickAdapter<ChargeBean, BaseViewHolder> {

    public ChargeAdapter(@Nullable List<ChargeBean> data) {
        super(R.layout.item_charge_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChargeBean item) {
        helper.setText(R.id.txt_item_charge_title, item.getTitle());
    }
}
