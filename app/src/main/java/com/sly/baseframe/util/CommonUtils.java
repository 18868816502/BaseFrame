package com.sly.baseframe.util;

import android.content.ClipData;
import android.content.Context;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.Toast;

import com.sly.baseframe.R;

import java.io.File;
import java.util.regex.Pattern;


public class CommonUtils {
    private static final String LOG_TAG = CommonUtils.class.getSimpleName();

    public final static boolean isValidEmail(String email) {
        String pattern = "(([\\w][\\.\\-]?)+[\\w])@([\\w\\-]+\\.)+[\\w]+";
        return Pattern.matches(pattern, email);
    }

    public static boolean isValidPassword(String pass) {
        if (pass.contains(" ") || pass.contains("\n"))
            return false;

        return true;
    }

    public static boolean isValidNickname(String name) {
        String pattern = "^[\\u4E00-\\u9FA5\\uF900-\\uFA2D\\w\\.]{2,18}$";
        return Pattern.matches(pattern, name);
    }

    public static boolean isValidMobile(String mobile) {
        String pattern = "^^[1][123456789][0-9]{9}$";
        return Pattern.matches(pattern, mobile);
    }

    public static boolean isValidQQ(String qq) {
        String pattern = "^[1-9](\\d){4,11}$";
        return Pattern.matches(pattern, qq);
    }
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    private static Toast mToast = null;

    public static void showTipInfo(Context cxt, int resId) {
        if (mToast != null)
            mToast.cancel();

        mToast = Toast.makeText(cxt, resId, Toast.LENGTH_SHORT);
        mToast.show();
    }

    public static void showTipInfo(Context cxt, String text) {
        if (mToast != null)
            mToast.cancel();

        mToast = Toast.makeText(cxt, text, Toast.LENGTH_SHORT);
        mToast.show();
    }

    public static void showTipInfo(Context cxt, String text, float margin) {
        if (mToast != null)
            mToast.cancel();

        mToast = Toast.makeText(cxt, text, Toast.LENGTH_SHORT);
        mToast.setMargin(0, margin);
        mToast.show();
    }


    public static boolean isSDCardAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static boolean isExternSdcardEnough() {
        long lSize = getSDCardAvailableSize() / 1024 / 1024;

        if (lSize < 10) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Get available space size left in SDcard.
     *
     * @return The available size which unit is Byte.
     */
    public static long getSDCardAvailableSize() {
        String storageDirectory = Environment.getExternalStorageDirectory().toString();
        StatFs stat = new StatFs(storageDirectory);
        return (long) stat.getAvailableBlocks() * (long) stat.getBlockSize();
    }

    /**
     * 获得SD卡总大小
     */
    public static String getSDTotalSize(Context context) {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return Formatter.formatFileSize(context, blockSize * totalBlocks);
    }

    /**
     * 获得sd卡剩余容量，即可用大小
     */
    public static String getSDAvailableSize(Context context) {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(context, blockSize * availableBlocks);
    }

    public static boolean isWifiEnabled(Context context) {
        if (context == null)
            return false;

        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager == null)
            return false;

        return wifiManager.isWifiEnabled();
    }

    public static boolean isNetworkConnect(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        }

        return false;
    }

    public static boolean isWifiConnected(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo wifiNetworkInfo = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (wifiNetworkInfo != null && wifiNetworkInfo.isConnected()) {
                    return true;
                }
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        }

        return false;
    }

    public static boolean hasFrontCamera() {
        int camNum = 1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            camNum = Camera.getNumberOfCameras();
        }
        return camNum > 1;
    }

    public static int getColor(String str) {
        str.trim();
        str = str.substring(1);
        int c = Integer.decode("0x" + str);
        int color = 0xFF000000 | c;
        return color;
    }

    public static void copyContent(Context context, String content) {
        if (Build.VERSION.SDK_INT >= 11) {
            android.content.ClipboardManager cm = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            cm.setPrimaryClip(ClipData.newPlainText(null, content));
        } else {
            android.text.ClipboardManager cm = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            cm.setText(content);
        }

        Toast.makeText(context, "复制成功", Toast.LENGTH_SHORT).show();
    }

    public static String mobileOrEmail(Context context, String str) {
        if (CommonUtils.isValidEmail(str)) {
            return "0";
        } else if (CommonUtils.isValidMobile(str)) {
            return "1";
        } else {
            ToastCommonUtil.createToastConfig().normalToast(context, R.string.user_name_format_error);
            return "";
        }
    }
}