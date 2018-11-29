package com.sly.baseframe.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sly.baseframe.R;


public class ToastCommonUtil {
	private static ToastCommonUtil toastCommon;
	private static Toast toast;

	private ToastCommonUtil() {

	}

	public static ToastCommonUtil createToastConfig() {
		if (toastCommon == null) {
			toastCommon = new ToastCommonUtil();
		}
		return toastCommon;
	}

	public Toast getToast() {
		return toast;
	}

	public void normalToast(Context context, String content) {
		if (toast != null) {
			toast.cancel();
		}
		View layout = LayoutInflater.from(context).inflate(R.layout.normal_toast_layout, null, false);
		TextView text = (TextView) layout.findViewById(R.id.toast_tv);
		text.setText(content);
		toast = new Toast(context);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();
	}
	public void normalToast(Context context, int content) {
		if (toast != null) {
			toast.cancel();
		}
		View layout = LayoutInflater.from(context).inflate(R.layout.normal_toast_layout, null, false);
		TextView text = (TextView) layout.findViewById(R.id.toast_tv);
		text.setText(content);
		toast = new Toast(context);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();
	}


}
