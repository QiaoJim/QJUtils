package com.qiaojim.qjutils.BaiduTTS;

import com.baidu.tts.client.TtsMode;

/**
 * Author: QiaoJim
 * Date:  2017/11/29
 * QQ: 919106848
 * Desc:
 */
public class QJTtsInitConfig {

    private String appId;
    private String apiKey;
    private String secretKey;
    private TtsMode ttsMode;
    private String offlineVoice;

    public TtsMode getTtsMode() {
        return ttsMode;
    }

    public void setTtsMode(TtsMode ttsMode) {
        this.ttsMode = ttsMode;
    }

    public String getOfflineVoice() {
        return offlineVoice;
    }

    public void setOfflineVoice(String offlineVoice) {
        this.offlineVoice = offlineVoice;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
