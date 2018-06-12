package com.yk.adverte.model.bean;

/**
 * Created by Silence on 2017/11/6.
 */

public class GpsBean {

    private String cityName;
    private double Longitude;
    private double Latitude;
    private String address;
    private String province;
    private String country;
    private String cityCode;

    public String getCityName() {
        return cityName;
    }

    public double getLongitude() {
        return Longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public String getAddress() {
        return address;
    }

    public String getProvince() {
        return province;
    }

    public String getCountry() {
        return country;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}
