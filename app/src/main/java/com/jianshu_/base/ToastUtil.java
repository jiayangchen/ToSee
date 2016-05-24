package com.jianshu_.base;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

/**
 * Toast工具类，支持在子线程中使用
 * 
 * @author jmw
 */
public class ToastUtil {

	private static Handler sHandler = new Handler() {
		public void handleMessage(Message msg) {
			showToast((String) msg.obj, msg.arg1);
		};
	};

	private static void showToast(String text, int duration) {
		Toast.makeText(Common.getContext(), text, duration).show();
	}

	public static void show(String text, int duration) {
		if (Looper.myLooper() == Looper.getMainLooper()) { // 当前代码在主线程
			showToast(text, duration);
		} else {
			Message msg = Message.obtain();
			msg.obj = text;
			msg.arg1 = duration;
			sHandler.sendMessage(msg);
		}
	}

	public static void show(int resId, int duration) {
		String text = Common.getContext().getResources().getString(resId);
		show(text, duration);
	}

	public static void show(String text) {
		show(text, Toast.LENGTH_SHORT);
	}

	public static void showLong(String text) {
		show(text, Toast.LENGTH_SHORT);
	}

	public static void show(int resId) {
		show(resId, Toast.LENGTH_SHORT);
	}

	public static void showLong(int resId) {
		show(resId, Toast.LENGTH_SHORT);
	}

}
