package com.qiaojim.qjutils.BaiduTTS;

import android.content.Context;
import android.os.Handler;

import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.qiaojim.qjutils.PermissionCheck.QJPermissionCheckUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: QiaoJim
 * Date:  2017/11/29
 * QQ: 919106848
 * Desc:
 */
public class QJBaiduTtsUtils {

    public static NonBlockSyntherizer initTts(Context context, QJTtsInitConfig qjTtsInitConfig, Map<String, String> params) {

        /*初始化参数均默认*/
        if (params == null) {
            params = getDefaultParams();
        }
        Handler handler = new Handler();
        SpeechSynthesizerListener listener = new UiMessageListener(handler);
        InitConfig initConfig = new InitConfig(qjTtsInitConfig.getAppId(),
                qjTtsInitConfig.getApiKey(), qjTtsInitConfig.getSecretKey(),
                qjTtsInitConfig.getTtsMode(), qjTtsInitConfig.getOfflineVoice(),
                params, listener);

        return new NonBlockSyntherizer(context, initConfig, handler); // 此处可以改为MySyntherizer 了解调用过程
    }

    public static NonBlockSyntherizer initTts(Context context, QJTtsInitConfig qjTtsInitConfig) {

        return initTts(context, qjTtsInitConfig, null);
    }

    private static Map<String, String> getDefaultParams() {
        Map<String, String> params = new HashMap<String, String>();
        // 以下参数均为选填
        params.put(SpeechSynthesizer.PARAM_SPEAKER, "0"); // 设置在线发声音人： 0 普通女声（默认） 1 普通男声 2 特别男声 3 情感男声<度逍遥> 4 情感儿童声<度丫丫>
        params.put(SpeechSynthesizer.PARAM_VOLUME, "5"); // 设置合成的音量，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_SPEED, "5");// 设置合成的语速，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_PITCH, "5");// 设置合成的语调，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);         // 该参数设置为TtsMode.MIX生效。即纯在线模式不生效。
        // MIX_MODE_DEFAULT 默认 ，wifi状态下使用在线，非wifi离线。在线状态下，请求超时6s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE_WIFI wifi状态下使用在线，非wifi离线。在线状态下， 请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_NETWORK ， 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE, 2G 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线
        return params;
    }
}
