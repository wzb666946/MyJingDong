package com.example.com.myjingdong.home.ui;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.com.myjingdong.R;
import com.example.com.myjingdong.base.BaseFragment;

import com.example.com.myjingdong.componnet.DaggerHttpComponent;
import com.example.com.myjingdong.home.bean.AdBean;
import com.example.com.myjingdong.home.bean.CatagoryBean;
import com.example.com.myjingdong.home.adapter.RvClassAdapter;
import com.example.com.myjingdong.home.adapter.RvRecommendAdapter;
import com.example.com.myjingdong.home.adapter.RvSecKillAdapter;
import com.example.com.myjingdong.home.page.contract.HomePageContract;
import com.example.com.myjingdong.home.page.presenter.HomePagePresenter;
import com.example.com.myjingdong.inter.OnItemClickListenner;
import com.example.com.myjingdong.module.HttpModule;
import com.example.com.myjingdong.myclass.ui.XqActivity;
import com.example.com.myjingdong.utils.GlideImageLoader;
import com.sunfusheng.marqueeview.MarqueeView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment<HomePagePresenter> implements HomePageContract.View{
    private Banner banner;
    private RecyclerView rvClass;
    private MarqueeView marqueeView;
    private RecyclerView rvSecKill;
    private RecyclerView rvRecommend;
    private ImageView ivZxing;


    @Override
    public void getAdSuccess(final AdBean adBean) {
        final List<AdBean.DataBean> data = adBean.getData();
        List<String> images = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            images.add(data.get(i).getIcon());
        }

        //设置图片集合
        banner.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        //设置轮播图点击事件
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent=new Intent(getActivity(),LunBoWebActivity.class);
                intent.putExtra("url",data.get(position).getUrl());
                startActivity(intent);
            }
        });

        RvSecKillAdapter rvSecKillAdapter = new RvSecKillAdapter(getActivity(), adBean.getMiaosha().getList());
        rvSecKill.setAdapter(rvSecKillAdapter);
        rvSecKillAdapter.setOnItemClickListener(new OnItemClickListenner() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(getActivity(),KillActivity.class);
                intent.putExtra("url",adBean.getMiaosha().getList().get(position).getDetailUrl());
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(int positin) {

            }
        });
        RvRecommendAdapter rvRecommendAdapter = new RvRecommendAdapter(getActivity(), adBean.getTuijian().getList());
        rvRecommend.setAdapter(rvRecommendAdapter);
        rvRecommendAdapter.setOnItemClickListener(new OnItemClickListenner() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(getActivity(),XqActivity.class);
                intent.putExtra("bean",adBean.getTuijian().getList().get(position));
                intent.putExtra("flag","0");
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(int positin) {

            }
        });
    }

    @Override
    public void getCatagory(CatagoryBean catagoryBean) {
        final List<CatagoryBean.DataBean> data = catagoryBean.getData();
        RvClassAdapter adapter = new RvClassAdapter(getActivity(), data);
        rvClass.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListenner() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getActivity(), ""+data.get(position).getName(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongItemClick(int positin) {

            }
        });
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
        marqueeView = view.findViewById(R.id.marqueeView);
        List<String> info = new ArrayList<>();
        info.add("要想成功先发疯");
        info.add("闭着眼往前冲");
        info.add("冲葱从!!!");
        marqueeView.startWithList(info);

        banner = (Banner) view.findViewById(R.id.banner);
        rvClass = view.findViewById(R.id.rvClass);
        //设置布局管理器
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, RecyclerView.HORIZONTAL, false);
        rvClass.setLayoutManager(gridLayoutManager);

        rvSecKill = view.findViewById(R.id.rvSecKill);
        //设置布局管理器
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false);
        rvSecKill.setLayoutManager(gridLayoutManager1);

        //设置布局管理器
        rvRecommend = view.findViewById(R.id.rvRecommend);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
        rvRecommend.setLayoutManager(gridLayoutManager2);

        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());

        //二维码
        ivZxing = view.findViewById(R.id.ivZxing);


        //请求数据
        initData();
    }

    private void initData() {
       mPersenter.getAd();
        mPersenter.getCatagory();
    }

    @Override
    public int getConnectLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //结束轮播
        banner.stopAutoPlay();
    }
}
