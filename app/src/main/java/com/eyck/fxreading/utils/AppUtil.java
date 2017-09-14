package com.eyck.fxreading.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.view.WindowManager;

/**
 * Created by Eyck on 2017/9/1.
 */

public class AppUtil {
    /**
     * 获取版本名
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context){
        PackageManager pm = context.getPackageManager();
        PackageInfo pi;
        try {
            pi = pm.getPackageInfo(context.getPackageName(),0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getVersionName(Context context){
        try{
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;
        }catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getAppVersionCode(Context context){
        PackageManager pm = context.getPackageManager();
        PackageInfo pi;
        try {
            pi = pm.getPackageInfo(context.getPackageName(),0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static String getDeviceId(Context context){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }
    public static int dp2px(Context paramContext, float paramFloat)
    {
        float scale = paramContext.getResources().getDisplayMetrics().density;
        return (int)(0.5F + paramFloat * scale);
    }
    public static int getWindowWidth(Context paramContext)
    {
        return getWindowManager(paramContext).getDefaultDisplay().getWidth();
    }
    public static WindowManager getWindowManager(Context paramContext)
    {
        return (WindowManager)paramContext.getSystemService(Context.WINDOW_SERVICE);
    }
}
