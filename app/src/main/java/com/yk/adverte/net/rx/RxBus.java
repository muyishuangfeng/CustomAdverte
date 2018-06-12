package com.yk.adverte.net.rx;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;


/**
 * 事件总线
 */
public class RxBus {

    private static volatile RxBus instance;
    private final Relay<Object> mBus;

    public RxBus() {
        this.mBus = PublishRelay.create().toSerialized();
    }

    public static RxBus getInstance() {
        if (instance == null) {
            synchronized (RxBus.class) {
                if (instance == null) {
                    instance = Holder.BUS;
                }
            }
        }
        return instance;
    }

    public void post(Object obj) {
        mBus.accept(obj);
    }

    public <T> io.reactivex.Observable<T> toObservable(Class<T> tClass) {
        return mBus.ofType(tClass);
    }

    public io.reactivex.Observable<Object> toObservable() {
        return mBus;
    }

    public boolean hasObservers() {
        return mBus.hasObservers();
    }

    private static class Holder {
        private static final RxBus BUS = new RxBus();
    }


}
