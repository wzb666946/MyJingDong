package com.example.com.myjingdong.componnet;

import com.example.com.myjingdong.home.ui.HomeFragment;
import com.example.com.myjingdong.module.HttpModule;
import com.example.com.myjingdong.my.ui.LoginActivity;
import com.example.com.myjingdong.my.ui.RegisterActivity;
import com.example.com.myjingdong.myclass.ui.ClassFragment;
import com.example.com.myjingdong.myclass.ui.MainChildActivity;

import dagger.Component;

@Component(modules = HttpModule.class)
public interface HttpComponent {
    void inject(HomeFragment homeFragment);
    void inject(ClassFragment classFragment);
    void inject(MainChildActivity mainChildActivity);
    void inject(LoginActivity loginActivity);
    void inject(RegisterActivity registerActivity);
}
