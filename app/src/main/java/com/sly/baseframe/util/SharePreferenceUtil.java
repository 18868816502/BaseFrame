package com.sly.baseframe.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.sly.baseframe.application.MyApplication;


/**
 * Created by Administrator on 2018/9/17.
 */
public class SharePreferenceUtil {
    private static final String FILE_NAME = "localfile";
    public static final String APP_LOCAL_EDIT_SAVE = "app_local_edit_save";
    public static final String APP_FIRST_LAUNCH = "app_first_launch";

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     */
    public static void setParam(Context context, String key, Object object) {
        if (object == null) {
            return;
        }
        String type = object.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if ("String".equals(type)) {
            editor.putString(key, (String) object);
        } else if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) object);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) object);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) object);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) object);
        }

        editor.commit();
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     */
    public static Object getParam(Context context, String key, Object defaultObject) {
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        if ("String".equals(type)) {
            return sp.getString(key, (String) defaultObject);
        } else if ("Integer".equals(type)) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if ("Boolean".equals(type)) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if ("Float".equals(type)) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if ("Long".equals(type)) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    public static void saveCacheObject(String key, Object obj) {
        SharedPreferences sp = MyApplication.getApplication().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        String objectStr = new Gson().toJson(obj);//把对象转为JSON格式的字符串
        editor.putString(key, objectStr);
        editor.commit();
    }

    public static <T> T getCacheObject(String key, Class<T> type) {
        SharedPreferences sp = MyApplication.getApplication().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        String json = sp.getString(key, null);
        if (!TextUtils.isEmpty(json)) {
            return new Gson().fromJson(json, type);
        }
        return null;
    }

    public static void clearObject(String key) {
        SharedPreferences sp = MyApplication.getApplication().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }
}
