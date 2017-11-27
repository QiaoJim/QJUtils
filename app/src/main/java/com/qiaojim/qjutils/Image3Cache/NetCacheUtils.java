package com.qiaojim.qjutils.Image3Cache;

/**
 * Created by QiaoJim on 2017/8/1.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 三级缓存之网络缓存
 */
public class NetCacheUtils {

    private LocalCacheUtils mLocalCacheUtils;
    private MemoryCacheUtils mMemoryCacheUtils;

    public NetCacheUtils(LocalCacheUtils localCacheUtils, MemoryCacheUtils memoryCacheUtils) {
        mLocalCacheUtils = localCacheUtils;
        mMemoryCacheUtils = memoryCacheUtils;
    }

    /**
     * 从网络下载图片
     *
     * @param url 下载图片的网络地址
     */
    public void getBitmapFromNet(ImageView view, final String url) {
        new BitmapTask().execute(view, url);
    }


    class BitmapTask extends AsyncTask<Object, Void, Bitmap> {
        private ImageView view;
        private String url;

        /**
         * 后台耗时操作,存在于子线程中
         *
         * @param params
         * @return
         */
        @Override
        protected Bitmap doInBackground(Object[] params) {
            view = (ImageView) params[0];
            url = (String) params[1];

            return downLoadBitmap(url);
        }

        /**
         * 更新进度,在主线程中
         *
         * @param values
         */
        @Override
        protected void onProgressUpdate(Void[] values) {
            super.onProgressUpdate(values);
        }

        /**
         * 耗时方法结束后执行该方法,主线程中
         *
         * @param result
         */
        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                view.setImageBitmap(result);

                //从网络获取图片后,保存至本地缓存
                mLocalCacheUtils.setBitmapToLocal(url, result);
                //保存至内存中
                mMemoryCacheUtils.setBitmapToMemory(url, result);

            }
        }
    }

    /**
     * 网络下载图片
     *
     * @param url
     * @return
     */
    private Bitmap downLoadBitmap(String url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                //图片压缩
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;//宽高压缩为原来的1/2
                options.inPreferredConfig = Bitmap.Config.ARGB_4444;
                return BitmapFactory.decodeStream(conn.getInputStream(), null, options);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null)
                conn.disconnect();
        }

        return null;
    }
}


