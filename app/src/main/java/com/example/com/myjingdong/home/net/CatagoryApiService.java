package com.example.com.myjingdong.home.net;

import com.example.com.myjingdong.home.bean.CatagoryBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface CatagoryApiService {
    @GET("product/getCatagory")
    Observable<CatagoryBean> getCatagory();
}
