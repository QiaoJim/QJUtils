package com.qiaojim.qjutils.SampleUsage;

import android.Manifest;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.qiaojim.qjutils.PermissionCheck.QJPermissionCheckUtils;
import com.qiaojim.qjutils.R;

import java.util.HashMap;
import java.util.Map;

public class TestPermissionCheck extends AppCompatActivity {

    private Context context = this;
    private static final String tag = QJPermissionCheckUtils.tag;
    Map<String, String> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_permission_check);


        map.put("存储图片", Manifest.permission.WRITE_EXTERNAL_STORAGE);
        map.put("获取手机识别符信息", Manifest.permission.READ_PHONE_STATE);
        if (QJPermissionCheckUtils.check(this, 233, map)) {
            Log.e(tag, "onCreate " + QJPermissionCheckUtils.check(this, 233, map));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 233:
                if (QJPermissionCheckUtils.allPermitted(grantResults)) {
                    Log.e(tag, "===========\nok");
                } else {
                    Log.e(tag, "===========\nfail");
                    QJPermissionCheckUtils.manuallyAuthorized(this, grantResults, map);

                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
