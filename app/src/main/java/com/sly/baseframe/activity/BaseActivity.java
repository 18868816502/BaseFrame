package com.sly.baseframe.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.sly.baseframe.manager.AppJumpManager;
import com.sly.baseframe.util.StatusBarUtil;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;


/**
 * Created by xst on 2018/9/17.
 */

public abstract class BaseActivity extends AppCompatActivity {
//    private ZLoadingDialog mZLoadingDialog;

    protected abstract int getLayoutResId();

    protected abstract void initVariables();

    protected abstract void initViews();

    protected abstract void loadData();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        ButterKnife.bind(this);
//        createLoadingDialog();
        initVariables();
        initViews();
        loadData();
        AppJumpManager.getAppManager().addActivity(this);
        StatusBarUtil.transparencyBar(this);
//        if (this instanceof BankManagerActivity) {
//            StatusBarUtil.StatusBarLightMode(this);
//        }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        AppJumpManager.getAppManager().addActivity(this);
    }

    protected boolean replaceFragment(Fragment fragment, int id) {
        try {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(id, fragment);
            ft.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

//    private void createLoadingDialog() {
//        //https://blog.csdn.net/qq_33540190/article/details/81082363
//        mZLoadingDialog = new ZLoadingDialog(this);
//        mZLoadingDialog.setLoadingBuilder(Z_TYPE.STAR_LOADING)//设置类型 //Z_TYPE.ROTATE_CIRCLE
//                .setLoadingColor(Color.BLACK)//颜色
//                .setHintText("Loading...")
//                .setCancelable(false)
//                .setCanceledOnTouchOutside(false);
//    }
//
//    public void showLoadingDialog() {
//        mZLoadingDialog.show();
//    }
//
//    public void hideLoadingDialog() {
//        mZLoadingDialog.dismiss();
//    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
