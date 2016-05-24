package com.jianshu_.base;


import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jmw on 2016/3/24.
 */
public class BasePresenter implements IBasePresenter {

    public CompositeSubscription mSubscriptions;

    public BasePresenter() {
        onCreate();
    }

    @Override
    public void onCreate() {
        LogUtil.d(getClass().getSimpleName(), "onCreate");
        mSubscriptions = RxUtils.getNewCompositeSubIfUnsubscribed(mSubscriptions);
    }

    @Override
    public void onDestroy() {
        LogUtil.d(getClass().getSimpleName(), "onDestroy");
        RxUtils.unsubscribeIfNotNull(mSubscriptions);
    }

    public void add(Subscription s) {
        mSubscriptions.add(s);
    }
}
