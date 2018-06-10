package com.example.com.myjingdong.my.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.com.myjingdong.R;
import com.example.com.myjingdong.shopcart.ShopCartActivity;
import com.example.com.myjingdong.utils.SharedPreferencesUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

public class MyFragment extends Fragment implements View.OnClickListener {

    private SimpleDraweeView mImageView;
    /**
     * 登录/注册
     */
    private TextView mDenglu;
    private LinearLayout mLn;
    private Button mFukuan;
    private Button mShouhuo;
    private Button mPingjia;
    private Button mTuihuan;
    private Button mDingdan;
    private LinearLayout mLl;
    private ScrollView mSc;
    private View view;
    private String myname=null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my, null);
        initView(view);
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==2){
            Bundle b=data.getExtras();
            String p = b.getString("myname");
            myname=p;
            if(myname==null){
                mDenglu.setText("登录/注册");
                Uri uri=Uri.parse(""+R.mipmap.user);
                mImageView.setImageURI(uri);
            }else{
                loginSuccess();
            }
        }else if(requestCode==1&&resultCode==3){
            Bundle b=data.getExtras();
            String p = b.getString("myname");
            myname=p;
            if(myname==null){
                mDenglu.setText("登录/注册");
                Uri uri=Uri.parse(""+R.mipmap.user);
                mImageView.setImageURI(uri);
            }else{
                loginSuccess();
            }
        }else if(requestCode==1&&resultCode==4){
            Bundle b=data.getExtras();
            String p = b.getString("myname");
            myname=p;
            if(myname==null){
                mDenglu.setText("登录/注册");
                Uri uri=Uri.parse(""+R.mipmap.user);
                mImageView.setImageURI(uri);
                mImageView.setClickable(false);
                mImageView.setFocusable(false);
            }else{
                loginSuccess();
            }
        }else if(requestCode==1&&resultCode==5){
            Bundle b=data.getExtras();
            String p = b.getString("tx");
            String pp= (String) SharedPreferencesUtils.getParam(getContext(),"tx","");
            Log.d("aaa",""+pp);
            Uri uri=Uri.parse(""+pp);
            mImageView.setImageURI(uri);
            String myname = (String) SharedPreferencesUtils.getParam(getContext(), "name", "");
            mDenglu.setText(""+myname);
        }
    }
    public void initView(View view) {
        mImageView = (SimpleDraweeView) view.findViewById(R.id.imageView);
        mImageView.setOnClickListener(this);
        mDenglu = (TextView) view.findViewById(R.id.denglu);
        mDenglu.setOnClickListener(this);
        mLn = (LinearLayout) view.findViewById(R.id.ln);
        mLn.setOnClickListener(this);
        mFukuan = (Button) view.findViewById(R.id.fukuan);
        mFukuan.setOnClickListener(this);
        mShouhuo = (Button) view.findViewById(R.id.shouhuo);
        mShouhuo.setOnClickListener(this);
        mPingjia = (Button) view.findViewById(R.id.pingjia);
        mPingjia.setOnClickListener(this);
        mTuihuan = (Button) view.findViewById(R.id.tuihuan);
        mTuihuan.setOnClickListener(this);
        mDingdan = (Button) view.findViewById(R.id.dingdan);
        mDingdan.setOnClickListener(this);
        mLl = (LinearLayout) view.findViewById(R.id.ll);
        mLl.setOnClickListener(this);
        mSc = (ScrollView) view.findViewById(R.id.sc);
        mSc.setOnClickListener(this);
        myname = (String) SharedPreferencesUtils.getParam(getContext(), "name", "");
        if(myname==""){
            mDenglu.setText("登录/注册");
            Uri uri=Uri.parse(""+R.mipmap.user);
            mImageView.setImageURI(uri);
        }else{
            loginSuccess();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.imageView:
                break;
            case R.id.denglu:
                Intent intent=new Intent(getActivity(),LoginActivity.class);
                Bundle bundle=new Bundle();
                intent.putExtras(bundle);
                startActivityForResult(intent,1);
                break;
            case R.id.ln:

                break;
            case R.id.fukuan:
                Toast.makeText(getActivity(), "付款", Toast.LENGTH_SHORT).show();
                break;
            case R.id.shouhuo:
                Toast.makeText(getActivity(), "收货", Toast.LENGTH_SHORT).show();
                break;
            case R.id.pingjia:
                Toast.makeText(getActivity(), "评价", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tuihuan:
                Toast.makeText(getActivity(), "推荐", Toast.LENGTH_SHORT).show();
                break;
            case R.id.dingdan:
                Intent intent2 = new Intent(getActivity(), MakeSureOrderActivity.class);
                startActivity(intent2);
                //Toast.makeText(getActivity(), "订单", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll:
                break;
            case R.id.sc:
                break;
        }
    }
    public  void  loginSuccess(){
        String url = (String) SharedPreferencesUtils.getParam(getContext(), "iconUrl", "");
        mDenglu.setText(""+myname);
        mImageView.setImageURI(url);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),UserInfoActivity.class);
                Bundle bundle=new Bundle();
                intent.putExtras(bundle);
                startActivityForResult(intent,1);
            }
        });
    }
}
