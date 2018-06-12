package com.yk.adverte.model.bean;

import java.util.ArrayList;
import java.util.List;

public class CarBean {

    String car_name;
    String car_number;
    String car_img;

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public String getCar_number() {
        return car_number;
    }

    public void setCar_number(String car_number) {
        this.car_number = car_number;
    }

    public String getCar_img() {
        return car_img;
    }

    public void setCar_img(String car_img) {
        this.car_img = car_img;
    }

    public CarBean(String car_name, String car_number, String car_img) {
        this.car_name = car_name;
        this.car_number = car_number;
        this.car_img = car_img;
    }

    public static List<CarBean> getData(){
        List<CarBean>mList= new ArrayList<>();
        mList.add(new CarBean("潇湘夜雨","直播",
                "http://img2.imgtn.bdimg.com/it/u=3093785514,1341050958&fm=21&gp=0.jpg"));
        mList.add(new CarBean("龙行天下","直播",
                "http://img2.imgtn.bdimg.com/it/u=3093785514,1341050958&fm=21&gp=0.jpg"));
        mList.add(new CarBean("虎贲将军","点播",
                "http://www.8kmm.com/UploadFiles/2012/8/201208140920132659.jpg"));
        mList.add(new CarBean("我欲成仙归去","直播",
                "http://img2.imgtn.bdimg.com/it/u=3093785514,1341050958&fm=21&gp=0.jpg"));
        mList.add(new CarBean("又恐琼楼玉宇","直播",
                "http://www.8kmm.com/UploadFiles/2012/8/201208140920132659.jpg"));
        mList.add(new CarBean("此事古难全","直播",
                "http://img2.imgtn.bdimg.com/it/u=3093785514,1341050958&fm=21&gp=0.jpg"));
        mList.add(new CarBean("但愿人长久千里共婵娟","直播",
                "http://www.8kmm.com/UploadFiles/2012/8/201208140920132659.jpg"));
        return mList;
    }
}
