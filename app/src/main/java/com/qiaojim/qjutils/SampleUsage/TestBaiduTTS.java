package com.qiaojim.qjutils.SampleUsage;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.TtsMode;
import com.qiaojim.qjutils.BaiduTTS.NonBlockSyntherizer;
import com.qiaojim.qjutils.BaiduTTS.OfflineResource;
import com.qiaojim.qjutils.BaiduTTS.QJBaiduTtsUtils;
import com.qiaojim.qjutils.BaiduTTS.QJTtsInitConfig;
import com.qiaojim.qjutils.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestBaiduTTS extends AppCompatActivity {

    private EditText editText;
    private Button button;

    private NonBlockSyntherizer speechSynthesizer;

    protected String appId = "10457134";
    protected String appKey = "TeUEBcZNsLy2mUT9XfWUdVuw";
    protected String secretKey = "3GQqnQKa47G7nGuteQ6ldNhP4oWobnZd ";
    // TtsMode.MIX; 离在线融合，在线优先； TtsMode.ONLINE 纯在线； 没有纯离线
    protected TtsMode ttsMode = TtsMode.MIX;
    // 离线发音选择，VOICE_FEMALE即为离线女声发音。
    // assets目录下bd_etts_speech_female.data为离线男声模型；bd_etts_speech_female.data为离线女声模型
    protected String offlineVoice = OfflineResource.VOICE_FEMALE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_tts);

        initPermission();
        initView();

        // 初始化TTS引擎，封装工具使用示例
        initialTts();
    }

    /**
     * QJBaiduTtsUtils 封装工具使用
     */
    protected void initialTts() {
        /*设置授权信息，注册应用服务*/
        QJTtsInitConfig config = new QJTtsInitConfig();
        config.setApiKey(appKey);
        config.setAppId(appId);
        config.setOfflineVoice(offlineVoice);
        config.setSecretKey(secretKey);
        config.setTtsMode(ttsMode);

        //获取默认实例或自定义实例，2种调用方法

        /*获取默认实例，调用其speak（str）方法，即可播报str
        speechSynthesizer = QJBaiduTtsUtils.initTts(this, config);*/

        /*获取自定义实例*/
        Map<String, String> params = new HashMap<String, String>();
        // 更多自定义参数还是参考官网吧
        params.put(SpeechSynthesizer.PARAM_SPEAKER, "0");   // 设置在线发声音人： 0 普通女声（默认） 1 普通男声 2 特别男声 3 情感男声<度逍遥> 4 情感儿童声<度丫丫>
        params.put(SpeechSynthesizer.PARAM_VOLUME, "5");    // 设置合成的音量，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_SPEED, "5");     // 设置合成的语速，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_PITCH, "5");     // 设置合成的语调，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);         // 该参数设置为TtsMode
        speechSynthesizer = QJBaiduTtsUtils.initTts(this, config);

    }

    @Override
    protected void onPause() {
        super.onPause();
        speechSynthesizer.release();
    }

    private void initView() {
        editText = findViewById(R.id.input);
        button = findViewById(R.id.speak);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*语音播报输入框内容*/
                String text = editText.getText().toString();
                speechSynthesizer.speak(text);
            }
        });
    }

    /*android 6.0 以上需要动态申请权限*/
    private void initPermission() {
        String permissions[] = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE
        };

        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                //进入到这里代表没有权限.
            }
        }
        String tmpList[] = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
