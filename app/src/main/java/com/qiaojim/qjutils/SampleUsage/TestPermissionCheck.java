package com.qiaojim.qjutils.SampleUsage;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.qiaojim.qjutils.PermissionCheck.QJCheckDialog;
import com.qiaojim.qjutils.PermissionCheck.QJPermissionCheckUtils;
import com.qiaojim.qjutils.R;

import java.util.HashMap;
import java.util.Map;

public class TestPermissionCheck extends AppCompatActivity {

    private Activity activity = this;

    public static final int PER_REQUEST_CODE = 233;
    Map<String, String> map = new HashMap<>();  //需要的权限和提示信息的map
    QJCheckDialog qjCheckDialog = null;         //不再提醒后显示的提示对话框，手动授权

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_permission_check);

        map.put("存储图片", Manifest.permission.WRITE_EXTERNAL_STORAGE);
        map.put("获取手机识别符信息", Manifest.permission.READ_PHONE_STATE);
        if (QJPermissionCheckUtils.check(activity, PER_REQUEST_CODE, map)) {
            // TODO 权限已经全部获取，直接继续操作
        } else {
            /*请求获取对应权限*/
            QJPermissionCheckUtils.request(activity, PER_REQUEST_CODE, map);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        /*若手动全部授权后，dismiss掉对话框*/
        if (QJPermissionCheckUtils.check(activity, PER_REQUEST_CODE, map)) {
            QJPermissionCheckUtils.dismiss(qjCheckDialog);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PER_REQUEST_CODE:
                if (QJPermissionCheckUtils.allPermitted(grantResults)) {
                    // TODO 用户已经全部授权
                } else {

                    /*可自定义提示对话框，按如下 build 即可，QJCheckDialog为全局变量

                    QJCheckDialog.Builder builder = new QJCheckDialog.Builder(this);
                    View v = getLayoutInflater().inflate(R.layout.activity_test_permission_dialog, null);
                    builder.setCustomView(v);
                    builder.setMsgView((TextView) v.findViewById(R.id.text));
                    builder.setPositiveBtn((Button) v.findViewById(R.id.ok), "好的");
                    builder.setNegativeBtn((Button) v.findViewById(R.id.no), "不好");
                    qjCheckDialog = builder.create();
                    QJPermissionCheckUtils.manuallyAuthorized(this, grantResults, map, qjCheckDialog);
                    */

                    /*按默认对话框提示用户*/
                    qjCheckDialog = QJCheckDialog.newDefaultInstance(activity);
                    QJPermissionCheckUtils.manuallyAuthorized(activity, grantResults, map, qjCheckDialog);

                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case QJCheckDialog.REQCODE_FOR_PERMISSIONS:
                /*若手动全部授权后，dismiss掉对话框*/
                if (QJPermissionCheckUtils.check(activity, PER_REQUEST_CODE, map)) {
                    QJPermissionCheckUtils.dismiss(qjCheckDialog);
                }
                break;
        }
    }
}
