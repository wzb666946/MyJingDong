package com.example.com.myjingdong.my.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.com.myjingdong.R;
import com.example.com.myjingdong.base.BaseActivaty;
import com.example.com.myjingdong.componnet.DaggerHttpComponent;
import com.example.com.myjingdong.module.HttpModule;
import com.example.com.myjingdong.my.bean.Loginbean;
import com.example.com.myjingdong.my.bean.Regbean;
import com.example.com.myjingdong.my.contract.LoginContract;
import com.example.com.myjingdong.my.presenter.LoginPresenter;

public class LoginActivity extends BaseActivaty<LoginPresenter> implements View.OnClickListener,LoginContract.View {

    /**
     * 请输入手机号
     */
    private EditText mMobile;
    /**
     * 请输密码
     */
    private EditText mPassword;
    /**
     * 登录
     */
    private Button mBtLogin;
    /**
     * 注册
     */
    private Button mBtregis;
    private ImageView mWxLogin;
    private ImageView mQqLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getConnectLayout());
        initView();
    }

    @Override
    public int getConnectLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void inject() {
        DaggerHttpComponent.builder()
                .httpModule(new HttpModule())
                .build()
                .inject(this);
    }

    private void initView() {
        mMobile = (EditText) findViewById(R.id.mobile);
        mPassword = (EditText) findViewById(R.id.password);
        mBtLogin = (Button) findViewById(R.id.btLogin);
        mBtLogin.setOnClickListener(this);
        mBtregis = (Button) findViewById(R.id.btregis);
        mBtregis.setOnClickListener(this);
        mWxLogin = (ImageView) findViewById(R.id.wx_login);
        mWxLogin.setOnClickListener(this);
        mQqLogin = (ImageView) findViewById(R.id.qq_login);
        mQqLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btLogin:
                String mobile = mMobile.getText().toString();
                String password = mPassword.getText().toString();
                mPresenter.getLoginimgess(mobile,password);
                break;
            case R.id.btregis:
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.wx_login:
                
                break;
            case R.id.qq_login:
                break;
        }
    }

    @Override
    public void showLoginimges(Loginbean loginbean) {
        Toast.makeText(LoginActivity.this,""+loginbean.getMsg(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRegister(Regbean regbean) {

    }
}
