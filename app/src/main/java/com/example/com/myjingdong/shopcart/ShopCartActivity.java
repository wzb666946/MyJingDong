package com.example.com.myjingdong.shopcart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;


import com.example.com.myjingdong.R;
import com.example.com.myjingdong.base.BaseActivaty;
import com.example.com.myjingdong.componnet.DaggerHttpComponent;
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

public class ShopCartActivity extends BaseActivaty<ShopcartPresenter> implements ShopcartContract.View {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getConnectLayout());
        initView();
        //初始化dialog
        progressDialog = DialogUtil.getProgressDialog(this);
        String token = (String) SharedPreferencesUtils.getParam(ShopCartActivity.this, "token", "");
        String uid = (String) SharedPreferencesUtils.getParam(ShopCartActivity.this, "uid", "");
        mPresenter.getCarts(uid,token);
    }


    @Override
    public int getConnectLayout() {
        return R.layout.activity_shop_cart;
    }

    @Override
    public void inject() {
        DaggerHttpComponent.builder()
                .build()
                .inject(this);
    }

    private void initView() {
        mElv = (ExpandableListView) findViewById(R.id.elv);
        mCbAll = (CheckBox) findViewById(R.id.cbAll);
        mTvMoney = (TextView) findViewById(R.id.tvMoney);
        mTvTotal = (TextView) findViewById(R.id.tvTotal);

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
                Intent intent = new Intent(ShopCartActivity.this, MakeSureOrderActivity.class);
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

    }

    @Override
    public void showCartList(List<SellerBean> groupList, List<List<GetCartsBean.DataBean.ListBean>> childList) {
        //判断所有商家是否全部选中
        mCbAll.setChecked(isSellerAddSelected(groupList));

        //创建适配器
        adapter = new ElvShopcartAdapter(this, groupList, childList, mPresenter,
                progressDialog);
        mElv.setAdapter(adapter);
        //获取数量和总价
        String[] strings = adapter.computeMoneyAndNum();
        mTvMoney.setText("总计：" + strings[0] + "元");
        mTvTotal.setText("去结算("+strings[1]+"个)");
        //        //默认展开列表
        for (int i = 0; i < groupList.size(); i++) {
            mElv.expandGroup(i);
        }
        //关闭进度条
        progressDialog.dismiss();
    }

    @Override
    public void updateCartsSuccess(String msg) {
        if (adapter!=null){
            adapter.updataSuccess();
        }
    }
 //0.00.0.0.0..
    @Override
    public void deleteCartSuccess(String msg) {
        //调用适配器里的delSuccess()方法
        if (adapter!=null){
            adapter.delSuccess();
        }
    }

    @Override
    public void TuiJianSuccess(AdBean adBean) {

    }

    /**
     * 判断所有商家是否全部选中
     *.......
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
