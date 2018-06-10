package com.example.com.myjingdong.home.page.presenter;

import com.example.com.myjingdong.base.BasePresenter;
import com.example.com.myjingdong.home.bean.AdBean;
import com.example.com.myjingdong.home.bean.CatagoryBean;
import com.example.com.myjingdong.home.net.AdApi;
import com.example.com.myjingdong.home.net.CatagoryApi;
import com.example.com.myjingdong.home.page.contract.HomePageContract;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomePagePresenter extends BasePresenter<HomePageContract.View> implements HomePageContract.Presenter{
    private AdApi adApi;
    private CatagoryApi catagoryApi;
    @Inject
    public HomePagePresenter(AdApi adApi,CatagoryApi catagoryApi){
        this.adApi=adApi;
        this.catagoryApi=catagoryApi;
    }
    @Override
    public void getAd() {
        adApi.getAd()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<AdBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AdBean adBean) {
                       if(mView!=null){
                           mView.getAdSuccess(adBean);
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
                        mView.getCatagory(catagoryBean);
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
