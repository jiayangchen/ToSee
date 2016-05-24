package com.jianshu_.base;

import android.text.TextUtils;
import android.util.Log;

/**
 * <pre>
 * Log工具，类似android.util.Log。
 * tag自动产生，格式: customTagPrefix:className.methodName(L:lineNumber),
 * customTagPrefix为空时只输出：className.methodName(L:lineNumber)。
 * </pre>
 */
public class LogUtil {

	/**
	 * tag的前缀
	 */
	private static String sCustomTagPrefix = "rk_log";
	/**
	 * 传入多个value时用该字段分隔
	 */
	private static String sValueSpace = ",";

	private static String generateTag() {
		StackTraceElement caller = new Throwable().getStackTrace()[2];
		String tag = "%s.%s(L:%d)";
		String callerClazzName = caller.getClassName();
		callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
		tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
		tag = TextUtils.isEmpty(sCustomTagPrefix) ? tag : sCustomTagPrefix + ":" + tag;
		return tag;
	}

	public static void setCustomTagPrefix(String customTagPrefix) {
		if (customTagPrefix == null) return;
		LogUtil.sCustomTagPrefix = customTagPrefix;
	}

	public static void setValueSpace(String valueSpace) {
		if (valueSpace == null) return;
		LogUtil.sValueSpace = valueSpace;
	}

	public static void d(Object... content) {
		if (!Common.isDebug())
			return;

		Log.d(generateTag(), filterNullStr(content));
	}

	public static void d(Throwable tr, Object... content) {
		if (!Common.isDebug())
			return;

		Log.d(generateTag(), filterNullStr(content), tr);
	}

	public static void e(Object... content) {
		if (!Common.isDebug())
			return;

		Log.e(generateTag(), filterNullStr(content));
	}

	public static void e(Object content, Throwable tr) {
		if (!Common.isDebug())
			return;

		Log.e(generateTag(), filterNullStr(content), tr);
	}

	public static void e(Throwable tr, Object... content) {
		if (!Common.isDebug())
			return;

		Log.e(generateTag(), filterNullStr(content), tr);
	}

	public static void i(Object... content) {
		if (!Common.isDebug())
			return;

		Log.i(generateTag(), filterNullStr(content));
	}

	public static void i(Throwable tr, Object... content) {
		if (!Common.isDebug())
			return;

		Log.i(generateTag(), filterNullStr(content), tr);
	}

	public static void v(Object... content) {
		if (!Common.isDebug())
			return;

		Log.v(generateTag(), filterNullStr(content));
	}

	public static void v(Throwable tr, Object... content) {
		if (!Common.isDebug())
			return;

		Log.v(generateTag(), filterNullStr(content), tr);
	}

	public static void w(Object... content) {
		if (!Common.isDebug())
			return;

		Log.w(generateTag(), filterNullStr(content));
	}

	public static void w(Throwable tr, Object... content) {
		if (!Common.isDebug())
			return;

		Log.w(generateTag(), filterNullStr(content), tr);
	}

	public static void w(Throwable tr) {
		if (!Common.isDebug())
			return;

		Log.w(generateTag(), tr);
	}

	public static void wtf(Object... content) {
		if (!Common.isDebug())
			return;

		Log.wtf(generateTag(), filterNullStr(content));
	}

	public static void wtf(Throwable tr, Object... content) {
		if (!Common.isDebug())
			return;

		Log.wtf(generateTag(), filterNullStr(content), tr);
	}

	public static void wtf(Throwable tr) {
		if (!Common.isDebug())
			return;

		Log.wtf(generateTag(), tr);
	}

	public static String filterNull(Object value) {
		return value == null ? "" : value.toString();
	}

	public static String filterNullStr(Object... values) {
		if (values == null || values.length == 0)
			return "";

		StringBuilder sb = new StringBuilder("");
		for (Object s : values) {
			sb.append(filterNull(s)).append(sValueSpace);
		}
		sb.delete(sb.length() - sValueSpace.length(), sb.length());
		return sb.toString();
	}

}
