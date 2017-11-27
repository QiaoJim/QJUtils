package com.qiaojim.qjutils.PermissionCheck;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Author: QiaoJim
 * Date:  2017/11/27
 * QQ: 919106848
 * Desc:
 */
public class QJCheckDialog {

    public static class Builder {
        private QJCheckDialog dialog;

        public Builder(Activity activity) {
            dialog = new QJCheckDialog();
            dialog.activity = activity;
        }

        public Builder setCustomView(View view){
            dialog.view=view;
            return this;
        }

        public Builder setMsgView(TextView textView) {
            dialog.textView = textView;
            return this;
        }

        public Builder setNegativeBtn(Button btn, String txt) {
            dialog.noBtn = btn;
            dialog.noTxt = txt;
            return this;
        }

        public Builder setPositiveBtn(Button btn, String txt) {
            dialog.okBtn = btn;
            dialog.okTxt = txt;
            return this;
        }

        public QJCheckDialog create() {
            return dialog;
        }
    }

    public Activity activity;
    public View view;
    public TextView textView;
    public Button okBtn, noBtn;
    public String okTxt, noTxt;


}
