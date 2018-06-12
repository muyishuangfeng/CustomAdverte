package com.yk.adverte.model;


import com.yk.adverte.model.bean.CarBean;
import com.yk.adverte.net.http.Api;
import com.yk.adverte.net.impl.RequestImpl;


import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeModel {

    String method;
    int page;

    public void setData(String method, int page) {
        this.method = method;
        this.page = page;
    }

    public void getData(final RequestImpl listener) {
        Api.getInstance()
                .getCarData(method, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CarBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CarBean value) {
                        listener.loadSuccess(value);
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
