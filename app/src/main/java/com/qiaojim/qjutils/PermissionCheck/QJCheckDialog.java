package com.qiaojim.qjutils.PermissionCheck;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
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

    public AlertDialog alertDialog;     //显示的对话框对象
    public Activity activity;
    public View view;           //自定义对话框布局view
    public TextView textView;   //显示提示文字的tv
    public Button positiveBtn, negativeBtn;
    public String positiveTxt, negativeTxt;

    public QJCheckDialog(Activity activity) {
        this.activity=activity;
    }

    public static class Builder {
        private QJCheckDialog qjCheckDialog;

        public Builder(Activity activity) {
            qjCheckDialog = new QJCheckDialog(activity);
        }

        /**
         * 设置自定义对话框背景view
         * @param view
         * @return
         */
        public Builder setCustomView(View view){
            qjCheckDialog.view=view;
            return this;
        }

        /**
         * 设定显示提示信息的textview
         * @param textView
         * @return
         */
        public Builder setMsgView(TextView textView) {
            qjCheckDialog.textView = textView;
            return this;
        }

        /**
         * 设定确认按钮及按钮显示的文字
         * @param btn
         * @param txt
         * @return
         */
        public Builder setNegativeBtn(Button btn, String txt) {
            qjCheckDialog.negativeBtn = btn;
            qjCheckDialog.negativeTxt = txt;
            return this;
        }

        /**
         * 设定取消按钮及按钮显示的文字
         * @param btn
         * @param txt
         * @return
         */
        public Builder setPositiveBtn(Button btn, String txt) {
            qjCheckDialog.positiveBtn = btn;
            qjCheckDialog.positiveTxt = txt;
            return this;
        }

        public QJCheckDialog create() {
            return qjCheckDialog;
        }
    }

}
