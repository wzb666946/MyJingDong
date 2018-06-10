package com.example.com.myjingdong.myclass.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.com.myjingdong.MainActivity;
import com.example.com.myjingdong.R;
import com.example.com.myjingdong.base.BaseActivaty;
import com.example.com.myjingdong.componnet.DaggerHttpComponent;
import com.example.com.myjingdong.home.bean.AdBean;
import com.example.com.myjingdong.my.ui.LoginActivity;
import com.example.com.myjingdong.myclass.bean.ChildBean;
import com.example.com.myjingdong.myclass.contrace.AddCartContract;
import com.example.com.myjingdong.myclass.presenter.AddCartPresenter;
import com.example.com.myjingdong.shopcart.ShopCartActivity;
import com.example.com.myjingdong.utils.GlideImageLoader;
import com.example.com.myjingdong.utils.SharedPreferencesUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class XqActivity extends BaseActivaty<AddCartPresenter> implements View.OnClickListener,AddCartContract.View {

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
    private AdBean.TuijianBean.ListBean bean;
    private String flag;
    private String images;
    private String url;
    private String myname=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getConnectLayout());

        Intent intent = getIntent();
        flag = intent.getStringExtra("flag");
       if(flag.equals("1")){
           mybean = (ChildBean.DataBean) intent.getSerializableExtra("bean");
       }else if(flag.equals("0")){
           bean = (AdBean.TuijianBean.ListBean) intent.getSerializableExtra("bean");
       }

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
        final List<String> list_img=new ArrayList<>();
        if(flag.equals("1")){
            String s = mybean.getImages();
            String[] p = s.split("\\|");
            for (int i=0; i<p.length;i++){
                list_img.add(p[i]);
                mXqTitle.setText(mybean.getTitle());
                mXqSubhead.setText(mybean.getSubhead());
                mXqPriceYj.setText("原价"+mybean.getBargainPrice());
                mXqPriceYhj.setText("优惠价"+mybean.getPrice());
                images=mybean.getImages();
            }
            url=mybean.getDetailUrl();
        }else if(flag.equals("0")){
            list_img.add(bean.getImages());
            mXqTitle.setText(bean.getTitle());
            mXqSubhead.setText(bean.getSubhead());
            mXqPriceYj.setText("原价"+bean.getBargainPrice());
            mXqPriceYhj.setText("优惠价"+bean.getPrice());
            images=bean.getImages();
            url=bean.getDetailUrl();
        }


        //设置图片加载器
        mXqBanner.setImageLoader(new GlideImageLoader());
        mXqBanner.setImages(list_img);
        mXqBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent=new Intent(XqActivity.this,BigImgActivity.class);
                intent.putExtra("imageUrls", images);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
        mXqBanner.start();
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
                finish();
                break;
            case R.id.fx:
                UMWeb umWeb = new UMWeb(url);
                new ShareAction(XqActivity.this).withMedia(umWeb).setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ,
                        SHARE_MEDIA.WEIXIN)
                        .setCallback(shareListener).open();

                break;
            case R.id.xq_gm:
                //跳转到购物车
                Intent intent = new Intent(XqActivity.this, ShopCartActivity.class);
                startActivity(intent);
                break;
            case R.id.xq_addcar:
                //先判断是否登录
                String token = (String) SharedPreferencesUtils.getParam(XqActivity.this, "token", "");
                if (TextUtils.isEmpty(token)) {
                    //还未登录
                    //跳转到登录页面
                    Intent intent2 = new Intent(XqActivity.this, LoginActivity.class);
                    startActivity(intent2);
                } else {
                    //登录过了
                    String uid = (String) SharedPreferencesUtils.getParam(XqActivity.this, "uid", "");
                    int pid = 0;
                    if (flag.equals("1")) {
                        pid = mybean.getPid();
                    } else {
                        pid = bean.getPid();
                    }
                    mPresenter.addCart(uid, pid + "", token);
                }
                break;
        }
    }

    @Override
    public int getConnectLayout() {
        return R.layout.activity_xq;
    }


    @Override
    public void inject() {
        DaggerHttpComponent.builder()
                .build()
                .inject(this);
    }

    @Override
    public void onSuccess(String str) {
        Toast.makeText(XqActivity.this, str, Toast.LENGTH_LONG).show();
    }
    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(XqActivity.this, "成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(XqActivity.this, "失败" + t.getMessage
                    (), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(XqActivity.this, "取消了", Toast
                    .LENGTH_LONG).show();
        }
    };
}
