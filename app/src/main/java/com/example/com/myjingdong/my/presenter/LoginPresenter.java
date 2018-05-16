package com.example.com.myjingdong.my.presenter;

import com.example.com.myjingdong.base.BasePresenter;
import com.example.com.myjingdong.my.bean.Loginbean;
import com.example.com.myjingdong.my.bean.Regbean;
import com.example.com.myjingdong.my.contract.LoginContract;
import com.example.com.myjingdong.my.net.MyApi;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter{
    private MyApi myApi;
    @Inject
    public LoginPresenter(MyApi myApi) {
        this.myApi = myApi;
    }


    @Override
    public void getLoginimgess(String mobile, String password) {
        myApi.getLogin(mobile,password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Loginbean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Loginbean loginbean) {
                        mView.showLoginimges(loginbean);
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
    public void getRegister(String mobile, String password) {
        myApi.getRegister(mobile,password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Regbean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Regbean regbean) {
                        mView.showRegister(regbean);
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
