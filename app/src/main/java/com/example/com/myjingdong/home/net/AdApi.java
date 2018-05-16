package com.example.com.myjingdong.home.net;

import com.example.com.myjingdong.home.bean.AdBean;

import io.reactivex.Observable;

public class AdApi {
    private static AdApi adApi;
    private  AdApiServicce adApiServicce;
    private  AdApi(AdApiServicce adApiServicce){
        this.adApiServicce=adApiServicce;
    }
    public static AdApi getAdApi(AdApiServicce adApiServicce){
        if(adApi==null){
            adApi=new AdApi(adApiServicce);
        }
        return adApi;
    }
    public Observable<AdBean> getAd(){
        return adApiServicce.getAd();
    }
}
