package com.jianshu_.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.jianshu_.R;

import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;

/**
 * 
 * @author jmw
 */
public abstract class BaseActivity extends Activity implements IBaseActivity, IBaseView {

	protected CompositeSubscription mSubscriptions = new CompositeSubscription();
	private IBasePresenter mPresenter;

	@Override
	protected final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//取消标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//取消状态栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);


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
