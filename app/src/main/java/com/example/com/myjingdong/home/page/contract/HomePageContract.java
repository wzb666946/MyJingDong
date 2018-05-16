package com.example.com.myjingdong.home.page.contract;

import com.example.com.myjingdong.base.BaseContract;
import com.example.com.myjingdong.home.bean.AdBean;
import com.example.com.myjingdong.home.bean.CatagoryBean;

public interface HomePageContract {
    interface View extends BaseContract.BaseView{
        void getAdSuccess(AdBean adBean);
        void getCatagory(CatagoryBean catagoryBean);
    }
    interface Presenter extends BaseContract.BasePresenter<View>{
        void getAd();
        void getCatagory();
    }
}
