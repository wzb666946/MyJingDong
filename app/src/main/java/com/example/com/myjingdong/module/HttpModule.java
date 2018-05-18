package com.example.com.myjingdong.module;

import com.example.com.myjingdong.api.Api;
import com.example.com.myjingdong.home.net.AdApi;
import com.example.com.myjingdong.home.net.AdApiServicce;
import com.example.com.myjingdong.home.net.CatagoryApi;
import com.example.com.myjingdong.home.net.CatagoryApiService;
import com.example.com.myjingdong.my.net.AddCartApi;
import com.example.com.myjingdong.my.net.AddCartApiService;
import com.example.com.myjingdong.my.net.AddrsApi;
import com.example.com.myjingdong.my.net.AddrsApiService;
import com.example.com.myjingdong.my.net.MyApi;
import com.example.com.myjingdong.my.net.MyApiService;
import com.example.com.myjingdong.my.net.MyInterceptor;
import com.example.com.myjingdong.myclass.net.ClassApi;
import com.example.com.myjingdong.myclass.net.ClassApiService;
import com.example.com.myjingdong.shopcart.net.CreateOrderApi;
import com.example.com.myjingdong.shopcart.net.CreateOrderApiService;
import com.example.com.myjingdong.shopcart.net.DeleteCartApi;
import com.example.com.myjingdong.shopcart.net.DeleteCartApiService;
import com.example.com.myjingdong.shopcart.net.GetCartApi;
import com.example.com.myjingdong.shopcart.net.GetCartApiService;
import com.example.com.myjingdong.shopcart.net.UpdateCartApi;
import com.example.com.myjingdong.shopcart.net.UpdateCartApiService;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
@Module
public class HttpModule {
    @Provides
    OkHttpClient.Builder provideOkHttpClientBuilder(){
        return new OkHttpClient.Builder()
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .connectTimeout(10,TimeUnit.SECONDS);
    }
    @Provides
    AdApi provideAdApi(OkHttpClient.Builder builder){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Api.BANNER_API)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
        AdApiServicce adApiServicce=retrofit.create(AdApiServicce.class);
        return AdApi.getAdApi(adApiServicce);
    }
    @Provides
    CatagoryApi provideCatagoryApi(OkHttpClient.Builder builder){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Api.BANNER_API)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(builder.build())
            .build();
        CatagoryApiService catagoryApiService=retrofit.create(CatagoryApiService.class);
        return CatagoryApi.getCatagoryApi(catagoryApiService);
    }
    @Provides
    ClassApi myClassApi(OkHttpClient.Builder builder){
        Retrofit retrofit=new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Api.BANNER_API)
                .client(builder.build())
                .build();
        ClassApiService classApiService =retrofit.create(ClassApiService.class);
        return ClassApi.getClasApi(classApiService);
    }
    //登录
    @Provides
    MyApi myLoginapi(OkHttpClient.Builder builder){
        Retrofit retrofit=new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Api.BANNER_API)
                .client(builder.build())
                .build();
        MyApiService myApiService=retrofit.create(MyApiService.class);
        return MyApi.getMyApi(myApiService);
    }
    @Provides
    AddCartApi provideAddCartApi(OkHttpClient.Builder builder) {
        builder.addInterceptor(new MyInterceptor());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BANNER_API)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
        AddCartApiService addCartApiService = retrofit.create(AddCartApiService.class);
        return AddCartApi.getAddCartApi(addCartApiService);
    }

    @Provides
    GetCartApi provideGetCartApi(OkHttpClient.Builder builder) {
        builder.addInterceptor(new MyInterceptor());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BANNER_API)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
        GetCartApiService getCartApiService = retrofit.create(GetCartApiService.class);
        return GetCartApi.getGetCartApi(getCartApiService);
    }

    @Provides
    UpdateCartApi provideUpdateCartApi(OkHttpClient.Builder builder) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BANNER_API)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
        UpdateCartApiService updateCartApiService = retrofit.create(UpdateCartApiService.class);
        return UpdateCartApi.getUpdateCartApi(updateCartApiService);
    }

    @Provides
    DeleteCartApi provideDeleteCartApi(OkHttpClient.Builder builder) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BANNER_API)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
        DeleteCartApiService deleteCartApiService = retrofit.create(DeleteCartApiService.class);
        return DeleteCartApi.getDeleteCartApi(deleteCartApiService);
    }

    @Provides
    AddrsApi provideAddrsApi(OkHttpClient.Builder builder) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BANNER_API)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
        AddrsApiService addrsApiService = retrofit.create(AddrsApiService.class);
        return AddrsApi.getAddrsApi(addrsApiService);
    }

    @Provides
    CreateOrderApi provideCreateOrderApi(OkHttpClient.Builder builder) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BANNER_API)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
        CreateOrderApiService createOrderApiService = retrofit.create(CreateOrderApiService.class);
        return CreateOrderApi.getCreateOrderApi(createOrderApiService);
    }
}
