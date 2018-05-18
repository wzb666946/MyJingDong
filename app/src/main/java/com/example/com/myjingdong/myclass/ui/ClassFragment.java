package com.example.com.myjingdong.myclass.ui;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.com.myjingdong.R;
import com.example.com.myjingdong.base.BaseFragment;
import com.example.com.myjingdong.click.OnItreamClickListener;
import com.example.com.myjingdong.componnet.DaggerHttpComponent;
import com.example.com.myjingdong.home.bean.CatagoryBean;
import com.example.com.myjingdong.module.HttpModule;
import com.example.com.myjingdong.myclass.adapter.Left_Adapter;
import com.example.com.myjingdong.myclass.adapter.Right_Adapter;
import com.example.com.myjingdong.myclass.bean.ChildBean;
import com.example.com.myjingdong.myclass.bean.RightBean;
import com.example.com.myjingdong.myclass.contrace.ClassContrace;
import com.example.com.myjingdong.myclass.presenter.ClassPresenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassFragment extends BaseFragment<ClassPresenter> implements ClassContrace.View {

    private View view;
    private RecyclerView class_left;
    private ExpandableListView class_righet;
    private int p =1;
    @Override
    public int getConnectLayout() {
        return R.layout.fragment_class;
    }

    @Override
    public void inject() {
        DaggerHttpComponent.builder()
                .httpModule(new HttpModule())
                .build()
                .inject(this);
    }

    @Override
    public void initView(View view) {
        class_left=view.findViewById(R.id.left_fl);
        class_righet=view.findViewById(R.id.right_fl);
        //设置布局管理器
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(), 1, RecyclerView.VERTICAL, false);
        class_left.setLayoutManager(gridLayoutManager1);
        mPersenter.getCatagory();
        mPersenter.getClassChild(""+p);
    }

    @Override
    public void getClassChildSuccess(RightBean rightBean) {
        List<RightBean.DataBean> list = rightBean.getData();
        Right_Adapter right_adapter=new Right_Adapter(getActivity(),list);
        class_righet.setAdapter(right_adapter);
        class_righet.setGroupIndicator(null);
    }

    @Override
    public void getClassCatagory(CatagoryBean catagoryBean) {
        List<CatagoryBean.DataBean> list = catagoryBean.getData();
        //设置适配器
        Left_Adapter left_adapter=new Left_Adapter(getActivity(),list);
        class_left.setAdapter(left_adapter);
        //设置点击事件
        left_adapter.setOnItemClickListener(new OnItreamClickListener() {
            @Override
            public void onItemClick(int position) {
                if(position==0){
                    position=p;
                }
                mPersenter.getClassChild(""+position);
            }

            @Override
            public void onLongItemClick(int position) {

            }
        });
    }

    @Override
    public void getProducts(ChildBean childBean) {

    }


}
