package com.example.com.myjingdong.my.net;

import com.example.com.myjingdong.my.bean.Loginbean;
import com.example.com.myjingdong.my.bean.Regbean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MyApiService {
    @FormUrlEncoded
    @POST("user/login")
    Observable<Loginbean> getLogi(@Field("mobile") String mobile,@Field("password")String password);
    @FormUrlEncoded
    @POST("user/reg")
    Observable<Regbean> getRegister(@Field("mobile") String mobile, @Field("password")String password);
}
