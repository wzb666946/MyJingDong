package com.example.com.myjingdong.myclass.net;

import com.example.com.myjingdong.myclass.bean.ChildBean;
import com.example.com.myjingdong.myclass.bean.RightBean;

import io.reactivex.Observable;

public class ClassApi {
    private static ClassApi classApi;
    private ClassApiService classApiService;

    private ClassApi(ClassApiService classApiService) {
        this.classApiService = classApiService;
    }

    public static ClassApi getClasApi(ClassApiService classApiService) {
        if (classApi ==null){
            classApi =new ClassApi(classApiService);
        }
        return classApi;
    }
    public Observable<RightBean> getClassChild(String cid){
        return classApiService.getClassChild(cid);
    }
    public Observable<ChildBean> getProducts(String pscid,String page){
        return classApiService.getProducts(pscid,page);
    }
}
