package com.litongjava.android.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * 屏幕工具类
 */
public class ScreenUtils {

  //获取屏幕宽度
  public static int getScreenWidth(Context context) {
    Resources resources = context.getResources();
    DisplayMetrics metrics = resources.getDisplayMetrics();
    return metrics.widthPixels;
  }

  //获取屏幕高度
  public static int getScreenHeight(Context context) {
    Resources resources = context.getResources();
    DisplayMetrics metrics = resources.getDisplayMetrics();
    return metrics.heightPixels;
  }
}
