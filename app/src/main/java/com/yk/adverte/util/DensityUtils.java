package com.yk.adverte.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yk.adverte.common.APP;


public class DensityUtils {

	private DensityUtils() {
		// 不能被实例化
		new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 * dp转px
	 * 
	 * @param context
	 * @param dpVal
	 * @return
	 */
	public static int dp2px(Context context, float dpVal) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dpVal, context.getResources().getDisplayMetrics());
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(float dpValue) {
		final float scale = APP.getInstance().getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	/**
	 * sp转px
	 * 
	 * @param context
	 * @param spVal
	 * @return
	 */
	public static int sp2px(Context context, float spVal) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
				spVal, context.getResources().getDisplayMetrics());
	}

	/**
	 * px转dp
	 * 
	 * @param context
	 * @param pxVal
	 * @return
	 */
	public static float px2dp(Context context, float pxVal) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (pxVal / scale);
	}

	/**
	 * px转sp
	 * 
	 * @param context
	 * @param pxVal
	 * @return
	 */
	public static float px2sp(Context context, float pxVal) {
		return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
	}
	/**
	 * 获取屏幕分辨率
	 *
	 * @param context
	 * @return
	 */
	public static String getPhoneSize(final Context context) {
		DisplayMetrics dm;
		dm = context.getResources().getDisplayMetrics();

       /* int densityDPI = dm.densityDpi; // 屏幕密度（每寸像素：120/160/240/320）
        float xdpi = dm.xdpi;
        float ydpi = dm.ydpi;*/
		int screenWidth = dm.widthPixels; // 屏幕宽（像素，如：480px）
		int screenHeight = dm.heightPixels; // 屏幕高（像素，如：800px）

		return screenWidth + "*" + screenHeight;
	}

	/**
	 * 通过比例设置图片的高度
	 *
	 * @param width 图片的宽
	 * @param bili  图片比例
	 * @param type  1:外层 LinearLayout 2：外层 RelativeLayout
	 */
	public static void formartHight(ImageView imageView, int width, float bili, int type) {
		int height = (int) (width / bili);
		if (type == 1) {
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
			imageView.setLayoutParams(lp);
		} else {
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
			imageView.setLayoutParams(lp);
		}
	}

	public static DisplayMetrics getDisplayMetrics(Context context) {
		DisplayMetrics metric = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
		return metric;
	}
}
