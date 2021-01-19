package com.jack.project.root.utils;

import android.content.Context;

import com.jack.project.root.AppApplication;
import com.jack.project.root.bean.UserBean;

public class UserUtil {
    private static UserBean userUpBean;
    private static UserUtil userUtil;
    private static SharedPreferencesUtils sharedPreferencesUtils;
    public static String USER_UP_BEAN_SHARED = "userUpBean.shared";
    public static String SHOPPING_CART_BEAN_SHARED = "shoppingCart.shared";

    private UserUtil() {

    }

    public static UserUtil getInstance() {
        if (userUtil == null) {
            synchronized (UserUtil.class) {
                if (userUtil == null) {
                    userUtil = new UserUtil();

                }
            }
        }
        return userUtil;
    }

    public UserBean getUserUpBean(Context context) {
        if (userUpBean == null) {
            if (sharedPreferencesUtils == null) {
                synchronized (UserUtil.class) {
                    sharedPreferencesUtils = new SharedPreferencesUtils(context, USER_UP_BEAN_SHARED);
                }
            }
            userUpBean = sharedPreferencesUtils.getObject(AppApplication.USER_UP_BEAN_KEY, UserBean.class);
        }
        return userUpBean != null ? userUpBean.clone() : null;
    }

    public void setUserUpBean(Context context, UserBean userUpBean) {
        if (sharedPreferencesUtils == null) {
            synchronized (UserUtil.class) {
                sharedPreferencesUtils = new SharedPreferencesUtils(context, USER_UP_BEAN_SHARED);
            }
        }
        sharedPreferencesUtils.setObject(AppApplication.USER_UP_BEAN_KEY, userUpBean);
        UserUtil.userUpBean = userUpBean;
    }



}
