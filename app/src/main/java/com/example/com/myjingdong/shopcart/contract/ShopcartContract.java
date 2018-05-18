package com.example.com.myjingdong.shopcart.contract;


import com.example.com.myjingdong.base.BaseContract;
import com.example.com.myjingdong.home.bean.AdBean;
import com.example.com.myjingdong.shopcart.bean.GetCartsBean;
import com.example.com.myjingdong.shopcart.bean.SellerBean;

import java.util.List;

public interface ShopcartContract {
    interface View extends BaseContract.BaseView {
        void showCartList(List<SellerBean> groupList, List<List<GetCartsBean.DataBean.ListBean>> childList);

        void updateCartsSuccess(String msg);

        void deleteCartSuccess(String msg);

        void TuiJianSuccess(AdBean adBean);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getCarts(String uid, String token);

        void updateCarts(String uid, String sellerid, String pid, String num, String selected, String token);

        void deleteCart(String uid, String pid, String token);

        void TuiJian();
    }
}
