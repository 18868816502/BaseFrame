package com.sly.baseframe.net;//package beilian.hashcloud.net;


import android.text.TextUtils;


import com.sly.baseframe.application.MyApplication;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xst on 2018/9/18.
 */

public class RequestManager {

    private static RequestManager sInstance;
    private Retrofit mRetrofit;
//    private static String pkgname;
//    private static String appFrom;
//    private static String appId;
//    private static int width;
//    private static int height;
//    private static String versionName;

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    public static RequestManager getInstance() {
        if (sInstance == null)
            sInstance = new RequestManager();
        return sInstance;
    }

    private RequestManager() {
        // add header
//        appFrom = RequestConfig.getInstance().getAppFrom();
//        appId = RequestConfig.getInstance().getAppId();
//        width = DimenUtils.getDisplayWidth(context);
//        height = DimenUtils.getDisplayHeight(context);
//        versionName = RequestConfig.getInstance().getVersionName();

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        //如果使用了代理 直接拒绝访问网络
                        if (NetUtil.isWifiProxy(MyApplication.getApplication().getApplicationContext())) {
                            return null;
                        }
                        Request.Builder builder = chain.request().newBuilder();
                        Request original = chain.request();

                        String sign = "";
                        if (original.method() == "GET" || original.method() == "DELETE") {
                            sign = recognizeGetParameter(original.url().toString());
                        } else {
                            if (original.body() == null || !(original.body() instanceof FormBody)) {
                                sign = recognizeBodyParameter(null);
                            } else {
                                sign = recognizeBodyParameter((FormBody) original.body());
                            }
                        }

                        builder.addHeader("sign", sign);
                        builder.addHeader("device", "1");
//                        if (!TextUtils.isEmpty(LoginManager.getInstance().getUserToken())) {
//                            builder.addHeader("user_token", LoginManager.getInstance().getUserToken());
//                        }
//                        if (!TextUtils.isEmpty(LoginManager.getInstance().getSsId())) {
//                            builder.addHeader("ss_id", LoginManager.getInstance().getSsId());
//                        }
                        Request request = builder.build();
                        String url = request.url().toString();

                        return chain.proceed(request);
                    }
                });
//        SSLParams sslParams = SSLParams.getSslSocketFactory();
//        builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);

        OkHttpClient okHttpClient = builder
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
        // init retrofit
        mRetrofit = new Retrofit.Builder()
                .baseUrl("BEILIAN_SERVER_URL")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }


    public static String parameterToUrl(String url, Map<String, String> params) {
        params.put("random", AESUtil.encrypt(String.valueOf(System.currentTimeMillis() / 1000)));
        StringBuilder sb = new StringBuilder(url);
        if (params.size() == 0)
            return url;
        sb = sb.append("?");

        for (String key : params.keySet()) {
            System.out.println(key);
//            try {
            sb.append(key + "=").append(URLEncoder.encode(params.get(key)) + "&");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
        }
        return sb.toString();
    }

    private String recognizeGetParameter(String url) {
        int index = url.indexOf("?");
        if (index == -1)
            return "";
        url = url.substring(index + 1);
        final String[] splits = url.split("&");
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < splits.length; i++) {
            String[] param = splits[i].split("=");
            if (param[0].equals("random")) {
                map.put(param[0], URLDecoder.decode(param[1]));
            } else {
                map.put(param[0], param[1]);
            }
        }
        return sign(map);
    }

    private String recognizeBodyParameter(FormBody formBody) {
        Map<String, String> hashMap = new HashMap<>();
        if (formBody != null && formBody.size() != 0) {
            for (int i = 0; i < formBody.size(); i++) {
                hashMap.put(formBody.encodedName(i), formBody.encodedValue(i));
            }
        }
        return sign(hashMap);
    }

    public static Map<String, String> addParameter(Map<String, String> params) {
        params.put("random", AESUtil.encrypt(String.valueOf(System.currentTimeMillis() / 1000)));
        return params;

    }

    public static String sign(Map<String, String> map) {
        Collection<String> keySet = map.keySet();
        List list = new ArrayList<>(keySet);
        Collections.sort(list);
        StringBuilder headerSb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                headerSb.append("&");
            }
//            try {
            String key = URLDecoder.decode(map.get(list.get(i)));
            key = key.replace(" ", "+");//会出现+变空格的情况  这边做统一替换
            headerSb.append(list.get(i)).append("=" + key);
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//            headerSb.append(list.get(i)).append("=" + map.get(list.get(i)));
        }
        String headerStr = headerSb.toString();
        String md5Str = FileMD5.getMD5(headerStr);
        String sign = "";
        try {
            byte[] byte1 = UseRSAUtil.encrypt(md5Str.getBytes("UTF-8"), "RSA_PUBLIC_KEY");
//            sign = Base64.encodeToString(byte1, Base64.DEFAULT).trim();
            sign = Base64.encode(byte1);
//            sign = FormatUtil.encodeHeadInfo(sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sign;
    }

}
