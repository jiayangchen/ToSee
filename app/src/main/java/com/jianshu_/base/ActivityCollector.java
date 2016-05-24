package com.jianshu_.base;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.Stack;

/**
 * 
 * @author jmw
 */
public class ActivityCollector {

	private static Stack<WeakReference<Activity>> sActivities = new Stack<WeakReference<Activity>>();

	public static void add(Activity activity) {
		sActivities.push(new WeakReference<Activity>(activity));
	}

	public static void remove(Activity activity) {
		sActivities.remove(activity);
	}
	
	/**
	 * 退出到栈底（第一个页面-首页）
	 */
	public static void finishToBottom() {
		Activity activity = null;
		for (int i = 0; i < sActivities.size(); i++) {
			activity = sActivities.get(i).get();
			if (activity != null) {
				sActivities.remove(i);
				finishAll();
				add(activity);
				break;
			}
		}
	}

	/**
	 * 退出到栈顶（只留当前的页面）
	 */
	public static void finishToTop() {
		try {
			Activity activity = sActivities.pop().get();
			finishAll();
			add(activity);
		} catch (Exception e) {}
	}

	/**
	 * 退出所有界面（应用程序退出）
	 */
	public static void finishAll() {
		for (WeakReference<Activity> wr : sActivities) {
			Activity activity = wr.get();
			if (activity != null && !activity.isFinishing())
				activity.finish();
		}
		sActivities.clear();
	}
	
}
