package com.jack.project.root.widget.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jack.project.root.R;


/**
 * @author: chenqc
 * @date: 11/24/20
 */
public class LoadingDialog {

        /**
         * 得到自定义的progressDialog
         *
         * @param context
         * @param msg
         * @return
         */
        public static Dialog createLoadingDialog(Context context, String msg) {

            // 首先得到整个View
            @SuppressLint("InflateParams")
            View view = LayoutInflater.from(context).inflate(
                    R.layout.dialog_loading, null);
            // 获取整个布局
            LinearLayout layout = (LinearLayout) view
                    .findViewById(R.id.dialog_view);
            // 页面中的Img
            ImageView img = (ImageView) view.findViewById(R.id.img);
            // 页面中显示文本
            TextView tipText = (TextView) view.findViewById(R.id.tipTextView);
            //Glide.with(context).asGif().load(R.mipmap.loading).into(img);
            // 显示文本
            tipText.setText(msg);

            // 创建自定义样式的Dialog
            Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);
            // 设置返回键无效
            loadingDialog.setCancelable(false);
            loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));

            return loadingDialog;
        }


}
