package com.liuhui.newsinfo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressLint("ResourceAsColor")
public class UtilTool {
	/** 地球半径 */
	private final static double EARTH_RADIUS = 6378137.0;

	/**
	 * 判断是否有网
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			Log.i("NetWorkState", "Unavailabel");
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						Log.i("NetWorkState", "Availabel");
						return true;
					}
				}
			}
		}
		return false;
	}

	/***
	 * 隐藏输入法
	 * 
	 * @param contenxt
	 * @param isOpen
	 *            true 打开 false 关闭
	 */
	public static void toggleSoftInput(Context contenxt, boolean isOpen) {
		boolean open = isOpen;
		// 隐藏输入法
		InputMethodManager imm = (InputMethodManager) contenxt
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		// 判断是否打开
		boolean isOpened = imm.isActive();
		// 打开软键盘
		if (open) {
			if (!isOpened) {
				// 显示输入法
				imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			}
		} else {
			if (isOpened) {
				// 隐藏输入法
				imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}

	}

	public static byte[] Bitmap2Bytes(Parcelable parcelable) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		((Bitmap) parcelable).compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * 打卡软键盘
	 * 
	 */
	public static void openKeybord(Context mContext, EditText mEditText) {
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
				InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	/**
	 * 关闭软键盘
	 * 
	 */
	public static void closeKeybord(Context mContext, EditText mEditText) {
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
	}

	/***
	 * /** 获取版本信息
	 * 
	 * @return version版本
	 * @throws Exception
	 *             抛出异常
	 */
	public static String getVersionCode(Activity activity) throws Exception {
		// 获取packagemanager的实例
		PackageManager packageManager = activity.getPackageManager();
		// getPackageName()是你当前类的包名，packInfo代表是获取版本信息
		PackageInfo packInfo = packageManager.getPackageInfo(
				activity.getPackageName(), 0);
		int version = packInfo.versionCode;
		// String visionName = packInfo.versionName;
		return version + "";
	}

	public static String getVersionName(Activity activity) throws Exception {
		// 获取packagemanager的实例。
		PackageManager packageManager = activity.getPackageManager();
		// getPackageName()是你当前类的包名，代表是获取版本信息。
		PackageInfo packInfo = packageManager.getPackageInfo(
				activity.getPackageName(), 0);
		String visionName = packInfo.versionName;

		return visionName;
	}

	/*
	 * check the app is installed
	 */
	public static boolean isAppInstalled(Context context, String packagename) {
		PackageInfo packageInfo;
		try {
			packageInfo = context.getPackageManager().getPackageInfo(
					packagename, 0);
		} catch (NameNotFoundException e) {
			packageInfo = null;
			e.printStackTrace();
		}
		if (packageInfo == null) {
			// System.out.println("没有安装");
			return false;
		} else {
			// System.out.println("已经安装");
			return true;
		}
	}

	public static String getFilePath(String path) {

		return path;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 * 
	 * @param context
	 *            ,dp
	 * @return px
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 创建显示对话框
	 * 
	 * @author dth
	 * @return void
	 * 
	 */
	public static void showDialog(final Context ctx, String msg, boolean goHome) {
		// 创建一个ALertDialog.Builder 对象
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx)
				.setIcon(R.mipmap.ic_launcher).setTitle("Message")
				.setMessage(msg).setCancelable(false);
		if (goHome) {
			builder.setNegativeButton("SURE", new OnClickListener() {

				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
				}
			});
		} else {
			builder.setPositiveButton("SURE", null);
		}
		builder.create().show();
	}

	/**
	 * 获取当前Activity名称
	 * 
	 * @param context
	 *            上下文
	 * @return String Activity名称
	 */
	public static String getRunningActivityName(Context context) {
		String contextString = context.toString();
		return contextString.substring(contextString.lastIndexOf(".") + 1,
				contextString.indexOf("@"));
	}

	/***
	 * 由时间戳获取现在时间，精确到秒
	 * 
	 * @return
	 */
	public static String parseTimeFromLong(long lon) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date(lon));
		return date;
	}

	/**
	 * 由时间戳获取现在时间，精确到日期
	 * 
	 * @param data
	 * @return
	 */
	public static String parseDataFromLong(long data) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date(data));
		return date;
	}

	/***
	 * 获取当前星期几
	 * 
	 * @return
	 */
	public static String getWeekInfo() {
		String str = "";
		int position = Calendar.getInstance()
				.get(Calendar.DAY_OF_WEEK_IN_MONTH);
		switch (position) {
		case 1:
			str = "星期日";
			break;

		case 2:
			str = "星期一";
			break;
		case 3:
			str = "星期二";
			break;
		case 4:
			str = "星期三";
			break;
		case 5:
			str = "星期四";
			break;
		case 6:
			str = "星期五";
			break;
		case 7:
			str = "星期六";
			break;
		}
		return str;
	}

	/***
	 * 重新计算每个item占用的高度
	 * 
	 * @param listView
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {

		// 获取ListView对应的Adapter

		ListAdapter listAdapter = listView.getAdapter();

		if (listAdapter == null) {

			return;

		}

		int totalHeight = 0;

		for (int i = 0; i < listAdapter.getCount(); i++) { // listAdapter.getCount()返回数据项的数目

			View listItem = listAdapter.getView(i, null, listView);

			listItem.measure(0, 0); // 计算子项View 的宽高

			totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度

		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();

		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));

		// listView.getDividerHeight();//获取子项间分隔符占用的高度

		// params.height最后得到整个ListView完整显示需要的高度

		listView.setLayoutParams(params);

	}

	/***
	 * 二进制生成图片
	 * 
	 * @param b
	 * @return
	 */
	public static Bitmap bytes2Bimap(byte[] b) {// byte[] → Bitmap
		if (b != null && b.length != 0) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		} else {
			return null;
		}
	}

	/****
	 * Button
	 * 
	 * @return
	 */
	// 手机型号
	public static enum PhoneModel {
		S300, S310
	}


	/**
	 * 半角转换为全角
	 * 
	 * @param input
	 * @return
	 */
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	/**
	 * 去除特殊字符或将所有中文标号替换为英文标号
	 * 
	 * @param str
	 * @return
	 */
	public static String stringFilter(String str) {
		str = str.replaceAll("【", "[").replaceAll("】", "]")
				.replaceAll("！", "!").replaceAll("：", ":");// 替换中文标号
		String regEx = "[『』]"; // 清除掉特殊字符
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	/**
	 * 通过包名启动一个app
	 */
	public static void startAPPByPackageName(Context context,
			String appPackageName) {
		try {
			Intent intent = context.getPackageManager()
					.getLaunchIntentForPackage(appPackageName);
			context.startActivity(intent);
		} catch (Exception e) {
			ToastShow.shortMessage(context,"没有安装");
		}
	}

	/**
	 * 判断指定包名的进程是否运行
	 * 
	 * @param context
	 * @param packageName
	 *            指定包名
	 * @return 是否运行
	 */
	public static boolean isRunning(Context context, String packageName) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> infos = am.getRunningAppProcesses();
		for (RunningAppProcessInfo rapi : infos) {
			if (rapi.processName.equals(packageName))
				return true;
		}
		return false;
	}

	/***
	 * 获取图片的byte[]
	 * 
	 * @param iv
	 * @return
	 */
	public static byte[] getImageViewByte(ImageView iv) {
		// 压缩成JPEG
		iv.setDrawingCacheEnabled(true);
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		Bitmap bp = iv.getDrawingCache();
		bp.compress(Bitmap.CompressFormat.JPEG, 100, os);
		iv.setDrawingCacheEnabled(false);
		// 得到二进制数据
		return os.toByteArray();
	}

	/***
	 * 将null赋予空值
	 * 
	 * @param model
	 */
	public static void setNullDate(Class model) {
		Field[] field = model.getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
		try {
			for (int j = 0; j < field.length; j++) { // 遍历所有属性
				String name = field[j].getName(); // 获取属性的名字
				name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
				String type = field[j].getGenericType().toString(); // 获取属性的类型
				if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
					Method m = model.getMethod("get" + name);
					String value = (String) m.invoke(model); // 调用getter方法获取属性值
					if (value == null) {
						m = model.getMethod("set" + name, String.class);
						m.invoke(model, "");
					}
				}
				if (type.equals("class java.lang.Integer")) {
					Method m = model.getMethod("get" + name);
					Integer value = (Integer) m.invoke(model);
					if (value == null) {
						m = model.getMethod("set" + name, Integer.class);
						m.invoke(model, 0);
					}
				}
				if (type.equals("class java.lang.Boolean")) {
					Method m = model.getMethod("get" + name);
					Boolean value = (Boolean) m.invoke(model);
					if (value == null) {
						m = model.getMethod("set" + name, Boolean.class);
						m.invoke(model, false);
					}
				}
				if (type.equals("class java.util.Date")) {
					Method m = model.getMethod("get" + name);
					Date value = (Date) m.invoke(model);
					if (value == null) {
						m = model.getMethod("set" + name, Date.class);
						m.invoke(model, new Date());
					}
				}
				// 如果有需要,可以仿照上面继续进行扩充,再增加对其它类型的判断
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
