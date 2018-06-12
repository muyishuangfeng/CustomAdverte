package com.yk.adverte.model;

import com.yk.adverte.model.bean.UserBean;
import com.yk.adverte.net.http.Api;
import com.yk.adverte.net.impl.RequestImpl;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserModel {

    String method;
    String user_name;
    String user_pass;
    String user_code;
    String user_phone;


    public void setData(String method, String user_name, String user_pass) {
        this.method = method;
        this.user_name = user_name;
        this.user_pass = user_pass;
    }

    /**
     * 注册
     *
     * @param method
     * @param user_name
     * @param user_code
     * @param user_pass
     */
    public void setRegister(String method, String user_name, String user_code, String user_pass) {
        this.method = method;
        this.user_name = user_name;
        this.user_code = user_code;
        this.user_pass = user_pass;
    }

    /**
     * 登录
     *
     * @param listener
     */
    public void getData(final RequestImpl listener) {
        Api.getInstance().login(method, user_name, user_pass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserBean userBean) {
                        listener.loadSuccess(userBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.loadFailed();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    /**
     * 注册
     *
     * @param listener
     */
    public void getRegister(final RequestImpl listener) {
        Api.getInstance().register(method, user_name, user_code, user_pass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserBean userBean) {
                        listener.loadSuccess(userBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.loadFailed();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public void setCode(String method, String user_phone) {
        this.method = method;
        this.user_phone = user_phone;
    }

    public void getCode(final RequestImpl listener) {
        Api.getInstance().getCode(method, user_phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        listener.loadSuccess(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.loadFailed();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
