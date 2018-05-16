package com.example.com.myjingdong.home.net;

import com.example.com.myjingdong.home.bean.AdBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface AdApiServicce {
    @GET("ad/getAd")
    Observable<AdBean> getAd();
}
