package com.jianshu_.base;

import android.os.Bundle;

/**
 * 定义了初始化的回调接口
 * Created by Administrator on 2016/3/21 0021.
 */
public interface IInitAble {

    /**
     * initView返回这个值代表终止初始化
     *
     * @see #initView(Bundle)
     */
    int BREAK_INIT = -1;

    /**
     * 初始化界面，如果终止初始化返回 -1
     *
     * @return layout resource id
     * @see #BREAK_INIT
     */
    int initView(Bundle savedInstanceState);

    /**
     * 初始化mvp p层
     * @return
     */
    IBasePresenter initPresenter();

    /**
     * 初始化
     */
    void init(Bundle savedInstanceState);

    /**
     * 初始化数据
     */
    void initData(Bundle savedInstanceState);

    /**
     * 初始化事件
     */
    void initEvent(Bundle savedInstanceState);
}
