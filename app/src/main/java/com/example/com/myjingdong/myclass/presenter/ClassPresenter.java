package com.example.com.myjingdong.myclass.presenter;

import com.example.com.myjingdong.base.BaseContract;
import com.example.com.myjingdong.base.BasePresenter;
import com.example.com.myjingdong.home.bean.CatagoryBean;
import com.example.com.myjingdong.home.net.CatagoryApi;
import com.example.com.myjingdong.myclass.bean.ChildBean;
import com.example.com.myjingdong.myclass.bean.RightBean;
import com.example.com.myjingdong.myclass.contrace.ClassContrace;
import com.example.com.myjingdong.myclass.net.ClassApi;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ClassPresenter extends BasePresenter<ClassContrace.View> implements ClassContrace.Presenter{
    private ClassApi classApi;
    private CatagoryApi catagoryApi;
    @Inject
    public ClassPresenter(ClassApi classApi, CatagoryApi catagoryApi) {
        this.classApi = classApi;
        this.catagoryApi = catagoryApi;
    }

    @Override
    public void getClassChild(String cid) {
        classApi.getClassChild(cid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<RightBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RightBean rightBean) {
                        if(mView!=null){
                            mView.getClassChildSuccess(rightBean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getCatagory() {
        catagoryApi.getCatagory()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<CatagoryBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CatagoryBean catagoryBean) {
                        mView.getClassCatagory(catagoryBean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getProducts(String pscid,String page) {
        classApi.getProducts(pscid,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ChildBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ChildBean childBean) {
                        mView.getProducts(childBean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
