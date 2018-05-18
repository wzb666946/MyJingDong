package com.example.com.myjingdong.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.com.myjingdong.inter.IBase;

import javax.inject.Inject;

import butterknife.Unbinder;

public abstract class BaseFragment<T extends BaseContract.BasePresenter> extends Fragment implements IBase,BaseContract.BaseView{
    @Inject
    protected  T mPersenter;
    private Unbinder unbinder;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject();
        //绑定
        if(mPersenter!=null){
            mPersenter.attachView(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解绑
       if(mPersenter!=null){
           mPersenter.detachView();
       }
       if(unbinder!=null){
           unbinder.unbind();
       }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(getConnectLayout(),null);
        initView(view);
        return view;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dimissLoading() {

    }

}
