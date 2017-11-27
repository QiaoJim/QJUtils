package com.qiaojim.qjutils.Image3Cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;


/**
 * Created by QiaoJim on 2017/8/6.
 */

public class QJImg3CacheUtil {

    private NetCacheUtils netCacheUtils;
    private LocalCacheUtils localCacheUtils;
    private MemoryCacheUtils memoryCacheUtils;

    private QJImg3CacheUtil() {
        memoryCacheUtils = new MemoryCacheUtils();
        localCacheUtils = new LocalCacheUtils();
        netCacheUtils = new NetCacheUtils(localCacheUtils, memoryCacheUtils);
    }

    private static class QJImg3CacheHolder {
        private static QJImg3CacheUtil instance = new QJImg3CacheUtil();
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static QJImg3CacheUtil getInstance() {
        return QJImg3CacheHolder.instance;
    }


    /**
     * 3级缓存,加载图片
     *
     * @param url
     * @return
     */
    public void loadBitmap(Context context, ImageView view, String url) {

        Bitmap bitmap;
        bitmap = memoryCacheUtils.getBitmapFromMemory(url);
        if (bitmap != null) {
            view.setImageBitmap(bitmap);
        }
        bitmap = localCacheUtils.getBitmapFromLocal(context, url);
        if (bitmap != null) {
            //从本地获取图片后,保存至内存中
            memoryCacheUtils.setBitmapToMemory(url, bitmap);
            view.setImageBitmap(bitmap);
        }
        netCacheUtils.getBitmapFromNet(view, url);
    }


}
