package com.jianshu_.base;


import rx.Subscriber;

/**
 * Created by jmw on 2016/4/1.
 */
public abstract class BaseSubscriber<T> extends Subscriber<T> {

    private IBaseView mBaseView;

    public BaseSubscriber(IBaseView baseView) {
        mBaseView = baseView;
    }

    @Override
    public void onCompleted() {
        mBaseView.hideLoading();
        onFinal();
    }

    @Override
    public void onError(Throwable e) {
        LogUtil.e(e.getMessage(), e);
        mBaseView.hideLoading();
        mBaseView.showError("请求失败");
        onFinal();
    }

    /**
     * 最终回调，onCompleted/onError后都会执行
     */
    public void onFinal() {

    }
}
