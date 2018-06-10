package com.example.com.myjingdong.componnet;

import com.example.com.myjingdong.home.ui.HomeFragment;
import com.example.com.myjingdong.module.HttpModule;
import com.example.com.myjingdong.my.ui.LoginActivity;
import com.example.com.myjingdong.my.ui.MakeSureOrderActivity;
import com.example.com.myjingdong.my.ui.RegisterActivity;
import com.example.com.myjingdong.my.ui.UserInfoActivity;
import com.example.com.myjingdong.myclass.ui.ClassFragment;
import com.example.com.myjingdong.myclass.ui.MainChildActivity;
import com.example.com.myjingdong.myclass.ui.XqActivity;
import com.example.com.myjingdong.shopcart.ShopCartActivity;
import com.example.com.myjingdong.shopcart.ShopCartFragment;

import dagger.Component;

@Component(modules = HttpModule.class)
public interface HttpComponent {
    void inject(HomeFragment homeFragment);
    void inject(ClassFragment classFragment);
    void inject(MainChildActivity mainChildActivity);
    void inject(LoginActivity loginActivity);
    void inject(RegisterActivity registerActivity);
    void inject(ShopCartActivity shopCartActivity);
    void inject(MakeSureOrderActivity makeSureOrderActivity);
    void inject(XqActivity xqActivity);
    void inject(ShopCartFragment shopCartFragment);
    void inject(UserInfoActivity userInfoActivity);
}
