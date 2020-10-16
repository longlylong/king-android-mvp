package com.kinglyl.android.util;

import com.kinglyl.android.R;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.kinglyl.library.activity.BaseActivity;

public class PictureUtils {

    public static void openCompress(int chooseType, BaseActivity activity, int requestCode) {
        if (chooseType == 1) {
            openCamera(activity, requestCode);
        } else {
            openAlbumOne(activity, requestCode);
        }
    }

    /**
     * 单独拍照不裁剪
     */
    public static void openCamera(BaseActivity activity, int requestCode) {
        PictureSelector.create(activity)
                .openCamera(PictureMimeType.ofImage())
                .theme(R.style.picture_default_style)
                .enableCrop(false)// 是否裁剪 true or false
                .selectionMode(PictureConfig.SINGLE)
                .compress(true)
                .minimumCompressSize(500)// 小于100kb的图片不压缩
                .forResult(requestCode);
    }

    /**
     * 单独拍照并矩形裁剪
     */
    public static void openCameraRectangle(BaseActivity activity, int requestCode) {
        PictureSelector.create(activity)
                .openCamera(PictureMimeType.ofImage())
                .theme(R.style.picture_default_style)
                .enableCrop(true)// 是否裁剪 true or false
                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽 true or false
                .withAspectRatio(1, 1)
                .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .isDragFrame(true)// 是否可拖动裁剪框(固定)
                .selectionMode(PictureConfig.SINGLE)
                .forResult(requestCode);
    }

    /**
     * 单独选择一张图片不裁剪
     */
    public static void openAlbumOne(BaseActivity activity, int requestCode) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .imageSpanCount(3)// 每行显示个数 int
                .enableCrop(false)// 是否裁剪 true or false
                .compress(true)
                .minimumCompressSize(500)// 小于100kb的图片不压缩
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .forResult(requestCode);
    }

    /**
     * 单独选择一张图片并矩形裁剪
     */
    public static void openAlbumOneRectangle(BaseActivity activity, int requestCode) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .imageSpanCount(3)// 每行显示个数 int
                .enableCrop(true)// 是否裁剪 true or false
                .withAspectRatio(1, 1)
                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .isCamera(false)// 是否显示拍照按钮 true or false
                .isDragFrame(true)// 是否可拖动裁剪框(固定)
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .forResult(requestCode);
    }

    /**
     * 选择多张图片
     */
    public static void openAlbumMore(BaseActivity activity, int requestCode, int num) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(num)// 最大图片选择数量 int
                //                .selectionMedia(list)// 是否传入已选图片 List<LocalMedia> list
                .imageSpanCount(3)// 每行显示个数 int
                .enableCrop(false)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .minimumCompressSize(500)// 小于500kb的图片不压缩
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .isCamera(true)// 是否显示拍照按钮 true or false
                .forResult(requestCode);
    }

    public static void openAlbumOneByVideo(BaseActivity activity, int maxSecond, int requestCode) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofVideo())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .imageSpanCount(3)// 每行显示个数 int
                .videoMaxSecond(maxSecond)//最大显示30秒
                .recordVideoSecond(maxSecond)
                .videoMinSecond(1)//最先显示10秒
                .videoQuality(0)
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .forResult(requestCode);
    }

    //包括裁剪和压缩后的缓存，要在上传成功后调用，注意：需要系统sd卡权限
    public static void clearCache(BaseActivity activity) {
        PictureFileUtils.deleteCacheDirFile(activity);
    }
}
