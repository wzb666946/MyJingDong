package com.example.com.myjingdong.my.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.com.myjingdong.R;
import com.example.com.myjingdong.base.BaseActivaty;
import com.example.com.myjingdong.componnet.DaggerHttpComponent;
import com.example.com.myjingdong.my.contract.UpdateHeaderContract;
import com.example.com.myjingdong.my.presenter.UpdatePresenter;
import com.example.com.myjingdong.utils.SharedPreferencesUtils;

import java.io.File;

public class UserInfoActivity extends BaseActivaty<UpdatePresenter> implements
        UpdateHeaderContract.View, View.OnClickListener {

    private ImageView mIv;
    private PopupWindow popupWindow;
    private LinearLayout ll;
    private String imgPath;
    private File imgFile;
    private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private Bitmap photo;
    private TextView mTv;
    /**
     * 退出登录
     */
    private Button mBt;

    /**
     * 退出登录
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getConnectLayout());
        imgPath = getExternalCacheDir() + File.separator + "header.jpg";
        imgFile = new File(imgPath);
        initView();
        Log.e("UserInfoActivity", "imgPath:" + imgPath);
        String name = (String) SharedPreferencesUtils.getParam(this, "name", "");
        String iconUrl = (String) SharedPreferencesUtils.getParam(this, "iconUrl", "");
        mTv.setText(name);
        Glide.with(this).load(iconUrl).into(mIv);


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.bt:
                //Toast.makeText(UserInfoActivity.this,"打开还发",Toast.LENGTH_SHORT).show();
                //清空SharedPreferences
                SharedPreferencesUtils.clear(UserInfoActivity.this);
                //回到登录页面
                Intent intent1=this.getIntent();
                Bundle bundle=intent1.getExtras();
                bundle.putString("myname",null);
                this.setResult(4,intent1);
                UserInfoActivity.this.finish();
                break;
            case R.id.iv:
                //在底部弹出PopupWindow
                initPopupWindow();
                popupWindow.showAtLocation(ll, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.btn_cancel:
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                break;
            case R.id.btn_take_photo:
                //拍照
                takePhoto();
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                break;
            case R.id.btn_pick_photo:

                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                break;
            case R.id.tv:
                break;
        }
    }

    private void initView() {
        mIv =  findViewById(R.id.iv);
        mIv.setOnClickListener(this);
        mTv = findViewById(R.id.tv);
        mTv.setOnClickListener(this);
        mBt =  findViewById(R.id.bt);
        mBt.setOnClickListener(UserInfoActivity.this);
        ll =  findViewById(R.id.ll);
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imgFile));
        startActivityForResult(intent,PHOTO_REQUEST_TAKEPHOTO);
    }

    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_REQUEST_TAKEPHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    //截取图片
                    startPhotoZoom(Uri.fromFile(imgFile));
                }
                break;
            case PHOTO_REQUEST_CUT:
                //截图图片成功
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    photo = bundle.getParcelable("data");
                    //上传头像
                    mPresenter.updateHeader(getUid(), imgPath);
                }
                break;
        }
    }


    private void initPopupWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.pop_item, null);
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout
                .LayoutParams.WRAP_CONTENT);
        //点击PopupWindow外部可以取消
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());

        Button btn_take_photo = view.findViewById(R.id.btn_take_photo);
        Button btn_pick_photo = view.findViewById(R.id.btn_pick_photo);
        Button btn_cancel = view.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);
        btn_pick_photo.setOnClickListener(this);
        btn_take_photo.setOnClickListener(this);
    }


    @Override
    public int getConnectLayout() {
        return R.layout.activity_user_info;
    }

    @Override
    public void inject() {
        DaggerHttpComponent.builder()
                .build()
                .inject(this);

    }

    @Override
    public void updateSuccess(String code) {
        if ("0".equals(code) && photo != null) {
            toast("上传成功");
            //去设置头像
            mIv.setImageBitmap(photo);
            //回到登录页面
            Intent intent1=this.getIntent();
            Bundle bundle=intent1.getExtras();
            String p=imgPath.toString();
            SharedPreferencesUtils.setParam(this,"tx",""+p);
            bundle.putString("tx",""+p);
            Log.d("ppp",""+imgFile);
            this.setResult(5,intent1);
            UserInfoActivity.this.finish();
        }
    }

}
