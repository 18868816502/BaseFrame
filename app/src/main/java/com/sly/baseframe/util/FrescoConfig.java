package com.sly.baseframe.util;

import android.net.Uri;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

public class FrescoConfig {
    private static volatile FrescoConfig mInstance;

    private FrescoConfig() {

    }

    public static FrescoConfig getInstance(){
        if (mInstance == null){
            synchronized(FrescoConfig.class){
                if (mInstance == null)
                    mInstance = new FrescoConfig();
            }
        }
        return mInstance;
    }
    //  支持动态的webp和gif
    public void loadImageView(DraweeView view, String url) {
        loadImageView(view, url, -1);
    }

    //  支持动态的webp和gif
    public void loadImageView(DraweeView view, String url, int placeImage) {
        loadImageView(view, url, placeImage, null);
    }

    private void loadImageView(DraweeView view, String url, int placeImage, String resize) {
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(true)
                .setUri(url)
                .setOldController(view.getController())
                .build();

        if (view instanceof SimpleDraweeView && placeImage > 0) {
            ((SimpleDraweeView) view).getHierarchy().setPlaceholderImage(placeImage);
        }
        view.setController(draweeController);
    }

    //图片加载完成后的回调
    public void loadImageView(DraweeView view, String url, ControllerListener controllerListener) {
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(true)
                .setUri(url)
                .setOldController(view.getController())
                .setControllerListener(controllerListener)
                .build();
        view.setController(draweeController);
    }

    public void loadFile(final SimpleDraweeView view, String filePath) {
        Uri uri = new Uri.Builder()
                .scheme(UriUtil.LOCAL_FILE_SCHEME)
                .path(filePath)
                .build();
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setLocalThumbnailPreviewsEnabled(true)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(view.getController())
                .build();
        view.setController(controller);
    }

}
