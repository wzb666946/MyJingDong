package com.example.com.myjingdong.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.com.myjingdong.inter.IBase;
import com.example.com.myjingdong.utils.SharedPreferencesUtils;

import javax.inject.Inject;

public abstract class BaseActivaty<T extends BaseContract.BasePresenter> extends AppCompatActivity implements IBase,BaseContract.BaseView{
   @Inject
    protected T mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject();
        //绑定
        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑
        mPresenter.detachView();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dimissLoading() {

    }
    @Override
    public void initView(View view) {

    }
    protected String getUid() {
        return (String) SharedPreferencesUtils.getParam(this, "uid", "");
    }

    protected String getToken() {
        return (String) SharedPreferencesUtils.getParam(this, "token", "");
    }

    protected void toast(String str){
        Toast.makeText(this,str, Toast.LENGTH_SHORT).show();
    }
}
