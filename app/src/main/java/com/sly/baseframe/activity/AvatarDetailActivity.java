package com.sly.baseframe.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.sly.baseframe.R;
import com.sly.baseframe.dialog.BaseSelectDialog;
import com.sly.baseframe.manager.AppJumpManager;
import com.sly.baseframe.util.FrescoConfig;
import com.sly.baseframe.util.arouter.ARouterParameter;
import com.sly.baseframe.util.arouter.ARouterPath;
import com.sly.baseframe.util.takephoto.CustomHelper;
import com.sly.baseframe.util.takephoto.PhotoBitmapUtils;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = ARouterPath.AVATAR_DETAIL_ACTIVITY)
public class AvatarDetailActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener{
    @BindView(R.id.iv_icon)
    SimpleDraweeView mIvIcon;
    private BaseSelectDialog mSelectIconDialog;
    private InvokeParam mInvokeParam;
    private TakePhoto takePhoto;
    private int getPhotoType = 0;//0：拍摄  1：相册选取
    private String mAvater;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_avatar_detail;
    }

    @Override
    protected void initVariables() {
        mAvater = getIntent().getStringExtra(ARouterParameter.KEY_AVATAR);
        FrescoConfig.getInstance().loadImageView(mIvIcon, mAvater);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.iv_back, R.id.iv_more})
    public void clickEvent(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                AppJumpManager.getAppManager().removeActivity(this);
                break;
            case R.id.iv_more:
                if (mSelectIconDialog == null) {
                    mSelectIconDialog = new BaseSelectDialog(this, R.style.ListDialog);
                }
                mSelectIconDialog.show(Gravity.BOTTOM);
                mSelectIconDialog.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        int i = v.getId();
                        if (i == R.id.tv_item1) {
                            CustomHelper.takePhoto(getTakePhoto());
                            getPhotoType = 0;
                        } else if (i == R.id.tv_item2) {
                            CustomHelper.getFromAlbum(getTakePhoto());
                            getPhotoType = 1;
                        } else {
                            mSelectIconDialog.dismiss();
                        }
                    }

                });
                break;
        }
    }


    @Override
    public void takeSuccess(TResult result) {
        if (result != null) {
            TImage images = result.getImage();
            String filePath = images.getCompressPath();
            if (getPhotoType == 1) {
                filePath = PhotoBitmapUtils.amendRotatePhoto(filePath, this);
            }
            FrescoConfig.getInstance().loadFile(mIvIcon, filePath);
            List<File> fileList = new ArrayList<>();
            fileList.add(new File(filePath));
//            mUploadPresenter.uploadFile(fileList, this);
        }
    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            mInvokeParam = invokeParam;
        }
        return type;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //以下代码为处理Android6.0、7.0动态权限所需
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, mInvokeParam, this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

}
