package com.example.com.myjingdong.myclass.net;

import com.example.com.myjingdong.myclass.bean.ChildBean;
import com.example.com.myjingdong.myclass.bean.RightBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ClassApiService {
    //home页已经获取到分类
    @FormUrlEncoded
    @POST("product/getProductCatagory")
    Observable<RightBean> getClassChild(@Field("cid") String cid);
    @FormUrlEncoded
    @POST("product/getProducts")
    Observable<ChildBean> getProducts(@Field("pscid") String pscid,@Field("page") String page);
}
