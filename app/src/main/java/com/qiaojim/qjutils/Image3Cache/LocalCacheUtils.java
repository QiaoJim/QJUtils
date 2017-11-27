package com.qiaojim.qjutils.Image3Cache;

/**
 * Created by QiaoJim on 2017/8/1.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * 三级缓存之本地缓存
 */
public class LocalCacheUtils {

    private static final String CACHE_PATH = "/CacheImage";

    /**
     * 从本地读取图片
     *
     * @param url
     */
    public Bitmap getBitmapFromLocal(Context context, String url) {
        String fileName;//把图片的url当做文件名,并进行MD5加密
        String path = context.getExternalFilesDir(null)+CACHE_PATH;
        try {
            fileName = MD5Encrypt(url);
            if (fileName !=null) {
                File file = new File(path, fileName);
                if (file.exists()){
                    return BitmapFactory.decodeStream(new FileInputStream(file));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从网络获取图片后,保存至本地缓存
     *
     * @param url
     * @param bitmap
     */
    public void setBitmapToLocal(String url, Bitmap bitmap) {
        try {
            String path = CACHE_PATH;

            String fileName = MD5Encrypt(url);//把图片的url当做文件名,并进行MD5加密
            File file = null;
            if (fileName!=null) {
                file = new File(path, fileName);
            }

            //通过得到文件的父文件,判断父文件是否存在
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }

            //把图片保存至本地
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * md5加密文件名
     * @param pwd
     * @return
     */
    private static String MD5Encrypt(String pwd) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(pwd.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {
                String s = Integer.toHexString(0xff & bytes[i]);

                if (s.length() == 1) {
                    sb.append("0" + s);
                } else {
                    sb.append(s);
                }
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}

