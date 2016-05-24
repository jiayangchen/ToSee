package com.jianshu_.base;

import android.app.Application;
import android.content.Context;

/**
 * 使用前必须先调用init初始化（建议Application中执行一次即可）
 * 
 * @see #init(Application)
 * @author jmw
 * 
 */
public class Common {

	private static boolean sIsInit; // 标记是否已经初始化
	private static boolean sIsDebug; // 标记是否是调试
	private static Context sContext;

	public static void init(Application context) {
		init(context, false);
	}

	public static void init(Application context, boolean isDebug) {
		if (sIsInit)
			return;

		sContext = context.getApplicationContext();
		sIsDebug = isDebug;
		sIsInit = true;

		UIUtil.init();
	}

	public static Context getContext() {
		if (sIsInit)
			return sContext;

		throw new RuntimeException("未初始化");
	}

	public static boolean isDebug() {
		return sIsDebug;
	}

	public static void setDebug(boolean isDebug) {
		sIsDebug = isDebug;
	}

	public static void enableDebug() {
		setDebug(true);
	}
	
	public static void disableDebug() {
		setDebug(false);
	}
}
