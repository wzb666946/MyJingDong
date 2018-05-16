package com.example.com.myjingdong.my.contract;

import com.example.com.myjingdong.base.BaseContract;
import com.example.com.myjingdong.my.bean.Loginbean;
import com.example.com.myjingdong.my.bean.Regbean;

public interface LoginContract {
    interface View extends BaseContract.BaseView{
        void showLoginimges(Loginbean loginbean);
        void showRegister(Regbean regbean);
    }
    interface Presenter extends BaseContract.BasePresenter<View>{
        void getLoginimgess(String mobile,String password);
        void getRegister(String mobile,String password);
    }
}
