package com.example.com.myjingdong.myclass.contrace;

import com.example.com.myjingdong.base.BaseContract;
import com.example.com.myjingdong.home.bean.CatagoryBean;
import com.example.com.myjingdong.myclass.bean.ChildBean;
import com.example.com.myjingdong.myclass.bean.RightBean;

public interface ClassContrace {
    interface View extends BaseContract.BaseView{
        void getClassChildSuccess(RightBean rightBean);
        void getClassCatagory(CatagoryBean catagoryBean);
        void  getProducts(ChildBean childBean);
    }
    interface Presenter extends BaseContract.BasePresenter<View>{
        void getClassChild(String cid);
        void getCatagory();
        void getProducts(String pscid,String page);
    }
}
