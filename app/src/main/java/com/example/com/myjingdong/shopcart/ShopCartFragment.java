package com.example.com.myjingdong.shopcart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.com.myjingdong.R;
import com.example.com.myjingdong.base.BaseFragment;
import com.example.com.myjingdong.componnet.DaggerHttpComponent;
import com.example.com.myjingdong.home.adapter.RvRecommendAdapter;
import com.example.com.myjingdong.home.bean.AdBean;
import com.example.com.myjingdong.my.bean.eventbus.MessageEvent;
import com.example.com.myjingdong.my.ui.MakeSureOrderActivity;
import com.example.com.myjingdong.shopcart.adapter.ElvShopcartAdapter;
import com.example.com.myjingdong.shopcart.bean.GetCartsBean;
import com.example.com.myjingdong.shopcart.bean.SellerBean;
import com.example.com.myjingdong.shopcart.contract.ShopcartContract;
import com.example.com.myjingdong.shopcart.presenter.ShopcartPresenter;
import com.example.com.myjingdong.utils.DialogUtil;
import com.example.com.myjingdong.utils.SharedPreferencesUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class ShopCartFragment extends BaseFragment<ShopcartPresenter> implements ShopcartContract.View {
    private ExpandableListView mElv;
    /**
     * 全选
     */
    private CheckBox mCbAll;
    /**
     * 合计：
     */
    private TextView mTvMoney;
    /**
     * 去结算：
     */
    private TextView mTvTotal;
    private ProgressDialog progressDialog;
    private ElvShopcartAdapter adapter;
    private View view;
    /**
     * 购物车
     */
    private TextView mDd;
    /**
     * 为您推荐
     */
    private TextView mTj;
    private RecyclerView mWwtj;

    @Override
    public int getConnectLayout() {
        return R.layout.activity_shop_cart;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getConnectLayout(), null);
        initView(view);
        return view;
    }

    @Override
    public void inject() {
        DaggerHttpComponent.builder()
                .build()
                .inject(this);
    }

    @Override
    public void initView(View view) {
        mElv = (ExpandableListView) view.findViewById(R.id.elv);
        mCbAll = (CheckBox) view.findViewById(R.id.cbAll);
        mTvMoney = (TextView) view.findViewById(R.id.tvMoney);
        mTvTotal = (TextView) view.findViewById(R.id.tvTotal);
        mElv.setGroupIndicator(null);
        mCbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter != null) {
                    progressDialog.show();
                    adapter.changeAllState(mCbAll.isChecked());
                }
            }
        });
        mTvTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MakeSureOrderActivity.class);
                startActivity(intent);
                //把用户选中的商品传过去
                List<SellerBean> gList = adapter.getGroupList();
                List<List<GetCartsBean.DataBean.ListBean>> cList = adapter.getchildList();
                MessageEvent messageEvent = new MessageEvent();
                messageEvent.setcList(cList);
                messageEvent.setgList(gList);
                EventBus.getDefault().postSticky(messageEvent);

            }
        });
        //初始化dialog
        progressDialog = DialogUtil.getProgressDialog(getActivity());
        String token = (String) SharedPreferencesUtils.getParam(getActivity(), "token", "");
        String uid = (String) SharedPreferencesUtils.getParam(getActivity(), "uid", "");
        mPersenter.getCarts(uid, token);
        mPersenter.TuiJian();
        mWwtj = (RecyclerView) view.findViewById(R.id.wwtj);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
        mWwtj.setLayoutManager(gridLayoutManager2);
    }

    @Override
    public void showCartList(List<SellerBean> groupList, List<List<GetCartsBean.DataBean.ListBean>> childList) {
        //判断所有商家是否全部选中
        mCbAll.setChecked(isSellerAddSelected(groupList));

        //创建适配器
        adapter = new ElvShopcartAdapter(getActivity(), groupList, childList, mPersenter,
                progressDialog);
        mElv.setAdapter(adapter);
        //获取数量和总价
        String[] strings = adapter.computeMoneyAndNum();
        mTvMoney.setText("总计：" + strings[0] + "元");
        mTvTotal.setText("去结算(" + strings[1] + "个)");
        //        //默认展开列表
        for (int i = 0; i < groupList.size(); i++) {
            mElv.expandGroup(i);
        }
        //关闭进度条
        progressDialog.dismiss();
    }

    @Override
    public void updateCartsSuccess(String msg) {
        if (adapter != null) {
            adapter.updataSuccess();
        }
    }

    @Override
    public void deleteCartSuccess(String msg) {
        //调用适配器里的delSuccess()方法
        if (adapter != null) {
            adapter.delSuccess();
        }
    }

    @Override
    public void TuiJianSuccess(AdBean adBean) {
        RvRecommendAdapter rvRecommendAdapter = new RvRecommendAdapter(getActivity(), adBean.getTuijian().getList());
        mWwtj.setAdapter(rvRecommendAdapter);
    }

    /**
     * 判断所有商家是否全部选中
     * .......
     *
     * @param groupList
     * @return
     */
    private boolean isSellerAddSelected(List<SellerBean> groupList) {
        for (int i = 0; i < groupList.size(); i++) {
            SellerBean sellerBean = groupList.get(i);
            if (!sellerBean.isSelected()) {
                return false;
            }
        }
        return true;
    }

}
