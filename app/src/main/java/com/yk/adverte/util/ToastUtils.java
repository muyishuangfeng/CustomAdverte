package com.yk.adverte.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class ToastUtils {
	public static final int LENGTH_SHORT = 0;
	public static final int LENGTH_LONG = 1;
	private static Toast toast;
	// 是否显示
	private static boolean isShow = true;
	private static ImageView mImgIcon;
	private static TextView mTxtMsg;
	private static LinearLayout mLytShow;

	/**
	 * 显示消息
	 * 
	 * @param context
	 *            上下文
	 * @param message
	 *            消息内容
	 */
	@SuppressLint("NewApi")
	public static void showMessage(Context context, String message) {
		if (isShow) {
			toast = new Toast(context);
			// 消息内容
			mTxtMsg = new TextView(context);
			LayoutParams lParams1 = new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			mTxtMsg.setTextColor(Color.parseColor("#FFFFFF"));
			mTxtMsg.setTextSize(18);
			mTxtMsg.setPadding(10, 5, 10, 5);

			mTxtMsg.setLayoutParams(lParams1);

			mLytShow = new LinearLayout(context);
			LayoutParams params = new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			mLytShow.setOrientation(LinearLayout.HORIZONTAL);
			mLytShow.setLayoutParams(params);

			int roundRadius = 10; // 8dp 圆角半径
			int fillColor = Color.parseColor("#000000");// 内部填充颜色
			GradientDrawable gd = new GradientDrawable();// 创建drawable
			gd.setCornerRadius(roundRadius);
			gd.setColor(fillColor);

			mLytShow.setBackground(gd);
			mLytShow.addView(mTxtMsg);

			if (message != null) {
				mTxtMsg.setText(message);
			}
			toast.setView(mLytShow);
			toast.setGravity(Gravity.BOTTOM, 0,
					DensityUtils.dp2px(context, 100));
			toast.setDuration(Toast.LENGTH_SHORT);
			toast.show();
		}
	}


	/**
	 * 显示消息内容（带图标）
	 * 
	 * @param context
	 *            上下文
	 * @param message
	 *            消息内容
	 * @param resId
	 *            图片资源id
	 * @param textColor
	 *            文本颜色
	 * @param textSize
	 *            文本大小
	 * @param bgColor
	 *            背景颜色
	 */
	@SuppressLint("NewApi")
	public static void showIconToastMessage(Context context, String message,
			int resId, String textColor, float textSize, String bgColor) {
		if (isShow) {
			toast = new Toast(context);
			// 图片
			mImgIcon = new ImageView(context);
			LayoutParams lParams = new LayoutParams(
					DensityUtils.dp2px(context, 48), DensityUtils.dp2px(
							context, 48));
			mImgIcon.setImageResource(resId);
			lParams.gravity = Gravity.CENTER_HORIZONTAL
					| Gravity.CENTER_VERTICAL;
			lParams.setMargins(5, 5, 5, 5);
			mImgIcon.setLayoutParams(lParams);

			// 消息内容
			mTxtMsg = new TextView(context);
			LayoutParams lParams1 = new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			mTxtMsg.setTextColor(Color.parseColor(textColor));
			mTxtMsg.setTextSize(DensityUtils.dp2px(context, textSize));
			mTxtMsg.setLayoutParams(lParams1);

			mLytShow = new LinearLayout(context);
			LayoutParams params = new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			mLytShow.setOrientation(LinearLayout.HORIZONTAL);
			mLytShow.setLayoutParams(params);

			int roundRadius = 100; // 8dp 圆角半径
			// int strokeColor = Color.parseColor("#2E3135");// 边框颜色
			int fillColor = Color.parseColor(bgColor);// 内部填充颜色
			GradientDrawable gd = new GradientDrawable();// 创建drawable
			gd.setColor(fillColor);
			gd.setCornerRadius(roundRadius);
			// gd.setStroke(0, strokeColor);

			mLytShow.setBackground(gd);
			mLytShow.addView(mImgIcon);
			mLytShow.addView(mTxtMsg);

			if (message != null) {
				mTxtMsg.setText(message);
			}
			toast.setView(mLytShow);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.setDuration(Toast.LENGTH_SHORT);
			toast.show();
		}
	}
}
