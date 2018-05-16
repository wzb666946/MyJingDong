package com.example.com.myjingdong.my.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.com.myjingdong.R;
import com.example.com.myjingdong.base.BaseActivaty;
import com.example.com.myjingdong.componnet.DaggerHttpComponent;
import com.example.com.myjingdong.module.HttpModule;
import com.example.com.myjingdong.my.bean.Loginbean;
import com.example.com.myjingdong.my.bean.Regbean;
import com.example.com.myjingdong.my.contract.LoginContract;
import com.example.com.myjingdong.my.presenter.LoginPresenter;

public class RegisterActivity extends BaseActivaty<LoginPresenter> implements LoginContract.View, View.OnClickListener {

    /**
     * 请输入手机号
     */
    private EditText mRegisterMobile;
    /**
     * 请输密码
     */
    private EditText mRegisterPassword;
    /**
     * 注册
     */
    private Button mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getConnectLayout());
        initView();
    }

    @Override
    public int getConnectLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void inject() {
        DaggerHttpComponent.builder()
                .httpModule(new HttpModule())
                .build()
                .inject(this);
    }

    @Override
    public void showLoginimges(Loginbean loginbean) {

    }

    @Override
    public void showRegister(Regbean regbean) {
        if (regbean.getMsg().equals("注册成功")) {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            Toast.makeText(RegisterActivity.this, "" + regbean.getMsg(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(RegisterActivity.this, "" + regbean.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        mRegisterMobile = (EditText) findViewById(R.id.register_mobile);
        mRegisterPassword = (EditText) findViewById(R.id.register_password);
        mRegister = (Button) findViewById(R.id.register);
        mRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.register:
                String mobile = mRegisterMobile.getText().toString();
                String password = mRegisterPassword.getText().toString();
                mPresenter.getRegister(mobile,password);
                break;
        }
    }
}
