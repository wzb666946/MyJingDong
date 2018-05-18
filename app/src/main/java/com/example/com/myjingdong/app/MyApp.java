package com.example.com.myjingdong.app;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

public class MyApp extends Application {
    {
        PlatformConfig.setQQZone("1106868124", "Bn1yBsE3bRmqNZDZ");
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        UMConfigure.init(this,"5ae2bc9db27b0a0787000224"
                ,"umeng",UMConfigure.DEVICE_TYPE_PHONE,"");
    }


}
