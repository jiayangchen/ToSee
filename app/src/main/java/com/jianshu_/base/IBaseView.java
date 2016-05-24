package com.jianshu_.base;

/**
 * Created by jmw on 2016/3/24.
 */
public interface IBaseView {

    void showLoading(String message);

    void hideLoading();

    void showError(String message);
}
