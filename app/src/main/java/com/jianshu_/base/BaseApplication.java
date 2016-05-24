package com.jianshu_.base;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

/**
 * @author jmw
 */
public class BaseApplication extends MultiDexApplication {

    public void onCreate() {
        super.onCreate();
        Common.init(this);
        Common.enableDebug(); // 激活输出debug日志
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
