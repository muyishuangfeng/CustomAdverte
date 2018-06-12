package com.yk.adverte.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.Marker;
import com.yk.adverte.R;

/**
 * Created by Silence on 2017/11/8.
 */

public class CustomInfoWindowAdapter implements AMap.InfoWindowAdapter {

    private Context context;
    private String address;

    public CustomInfoWindowAdapter(Context context, String address) {
        this.context = context;
        this.address = address;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_info_layout, null);
        setViewContent( view);
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    /**
     * 这个方法根据自己的实体信息来进行相应控件的赋值
     * @param view
     */
    private void setViewContent( View view) {
        ImageView mImgMarker = view.findViewById(R.id.img_marker_icon);
        CircularProgress mPgbMarker = view.findViewById(R.id.pgb_marker);
        TextView mTxtMarker =  view.findViewById(R.id.txt_marker_title);
        if (!TextUtils.isEmpty(address)) {
            mTxtMarker.setText(address);
            mImgMarker.setVisibility(View.VISIBLE);
            mPgbMarker.setVisibility(View.GONE);
        } else {
            mPgbMarker.setVisibility(View.VISIBLE);
            mImgMarker.setVisibility(View.GONE);
            mTxtMarker.setText(context.getResources().getString(R.string.no_data));
        }
    }
}
