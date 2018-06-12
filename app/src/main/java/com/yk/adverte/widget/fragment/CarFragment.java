package com.yk.adverte.widget.fragment;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yk.adverte.R;
import com.yk.adverte.base.BaseFragment;
import com.yk.adverte.common.MsgBox;
import com.yk.adverte.model.HomeModel;
import com.yk.adverte.model.bean.CarBean;
import com.yk.adverte.model.bean.JsonBean;
import com.yk.adverte.net.impl.RequestImpl;
import com.yk.adverte.util.JsonParseUtils;
import com.yk.adverte.util.ToastUtils;
import com.yk.adverte.view.title.CustomTitleBar;
import com.yk.adverte.widget.activity.DetailActivity;
import com.yk.adverte.widget.adapter.DetailAdapter;

import java.util.ArrayList;
import java.util.List;

public class CarFragment extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener,
        DetailAdapter.RequestLoadMoreListener{

    private ArrayList<JsonBean> mProvinceList = new ArrayList<>();
    private ArrayList<ArrayList<String>> mCityList = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> mCountryList = new ArrayList<>();
    SwipeRefreshLayout mSrlDetail;
    RecyclerView mRlvDetail;
    private Thread thread;
    DetailAdapter mAdapter;
    CustomTitleBar mTitle;
    //页面大小
    private int PAGE_SIZE = 1;
    //等待时间
    private int delayMillis = 1000;
    //实时大小
    private int mCurrentCounter = 0;
    //总共数量
    private int TOTAL_COUNTER = 0;
    private boolean isErr;
    List<CarBean> mList;
    HomeModel mModel;
    TextView mTxtCarNum,mTxtAddress;
    LinearLayout mLytAddress;
    private boolean isLoaded = false;

    @Override
    public int setFragmentLayoutID() {
        return R.layout.fragment_car;
    }

    @Override
    protected void initView(View view) {
        mModel = new HomeModel();
        mSrlDetail = view.findViewById(R.id.srl_car);
        mRlvDetail = view.findViewById(R.id.rlv_car);
        mLytAddress = view.findViewById(R.id.lyt_car_address);
        mTxtCarNum = view.findViewById(R.id.txt_car_online);
        mTxtAddress = view.findViewById(R.id.txt_car_address);
    }

    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
        mSrlDetail.setOnRefreshListener(this);
        mSrlDetail.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSrlDetail.post(new Runnable() {
            @Override
            public void run() {
                mSrlDetail.setRefreshing(true);
            }
        });
        mList = CarBean.getData();
        setRecycler(mList);
        mHandler.sendEmptyMessage(MsgBox.MSG_LOAD_DATA);
    }

    @Override
    public void initClick(View view) {
        switch (view.getId()) {
            case R.id.lyt_car_address: {//地址
                if (isLoaded)
                    showPickerView();
                else
                    ToastUtils.showMessage(mActivity,
                            getResources().getString(R.string.load_error));
                break;
            }
        }
    }

    @Override
    protected void initListener() {
        mLytAddress.setOnClickListener(this);
    }

    @Override
    public void onRefresh() {
        mAdapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.getData().clear();
                mAdapter.setNewData(getData(PAGE_SIZE));
                mCurrentCounter = PAGE_SIZE;
                mSrlDetail.setRefreshing(false);
            }
        }, delayMillis);
    }

    @Override
    public void onLoadMoreRequested() {
        mSrlDetail.setEnabled(false);
        if (mAdapter.getData().size() < PAGE_SIZE) {
            mAdapter.loadMoreEnd(true);
        } else {
            if (mCurrentCounter >= TOTAL_COUNTER) {
                mAdapter.loadMoreEnd();
            } else {
                if (isErr) {
                    //加载更多
                    mAdapter.addData(getData(PAGE_SIZE));
                    mCurrentCounter = mAdapter.getData().size();
                    mAdapter.loadMoreComplete();
                } else {
                    isErr = true;
                    mAdapter.loadMoreFail();

                }
            }
            mSrlDetail.setEnabled(true);
        }
    }

    /**
     * @param mList
     */
    private void setRecycler(List<CarBean> mList) {
        mRlvDetail.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new DetailAdapter(mList);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mAdapter.setOnLoadMoreListener(this, mRlvDetail);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(DetailActivity.class);
            }
        });
        mRlvDetail.setAdapter(mAdapter);
    }

    /**
     * 获取信息
     *
     * @param page
     */
    private List<CarBean> getData(int page) {
        mModel.setData("", page);
        mModel.getData(new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                mList = (List<CarBean>) object;
                setRecycler(mList);
            }

            @Override
            public void loadFailed() {
                if (PAGE_SIZE > 1) {
                    PAGE_SIZE--;
                }
            }
        });

        return mList;
    }

    /**
     * 城市选择
     */
    private void showPickerView() {// 弹出选择器
        OptionsPickerView mOptions = new OptionsPickerBuilder(mActivity,
                new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        String mTxtContent = mProvinceList.get(options1).getPickerViewText() +
                                mCityList.get(options1).get(options2) +
                                mCountryList.get(options1).get(options2).get(options3);

                        mTxtAddress.setText(mTxtContent);
                    }
                })
                .setTitleText(getResources().getString(R.string.choose_city))
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        //三级选择器
        mOptions.setPicker(mProvinceList, mCityList, mCountryList);
        mOptions.show();
    }
    /**
     * 解析数据
     */
    private void initJsonData() {
        //获取assets目录下的json文件数据
        String JsonData = JsonParseUtils.getJson(mActivity, "province.json");
        ArrayList<JsonBean> jsonBean = JsonParseUtils.parseData(JsonData, mHandler,
                MsgBox.MSG_LOAD_FAILED);//用Gson 转成实体
        mProvinceList = jsonBean;
        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表
                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }
            //添加城市数据
            mCityList.add(CityList);
            //添加地区数据
            mCountryList.add(Province_AreaList);
        }
        mHandler.sendEmptyMessage(MsgBox.MSG_LOAD_SUCCESS);
    }

    /**
     * Handler
     */
    private Handler mHandler = new Handler(Looper.myLooper()) {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MsgBox.MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MsgBox.MSG_LOAD_SUCCESS:
                    isLoaded = true;
                    break;

                case MsgBox.MSG_LOAD_FAILED:
                    ToastUtils.showMessage(mActivity, getResources().getString(R.string.load_error));
                    break;

            }
        }
    };

}
