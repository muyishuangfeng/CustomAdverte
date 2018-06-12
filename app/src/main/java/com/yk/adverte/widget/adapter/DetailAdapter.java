package com.yk.adverte.widget.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yk.adverte.R;
import com.yk.adverte.model.bean.CarBean;
import com.yk.adverte.view.glide.GlideUtils;

import java.util.List;

public class DetailAdapter extends BaseQuickAdapter<CarBean, BaseViewHolder> {


    public DetailAdapter(@Nullable List<CarBean> data) {
        super(R.layout.item_home_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CarBean item) {
        helper.setText(R.id.txt_item_car_name, item.getCar_name());
        GlideUtils.loadBitmapFromUrl(mContext,
                item.getCar_img(),
                R.drawable.errorview,
                R.drawable.errorview,
                DiskCacheStrategy.ALL,
                (ImageView) helper.getView(R.id.img_item_car_photo));
    }
}
