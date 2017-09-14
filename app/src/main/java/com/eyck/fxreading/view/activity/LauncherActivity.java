package com.eyck.fxreading.view.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.eyck.fxreading.R;
import com.eyck.fxreading.app.FXApplication;
import com.eyck.fxreading.di.components.DaggerLauncherComponent;
import com.eyck.fxreading.di.modules.LauncherModule;
import com.eyck.fxreading.presenter.LauncherContract;
import com.eyck.fxreading.presenter.LauncherPresenter;
import com.eyck.fxreading.utils.AppUtil;
import com.eyck.fxreading.utils.FileUtil;
import com.eyck.fxreading.utils.PreferenceUtils;
import com.eyck.fxreading.view.base.BaseActivity;
import com.eyck.fxreading.view.widget.FixedImageView;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class LauncherActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks , LauncherContract.View{

    @Bind(R.id.splash_img)
    FixedImageView splashImg;
    @Bind(R.id.weread_logo_iv)
    ImageView wereadLogoIv;
    @Bind(R.id.publish_logo_iv)
    ImageView publishLogoIv;

    @Inject
    LauncherPresenter presenter;

    private static final int PERMISSON_REQUESTCODE = 1;

    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerLauncherComponent.builder()
                .netComponent(FXApplication.get(this).getNetComponent())
                .launcherModule(new LauncherModule(this))
                .build()
                .inject(this);
        initStates();
    }

    private void initStates() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestCodePermissions();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }

    @AfterPermissionGranted(PERMISSON_REQUESTCODE)
    private void requestCodePermissions() {
        if(!EasyPermissions.hasPermissions(this,needPermissions)) {
            EasyPermissions.requestPermissions(this,"应用需要这些权限",PERMISSON_REQUESTCODE,needPermissions);
        }else {
            setContentView(R.layout.activity_launcher);
            ButterKnife.bind(this);
            delaySplash();
            String deviceId = AppUtil.getDeviceId(this);
            presenter.getLauncher(deviceId);
        }
    }




    private void delaySplash() {
        List<String> picList = FileUtil.getAllAD();
        if(picList.size() > 0) {
            Random random = new Random();
            int index = random.nextInt(picList.size());
            int imgIndex = PreferenceUtils.getPrefInt(this, "splash_img_index", 0);
            Logger.i("当前的imgIndex=" + imgIndex);
            if(index == imgIndex) {
                if(index >= picList.size()) {
                    index--;
                }else if(imgIndex == 0){
                    if (index + 1 < picList.size()) {
                        index++;
                    }
                }
            }
            PreferenceUtils.setPrefInt(this, "splash_img_index", index);
            Logger.i("当前的picList.size=" + picList.size() + ",index = " + index);
            File file = new File(picList.get(index));
            try {
                InputStream fis = new FileInputStream(file);
                splashImg.setImageDrawable(inputStream2Drawable(fis));
                animWelcomeImage();
                fis.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            AssetManager assets = this.getAssets();
            try {
                InputStream in = assets.open("welcome_default.jpg");
                splashImg.setImageDrawable(inputStream2Drawable(in));
                animWelcomeImage();
                in.close();
            } catch (IOException e) {

            }
        }
    }

    private void animWelcomeImage() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(splashImg, "translationX", 100F);
        animator.setDuration(1500L).start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private Drawable inputStream2Drawable(InputStream fis) {
        Drawable splashImg = BitmapDrawable.createFromStream(fis, "splashImg");
        return splashImg;
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        recreate();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        showMissingPermissionDialog();
    }

    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("当前应用缺少必要权限。请点击\"设置\"-\"权限\"-打开所需权限。");

        // 拒绝, 退出应用
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

        builder.setPositiveButton("设置",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                });

        builder.setCancelable(false);

        builder.show();
    }

    /**
     * 启动应用的设置
     */
    private void startAppSettings() {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }
}
