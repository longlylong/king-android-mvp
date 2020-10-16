package com.kinglyl.library.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore.Images.ImageColumns;

import java.io.File;

public class SdCardUtil {

    public static final String PROJECT_FILE_PATH = Environment
            .getExternalStorageDirectory().getPath() + "/" + "dreamlife" + "/"; // 项目路径
    public static final String DEFAULT_PHOTO_PATH = PROJECT_FILE_PATH + "pics/";
    public static final String DEFAULT_RECORD_PATH = PROJECT_FILE_PATH + "record/";
    public static String CACHE_PATH = ""; // 应用的cache目录用于存放缓存
    public static String TEMP = "file:///" + PROJECT_FILE_PATH + "camera.jpg";

    /**
     * 判断是否有sd
     */
    public static boolean checkSdState() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 初始化文件目录
     */
    public static void initFileDir(Context context) {
        File projectDir = new File(PROJECT_FILE_PATH);
        if (!projectDir.exists()) {
            projectDir.mkdirs();
        }
        File fileDir = new File(DEFAULT_PHOTO_PATH);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        File recordDir = new File(DEFAULT_RECORD_PATH);
        if (!recordDir.exists()) {
            recordDir.mkdirs();
        }
        CACHE_PATH = Environment.getExternalStorageDirectory().getPath()
                + "/Android/data/" + context.getPackageName() + "/cache/";

        File cacheDir = new File(CACHE_PATH);
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
    }

    /**
     * 获取拓展存储Cache的绝对路径
     *
     * @param context
     */
    public static String getExternalCacheDir(Context context) {
        if (!SdCardUtil.checkSdState())
            return null;
        StringBuilder sb = new StringBuilder();
        File file = context.getExternalCacheDir();
        if (file != null) {
            sb.append(file.getAbsolutePath()).append(File.separator);
        } else {
            sb.append(Environment.getExternalStorageDirectory().getPath()).append("/Android/data/").append(context.getPackageName())
                    .append("/cache/").append(File.separator).toString();
        }
        return sb.toString();
    }

    public static String getExternalFilesDir(Context context, String type) {
        if (!SdCardUtil.checkSdState())
            return null;
        StringBuilder sb = new StringBuilder();
        File file = context.getExternalFilesDir(type);
        if (file != null) {
            sb.append(file.getAbsolutePath()).append(File.separator);
        } else {
            sb.append(Environment.getExternalStorageDirectory().getPath()).append("/Android/data/").append(context.getPackageName())
                    .append("/files/").append(File.separator).toString();
        }
        return sb.toString();
    }

    /**
     * 获取拍照路径
     *
     * @param context
     * @return
     */
    public static String getCaremaPath(Context context) {
        return getExternalCacheDir(context) + "carema.jpg";
    }


    /**
     * Try to return the absolute file path from the given Uri
     *
     * @param context
     * @param uri
     * @return the file path or null
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri)
            return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri,
                    new String[]{ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public static String getTempCamera() {
        return PROJECT_FILE_PATH + System.currentTimeMillis() + ".jpg";
    }


}
