package com.example.com.myjingdong.myclass.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.com.myjingdong.R;
import com.example.com.myjingdong.base.BaseActivaty;
import com.example.com.myjingdong.componnet.DaggerHttpComponent;
import com.example.com.myjingdong.home.bean.CatagoryBean;
import com.example.com.myjingdong.module.HttpModule;
import com.example.com.myjingdong.myclass.adapter.ClassChild_Adapter;
import com.example.com.myjingdong.myclass.bean.ChildBean;
import com.example.com.myjingdong.myclass.bean.RightBean;
import com.example.com.myjingdong.myclass.contrace.ClassContrace;
import com.example.com.myjingdong.myclass.presenter.ClassPresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainChildActivity extends BaseActivaty<ClassPresenter> implements ClassContrace.View {
    private  ClassChild_Adapter classChild_adapter;
    private XRecyclerView mClasschildRl;
    private List<ChildBean.DataBean> list;
    private List<ChildBean.DataBean> alllist=new ArrayList<>();
    private Boolean isRefresh=true;
    private int num=1;
    private String cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getConnectLayout());
        initView();
        Intent intent = getIntent();
        cid = intent.getStringExtra("cid");
        mPresenter.getProducts(cid,""+num);

    }

    private void initView() {
        mClasschildRl = (XRecyclerView) findViewById(R.id.classchild_rl);
        //设置布局管理器
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false);
        mClasschildRl.setLayoutManager(gridLayoutManager1);
        mClasschildRl.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //刷新
                isRefresh=true;
                num=1;
                mPresenter.getProducts(cid,""+num);
            }

            @Override
            public void onLoadMore() {
                //刷新
                isRefresh=false;
                num++;
                if (num>3){
                    Toast.makeText(MainChildActivity.this,"不能加载了!只有三页",Toast.LENGTH_SHORT).show();
                    return;
                }
                mPresenter.getProducts(cid,""+num);
            }
        });
    }

    @Override
    public int getConnectLayout() {
        return R.layout.activity_main_child;
    }

    @Override
    public void inject() {
        DaggerHttpComponent.builder()
                .httpModule(new HttpModule())
                .build()
                .inject(this);
    }


    @Override
    public void getClassChildSuccess(RightBean rightBean) {

    }

    @Override
    public void getClassCatagory(CatagoryBean catagoryBean) {

    }
    private void setadapter() {
        if(isRefresh){
            classChild_adapter=new ClassChild_Adapter(this,list);
            mClasschildRl.setAdapter(classChild_adapter);
            classChild_adapter.refresh(alllist);
            mClasschildRl.refreshComplete();//设置刷新完成
        }else{
            if(classChild_adapter!=null){
                classChild_adapter.loadMore(alllist);
                mClasschildRl.loadMoreComplete();//设置加载更多完成
            }
        }
        if (classChild_adapter==null){
            return;
        }

    }
    @Override
    public void getProducts(ChildBean childBean) {
        list=childBean.getData();
        alllist.addAll(list);
        setadapter();
        classChild_adapter.setOnListItemClickListener(new ClassChild_Adapter.OnListItemClickListener() {
            @Override
            public void OnItemClick(ChildBean.DataBean dataBean) {
                Intent intent=new Intent(MainChildActivity.this,XqActivity.class);
                intent.putExtra("bean", dataBean);
                intent.putExtra("flag","1");
                startActivity(intent);
            }
        });

        }
}
