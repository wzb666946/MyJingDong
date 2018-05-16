package com.example.com.myjingdong.myclass.ui;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.com.myjingdong.R;
import com.example.com.myjingdong.myclass.bean.ChildBean;
import com.example.com.myjingdong.utils.GlideImageLoader;
import com.youth.banner.Banner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class XqActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mFh;
    private ImageView mFx;
    private Banner mXqBanner;
    /**
     * 冻结爱火锅覅发解放军
     */
    private TextView mXqTitle;
    /**
     * 冻结爱火锅覅发解放军
     */
    private TextView mXqSubhead;
    /**
     * 原价
     */
    private TextView mXqPriceYj;
    /**
     * 原价
     */
    private TextView mXqPriceYhj;
    /**
     * 立即购买
     */
    private TextView mXqGm;
    /**
     * 加入购物车
     */
    private TextView mXqAddcar;
    private ChildBean.DataBean mybean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xq);

        Intent intent = getIntent();
        mybean = (ChildBean.DataBean) intent.getSerializableExtra("bean");

        initView();
    }

    private void initView() {
        mFh = (ImageView) findViewById(R.id.fh);
        mFh.setOnClickListener(this);
        mFx = (ImageView) findViewById(R.id.fx);
        mFx.setOnClickListener(this);
        mXqBanner = (Banner) findViewById(R.id.xq_banner);
        mXqTitle = (TextView) findViewById(R.id.xq_title);
        mXqSubhead = (TextView) findViewById(R.id.xq_subhead);
        mXqPriceYj= (TextView) findViewById(R.id.bargainPrice);
        mXqPriceYhj = (TextView) findViewById(R.id.price);
        mXqGm = (TextView) findViewById(R.id.xq_gm);
        mXqGm.setOnClickListener(this);
        mXqAddcar = (TextView) findViewById(R.id.xq_addcar);
        mXqAddcar.setOnClickListener(this);
        List<String> list_img=new ArrayList<>();
        String s = mybean.getImages();
        String[] p = s.split("\\|");
        for (int i=0; i<p.length;i++){
            list_img.add(p[i]);
        }
        //设置图片加载器
        mXqBanner.setImageLoader(new GlideImageLoader());
        mXqBanner.setImages(list_img);
        mXqBanner.start();
        mXqTitle.setText(mybean.getTitle());
        mXqSubhead.setText(mybean.getSubhead());
        mXqPriceYj.setText("原价"+mybean.getBargainPrice());
        mXqPriceYhj.setText("优惠价"+mybean.getPrice());
        mXqPriceYj.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //结束轮播
        mXqBanner.stopAutoPlay();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.fh:
                break;
            case R.id.fx:
                break;
            case R.id.xq_gm:
                break;
            case R.id.xq_addcar:
                break;
        }
    }
}
