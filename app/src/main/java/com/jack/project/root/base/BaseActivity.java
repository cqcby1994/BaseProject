package com.jack.project.root.base;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.jack.project.root.utils.NavigationBarUtil;
import com.jack.project.root.widget.dialog.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public abstract class BaseActivity extends AppCompatActivity {
    private Dialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(NavigationBarUtil.hasNavigationBar(this)){
            NavigationBarUtil.initActivity(findViewById(android.R.id.content));
        }
        setContentView(contentView());
        initBaseView();
        initBase();
        initData();
        initView();
        initEvent();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    private void initBase() {
        EventBus.getDefault().register(this);
    }

    protected abstract int contentView();

    protected abstract void initData();

    protected abstract void initView();

    protected abstract void initEvent();

    @Subscribe
    public void baseEvent(BaseEvent event) {

    }
    private void initBaseView(){
        Window window = getWindow();
//        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        //window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    public  void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        if (imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
    }

        @Override
    protected void onDestroy() {
        closeDialog();
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
    public void showDialog() {
        if (dialog == null) {
            dialog = LoadingDialog.createLoadingDialog(this, "加载中...");
            dialog.show();
        }
    }
    public void showDialog(String msg) {
        if (dialog == null) {
            dialog = LoadingDialog.createLoadingDialog(this, msg);
            dialog.show();
        }
    }
    /**
     * 关闭Dialog
     */
    public void closeDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
