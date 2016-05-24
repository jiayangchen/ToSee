package com.jianshu_.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;


import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;

/**
 * 
 * @author jmw
 */
public abstract class BaseFragmentActivity extends FragmentActivity implements IBaseActivity, IBaseView {

	protected CompositeSubscription mSubscriptions = new CompositeSubscription();
	private IBasePresenter mPresenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ActivityCollector.add(this);

		int id = initView(savedInstanceState);
		if (id == BREAK_INIT) {
			finish();
			return;
		}
		setContentView(id);
		ButterKnife.bind(this);

		mSubscriptions = RxUtils.getNewCompositeSubIfUnsubscribed(mSubscriptions);

		mPresenter = initPresenter();
		init(savedInstanceState);
		initEvent(savedInstanceState);
		initData(savedInstanceState);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();

		ButterKnife.unbind(this);
		ActivityCollector.remove(this);

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
}
