package com.jianshu_.base;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;


/**
 * 基础Fragment
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment implements IBaseFragment, IBaseView {

    protected int mPageFlag;
    protected View mRootView;
    protected CompositeSubscription mSubscriptions = new CompositeSubscription();
    private IBasePresenter mPresenter;
    protected boolean mInitFinish;

    public BaseFragment(){
    }

    public BaseFragment(int pageFlag){
        mPageFlag = pageFlag;
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(initView(savedInstanceState), null);

        ButterKnife.bind(this, mRootView);

        mSubscriptions = RxUtils.getNewCompositeSubIfUnsubscribed(mSubscriptions);

        mPresenter = initPresenter();
        init(savedInstanceState);
        initEvent(savedInstanceState);
        initData(savedInstanceState);

        mInitFinish = true;

        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);

        if (mPresenter != null) {
            mPresenter.onDestroy();
        }

        RxUtils.unsubscribeIfNotNull(mSubscriptions);
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initEvent(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }

    @Override
    public void showLoading(String message) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String message) {
        ToastUtil.show(message);
    }

    /**
     * 刷新界面
     * @return 是否实现了刷新方法，子类覆盖必须返回true
     */
    @Override
    public boolean refreshUI() {
        return false;
    }
}
