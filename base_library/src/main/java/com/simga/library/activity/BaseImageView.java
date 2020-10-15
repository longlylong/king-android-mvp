package com.simga.library.activity;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * 图片类
 * Created by King on 2016/5/3 0003.
 */
public class BaseImageView extends SimpleDraweeView {

    private static final int RES = 1;
    private static final int PATH = 2;
    private static int resize = 90;

    public BaseImageView(Context context) {
        super(context);
    }

    public BaseImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public BaseImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 远程图片	http://, https://	HttpURLConnection 或者参考 使用其他网络加载方案
     * 本地文件	file://	FileInputStream
     * Content provider	content://	ContentResolver
     * asset目录下的资源	asset://	AssetManager
     * res目录下的资源	res://	Resources.openRawResource
     */
    public void display(String path) {
        display(path, null);
    }

    public void display(int resId) {
        displayRes(resId, null);
    }


    /**
     * 可兼容ImageLoader的前缀
     */
    public void display(String path, ResizeOptions resizeOptions) {
        if (path.contains("file://")) {
            displayPath(path, resizeOptions);
        } else if (path.contains("drawable://")) {
            try {
                int resId = Integer.parseInt(path.replace("drawable://", ""));
                display(resId, resizeOptions);
            } catch (NumberFormatException ignored) {
            }
        } else {
            Uri uri = Uri.parse(path);
            setImageURI(uri);
        }
    }

    public void display(int resId, ResizeOptions resizeOptions) {
        displayRes(resId, resizeOptions);
    }

    private void displayPath(String path, ResizeOptions resizeOptions) {
        setDisplayController(PATH, path, resizeOptions);
    }

    private void displayRes(int resId, ResizeOptions resizeOptions) {
        setDisplayController(RES, String.valueOf(resId), resizeOptions);
    }

    private void setDisplayController(int type, String param, ResizeOptions resizeOptions) {
        ImageRequestBuilder requestBuilder;

        if (type == RES) {
            requestBuilder = ImageRequestBuilder.newBuilderWithResourceId(Integer.parseInt(param));
        } else {
            requestBuilder = ImageRequestBuilder.newBuilderWithSource(Uri.parse(param));
        }

        if (resizeOptions == null || resizeOptions.height < 1 || resizeOptions.width < 1) {
            int width = resize;
            int height = resize;
            if (getLayoutParams() != null && getLayoutParams().width > 0) {
                width = getLayoutParams().width;
            }
            if (getLayoutParams() != null && getLayoutParams().height > 0) {
                height = getLayoutParams().height;
            }
            resizeOptions = new ResizeOptions(width, height);
        }

        requestBuilder.setResizeOptions(resizeOptions);
        requestBuilder.setAutoRotateEnabled(true);
        ImageRequest request = requestBuilder.build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(this.getController())
                .build();
        this.setController(controller);
    }
}
