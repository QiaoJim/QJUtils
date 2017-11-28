package com.qiaojim.qjutils.PermissionCheck;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

    /**
     * 回调结果中，判断result数组是否全为0-即全部授权成功
     * @param result
     * @return
     */
    public static boolean allPermitted(int[] result) {
        for (int aResult : result) {
            if (aResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查是否全部授权
     * @param activity
     * @param code
     * @param map
     * @return
     */
    public static boolean check(Activity activity, int code, Map<String, String> map) {
        List<String> list = new ArrayList<>();
        for (Map.Entry entry : map.entrySet()) {
            String value = (String) entry.getValue();
            list.add(value);
        }

        for (String s : list) {
            if (!isPermissionGranted(activity, s)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 请求系统授权对话框
     * @param activity
     * @param code
     * @param map
     */
    public static void request(Activity activity, int code, Map<String, String> map){
        List<String> list = new ArrayList<>();
        List<String> requestList = new ArrayList<>();
        for (Map.Entry entry : map.entrySet()) {
            String value = (String) entry.getValue();
            list.add(value);
        }

        for (String s : list) {
            if (!isPermissionGranted(activity, s)) {
                requestList.add(s);
            }
        }
        ActivityCompat.requestPermissions(activity, requestList.toArray(new String[requestList.size()]), code);

    }

    /**
     * 手动授权
     * @param activity
     * @param results
     * @param map
     * @param dialog
     */
    public static void manuallyAuthorized(final Activity activity, int[] results, Map<String, String> map, QJCheckDialog dialog) {
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

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        buildDialog(activity, builder, dialog, title);

    }

    private static void buildDialog(final Activity activity, final AlertDialog.Builder builder, final QJCheckDialog qjCheckDialog, String title) {
        AlertDialog dialog = null;
        if (qjCheckDialog.view != null) {

            TextView textView = qjCheckDialog.textView;
            Button okBtn = qjCheckDialog.positiveBtn;
            Button noBtn = qjCheckDialog.negativeBtn;

            builder.setView(qjCheckDialog.view);
            dialog = builder.create();
            qjCheckDialog.alertDialog=dialog;

            if (textView != null) {
                textView.setText(title);
            }
            if (okBtn != null) {
                okBtn.setText((qjCheckDialog.positiveTxt == null ? "去手动授权" : qjCheckDialog.positiveTxt));
                okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
            }
            if (noBtn != null) {
                noBtn.setText((qjCheckDialog.negativeTxt == null ? "取消" : qjCheckDialog.negativeTxt));
                final AlertDialog finalDialog = dialog;
                noBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finalDialog.dismiss();
                        activity.finish();
                    }
                });
            }
            dialog.show();


        } else {
            builder.setMessage(title);
            builder.setPositiveButton("去手动授权",null);
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    activity.finish();
                }
            });
            dialog = builder.create();
            qjCheckDialog.alertDialog=dialog;
            dialog.show();

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.setData(Uri.parse("package:" + activity.getPackageName()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    activity.startActivityForResult(intent, QJCheckDialog.REQCODE_FOR_PERMISSIONS);
                }
            });
        }

    }

    /**
     * 手动授权后，取消对话框
     * @param qjCheckDialog
     */
    public static void dismiss(QJCheckDialog qjCheckDialog) {
        if (qjCheckDialog!=null){
            qjCheckDialog.alertDialog.dismiss();
        }
    }
}
