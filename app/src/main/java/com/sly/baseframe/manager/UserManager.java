package com.sly.baseframe.manager;


import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.sly.baseframe.activity.MainActivity;
import com.sly.baseframe.application.MyApplication;
import com.sly.baseframe.common.Constants;
import com.sly.baseframe.data.UserModel;
import com.sly.baseframe.util.SharePreferenceUtil;
import com.sly.baseframe.util.ToastCommonUtil;
import com.sly.baseframe.util.arouter.ARouterPath;


public class UserManager {
    private static UserManager instance;
    private static UserModel mUserModel;

    private UserManager() {
        initLoginMember();
    }

    /**
     * 单一实例
     */
    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }


    public void initLoginMember() {
        mUserModel = SharePreferenceUtil.getCacheObject(Constants.KEY_LOGIN_USER, UserModel.class);
    }

    public void saveLoginMember(UserModel userModel) {
        mUserModel = userModel;
        SharePreferenceUtil.saveCacheObject(Constants.KEY_LOGIN_USER, userModel);
    }

    public void clearLoginMember() {
        SharePreferenceUtil.clearObject(Constants.KEY_LOGIN_USER);
        mUserModel = null;
    }

    public Boolean isLogin() {
        if (mUserModel == null)
            return false;
        if (TextUtils.isEmpty(mUserModel.getUser_token())) {
            return false;
        } else {
            return true;
        }
    }

    public int getUserId() {
        if (mUserModel == null) {
            return 0;
        } else {
            return mUserModel.getId();
        }
    }




    public Boolean checkLogin() {
        if (!isLogin()) {
            ARouter.getInstance().build(ARouterPath.LOGIN_ACTIVITY).navigation();
            return false;
        } else {
            return true;
        }
    }

    public void loginAgain() {
        if (isLogin()) {
            ToastCommonUtil.createToastConfig().normalToast(MyApplication.getApplication().getApplicationContext(), "登录失效,请重新登录");
            AppJumpManager.getAppManager().finishAllExcept(MainActivity.class.getSimpleName());
        }
        clearLoginMember();
//        ARouter.getInstance().build(ARouterPath.LOGIN_ACTIVITY).navigation();
    }



}
