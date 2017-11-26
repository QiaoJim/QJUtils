package com.qiaojim.qjutils.PermissionCheck;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author: QiaoJim
 * Date:  2017/11/25
 * QQ: 919106848
 * Desc:
 */

public class QJPermissionCheckUtils extends AppCompatActivity {

    public static final String tag = "QJPermissionCheckUtils";

    private static boolean isPermissionGranted(Context context, String p) {
        return ContextCompat.checkSelfPermission(context, p) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean allPermitted(int[] result) {
        for (int aResult : result) {
            if (aResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static boolean check(Activity activity, int code, Map<String, String> map) {
        List<String> list = new ArrayList<>();
        for (Map.Entry entry : map.entrySet()) {
            String value = (String) entry.getValue();
            list.add(value);
        }

        for (String s : list) {
            if (!isPermissionGranted(activity, s)) {
                ActivityCompat.requestPermissions(activity, list.toArray(new String[list.size()]), code);
                return false;
            }
        }
        return true;
    }

    public static void manuallyAuthorized(final Activity activity, int[] results, Map<String, String> map) {
        List<String> tipList = new ArrayList<>();
        List<String> perList = new ArrayList<>();

        for (Map.Entry entry : map.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            tipList.add(key);
            perList.add(value);
        }
        map.clear();
        for (int i = 0; i < results.length; i++) {
            if (results[i] == PackageManager.PERMISSION_DENIED) {
                map.put(tipList.get(i), perList.get(i));
            }
        }

        String title = "为了正常使用,\n请在设置中允许以下权限 : ";
        for (String tip : map.keySet()) {
            title += "\n" + tip;
        }

        AlertDialog dialog = buildAlertDialog(activity, title,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        intent.setData(Uri.parse("package:" + activity.getPackageName()));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        activity.startActivity(intent);
                    }
                });
        dialog.show();

    }

    private static AlertDialog buildAlertDialog(Context context, View view,
                                                String title, String msg,
                                                String btnText, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // 定制对话框内容
        if (title != null && !"".equals(title)) {
            builder.setTitle(title);
        }
        if (msg != null && !"".equals(msg)) {
            builder.setMessage(msg);
        }
        if (btnText != null || !"".equals(btnText) || listener != null) {
            btnText = (btnText == null || "".equals(btnText)) ? "确定" : btnText;
            builder.setPositiveButton(btnText, listener);
            builder.setNegativeButton("取消", null);
        }
        if (view != null) {
            builder.setView(view);
        }
        return builder.create();
    }

    private static AlertDialog buildAlertDialog(Context context, String msg, DialogInterface.OnClickListener listener) {
        return buildAlertDialog(context, null, null, msg, null, listener);
    }

}
