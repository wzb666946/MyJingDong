package com.example.com.myjingdong.my.net;

import com.example.com.myjingdong.my.bean.Loginbean;
import com.example.com.myjingdong.my.bean.Regbean;

import io.reactivex.Observable;

public class MyApi {
    private static MyApi myApi;
    private MyApiService myApiService;

    public MyApi(MyApiService myApiService) {
        this.myApiService = myApiService;
    }

    public static MyApi getMyApi(MyApiService myApiService) {
        if(myApi==null){
            myApi=new MyApi(myApiService);
        }
        return myApi;
    }
    public Observable<Loginbean> getLogin(String mobile,String password){
        return myApiService.getLogi(mobile,password);
    }
    public Observable<Regbean> getRegister(String mobile, String password){
        return myApiService.getRegister(mobile,password);

    }
}
