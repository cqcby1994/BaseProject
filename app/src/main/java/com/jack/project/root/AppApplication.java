package com.jack.project.root;

import android.app.Application;

import com.blankj.utilcode.util.ActivityUtils;
import com.jack.project.root.utils.KLog;
import com.jack.project.root.utils.UserUtil;
import com.jack.project.root.utils.Utils;

/**
 * @author: chenqc
 * @date: 1/3/21
 */
public class AppApplication extends Application {
    public static final String USER_UP_BEAN_KEY = "detection_user";
    public static final String SHOPPING_CART_BEAN_KEY = "detection_shopping_cart";

    public static final String IS_SHOW_GUIDE_KEY = "is_show_guide";

    public static final boolean isDEBUG = true;
    @Override
    public void onCreate() {
        super.onCreate();
    }
    public void logout() {
        // clearCache();
//        ActivityManager activityManager = ActivityManager.getInstance();
//        activityManager.finish(getMainActivityTag());
        ActivityUtils.finishAllActivities();
        UserUtil.getInstance().setUserUpBean(this, null);
        Utils.init(this);
        KLog.init(isDEBUG);
    }
}
