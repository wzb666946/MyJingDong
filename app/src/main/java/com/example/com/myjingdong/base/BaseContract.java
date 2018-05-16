package com.example.com.myjingdong.base;

public interface BaseContract  {
    interface BasePresenter<T extends BaseContract.BaseView>{
        void attachView(T view);
        void detachView();
    };
    interface BaseView{
        void showLoading();
        void dimissLoading();
    }
}
