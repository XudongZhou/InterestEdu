package com.shadow.interestedu;

import android.app.Application;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.RequestQueue;

/**
 * Created by admin on 2016/9/26.
 */

public class AppApplication extends Application {

    public RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();

        NoHttp.initialize(this);
        Logger.setDebug(true);
        requestQueue = NoHttp.newRequestQueue();

        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        //初始化
        EMClient.getInstance().init(this, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
    }
}
