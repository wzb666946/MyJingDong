package com.example.com.myjingdong.my.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.com.myjingdong.MainActivity;
import com.example.com.myjingdong.R;
import com.example.com.myjingdong.base.BaseActivaty;
import com.example.com.myjingdong.componnet.DaggerHttpComponent;
import com.example.com.myjingdong.module.HttpModule;
import com.example.com.myjingdong.my.bean.Loginbean;
import com.example.com.myjingdong.my.bean.Regbean;
import com.example.com.myjingdong.my.contract.LoginContract;
import com.example.com.myjingdong.my.presenter.LoginPresenter;
import com.example.com.myjingdong.utils.SharedPreferencesUtils;

public class LoginActivity extends BaseActivaty<LoginPresenter> implements View.OnClickListener, LoginContract.View {

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
    private TextView mBtregis;
    private ImageView mWxLogin;
    private ImageView mQqLogin;
    /**
     * 注销
     */
    private Button mZx;
    /**
     * 忘记密码
     */
    private TextView mWjmm;

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
        mBtregis = (TextView) findViewById(R.id.btregis);
        mBtregis.setOnClickListener(this);
        mWxLogin = (ImageView) findViewById(R.id.wx_login);
        mWxLogin.setOnClickListener(this);
        mQqLogin = (ImageView) findViewById(R.id.qq_login);
        mQqLogin.setOnClickListener(this);
        mMobile.setOnClickListener(this);
        mZx = (Button) findViewById(R.id.zx);
        mZx.setOnClickListener(this);
        mWjmm = (TextView) findViewById(R.id.wjmm);
        mWjmm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btLogin:
                String mobile = mMobile.getText().toString();
                String password = mPassword.getText().toString();
                mPresenter.getLoginimgess(mobile, password);
                break;
            case R.id.btregis:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.wx_login:

                break;
            case R.id.qq_login:
                break;
            case R.id.mobile:
                break;
            case R.id.zx://注销
                SharedPreferencesUtils.setParam(LoginActivity.this, "uid",  "");
                SharedPreferencesUtils.setParam(LoginActivity.this, "name",  "");
                SharedPreferencesUtils.setParam(LoginActivity.this, "iconUrl",  "");
                SharedPreferencesUtils.setParam(LoginActivity.this, "token", "");
                Intent intent1=new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent1);
                Toast.makeText(LoginActivity.this, "已经退出登录", Toast.LENGTH_SHORT).show();
                break;
            case R.id.wjmm:
                break;
        }
    }

    @Override
    public void showLoginimges(Loginbean userBean) {
        if(userBean.getMsg().equals("登录成功")){
            Toast.makeText(LoginActivity.this, userBean.getMsg(), Toast.LENGTH_SHORT).show();
            SharedPreferencesUtils.setParam(LoginActivity.this, "uid", userBean.getData().getUid() + "");
            SharedPreferencesUtils.setParam(LoginActivity.this, "name", userBean.getData().getUsername() + "");
            SharedPreferencesUtils.setParam(LoginActivity.this, "iconUrl", userBean.getData().getIcon() + "");
            SharedPreferencesUtils.setParam(LoginActivity.this, "token", userBean.getData().getToken() + "");
            Intent intent=new Intent();
            Bundle bundle=new Bundle();
            bundle.putString("myname",""+userBean.getData().getUsername());
            intent.putExtras(bundle);
            setResult(2,intent);
            finish();
        }

    }

    @Override
    public void showRegister(Regbean regbean) {


    }
}
