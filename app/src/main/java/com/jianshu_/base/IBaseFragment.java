package com.jianshu_.base;

import android.view.View;

/**
 * Created by jmw on 2016/3/24.
 */
public interface IBaseFragment extends IInitAble, View.OnClickListener {

    /**
     * 刷新界面
     * @return 是否实现了刷新方法，子类覆盖必须返回true
     */
    boolean refreshUI();
}
