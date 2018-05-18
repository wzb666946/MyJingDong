package com.example.com.myjingdong.shopcart.presenter;

import com.example.com.myjingdong.base.BasePresenter;
import com.example.com.myjingdong.home.bean.AdBean;
import com.example.com.myjingdong.home.net.AdApi;
import com.example.com.myjingdong.shopcart.bean.BaseBean;
import com.example.com.myjingdong.shopcart.bean.GetCartsBean;
import com.example.com.myjingdong.shopcart.bean.SellerBean;
import com.example.com.myjingdong.shopcart.contract.ShopcartContract;
import com.example.com.myjingdong.shopcart.net.DeleteCartApi;
import com.example.com.myjingdong.shopcart.net.GetCartApi;
import com.example.com.myjingdong.shopcart.net.UpdateCartApi;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ShopcartPresenter extends BasePresenter<ShopcartContract.View> implements ShopcartContract.Presenter {
    private GetCartApi getCartApi;
    private UpdateCartApi updateCartApi;
    private DeleteCartApi deleteCartApi;
    private AdApi adApi;
    @Inject
    public ShopcartPresenter(GetCartApi getCartApi, UpdateCartApi updateCartApi, DeleteCartApi deleteCartApi,AdApi adApi) {
        this.getCartApi = getCartApi;
        this.updateCartApi = updateCartApi;
        this.deleteCartApi = deleteCartApi;
        this.adApi=adApi;
    }

    @Override
    public void getCarts(String uid, String token) {
        getCartApi.getCatagory(uid, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GetCartsBean>() {
                    @Override
                    public void accept(GetCartsBean getCartsBean) throws Exception {
                        List<SellerBean> groupList = new ArrayList<>();//用于封装一级列表
                        List<List<GetCartsBean.DataBean.ListBean>> childList = new ArrayList<>();//用于封装二级列表
                        List<GetCartsBean.DataBean> data = getCartsBean.getData();
                        if (data == null) {
                            return;
                        }
                        for (int i = 0; i < data.size(); i++) {
                            GetCartsBean.DataBean dataBean = data.get(i);
                            SellerBean sellerBean = new SellerBean();
                            sellerBean.setSellerName(dataBean.getSellerName());
                            sellerBean.setSellerid(dataBean.getSellerid());
                            sellerBean.setSelected(isSellerProductAllSelect(dataBean));
                            //true或者false要根据该商家下面的商品是否全选
                            groupList.add(sellerBean);

                            List<GetCartsBean.DataBean.ListBean> list = dataBean.getList();
                            childList.add(list);
                        }
                        if (mView != null) {
                            mView.showCartList(groupList, childList);
                        }
                    }
                });
    }

    @Override
    public void updateCarts(String uid, String sellerid, String pid, String num, String selected, String token) {
        updateCartApi.updateCarts(uid, sellerid, pid, num, selected, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<BaseBean, String>() {
                    @Override
                    public String apply(BaseBean baseBean) throws Exception {
                        return baseBean.getMsg();
                    }
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                if (mView != null) {
                    mView.updateCartsSuccess(s);
                }
            }
        });
    }

    @Override
    public void deleteCart(String uid, String pid, String token) {
        deleteCartApi.deleteCart(uid, pid, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<BaseBean, String>() {
                    @Override
                    public String apply(BaseBean baseBean) throws Exception {
                        return baseBean.getMsg();
                    }
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                if (mView != null) {
                    mView.updateCartsSuccess(s);
                }
            }
        });
    }
    //推荐
    @Override
    public void TuiJian() {
        adApi.getAd()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<AdBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AdBean adBean) {
                        mView.TuiJianSuccess(adBean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 判断该商家下面的商品是否全选
     *
     * @return
     */
    private boolean isSellerProductAllSelect(GetCartsBean.DataBean dataBean) {
        //获取该商家下面的所有商品
        List<GetCartsBean.DataBean.ListBean> list = dataBean.getList();
        for (int i = 0; i < list.size(); i++) {
            GetCartsBean.DataBean.ListBean listBean = list.get(i);
            if (0 == listBean.getSelected()) {
                //如果是0的话，表示有一个商品未选中
                return false;
            }
        }
        return true;

    }

}
